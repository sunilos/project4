package com.sunilos.p4.model;

import com.sunilos.p4.bean.CourseBean;
import com.sunilos.p4.exception.DuplicateRecordException;
import com.sunilos.p4.util.JDBCDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CourseModelTest {

    private CourseModel model;
    private Connection mockConn;
    private PreparedStatement mockSelectPstmt;
    private PreparedStatement mockMaxPKPstmt;
    private PreparedStatement mockDMLPstmt;
    private ResultSet mockSelectRs;
    private ResultSet mockMaxPKRs;
    private Statement mockStatement;
    private MockedStatic<JDBCDataSource> mockJDBC;

    @BeforeEach
    void setUp() throws Exception {
        model = new CourseModel();
        mockConn = mock(Connection.class);

        mockMaxPKPstmt = mock(PreparedStatement.class);
        mockMaxPKRs = mock(ResultSet.class);
        when(mockMaxPKPstmt.executeQuery()).thenReturn(mockMaxPKRs);
        when(mockMaxPKRs.next()).thenReturn(true, false);
        when(mockMaxPKRs.getInt(1)).thenReturn(5); // MAX(ID)=5 → nextPK=6

        mockSelectPstmt = mock(PreparedStatement.class);
        mockSelectRs = mock(ResultSet.class);
        when(mockSelectPstmt.executeQuery()).thenReturn(mockSelectRs);

        mockDMLPstmt = mock(PreparedStatement.class);
        when(mockDMLPstmt.executeUpdate()).thenReturn(1);

        when(mockConn.prepareStatement(anyString())).thenAnswer(inv -> {
            String sql = ((String) inv.getArgument(0)).toUpperCase();
            if (sql.contains("MAX(ID)"))
                return mockMaxPKPstmt;
            if (sql.contains("SELECT"))
                return mockSelectPstmt;
            return mockDMLPstmt; // INSERT / UPDATE / DELETE
        });

        // Provide a mock Statement for updatedTimestamp calls
        mockStatement = mock(Statement.class);
        when(mockConn.createStatement()).thenReturn(mockStatement);
        when(mockStatement.execute(anyString())).thenReturn(true);

        mockJDBC = mockStatic(JDBCDataSource.class);
        mockJDBC.when(JDBCDataSource::getConnection).thenReturn(mockConn);
    }

    @AfterEach
    void tearDown() {
        mockJDBC.close();
    }

    @Test
    void testAdd_success() throws Exception {
        when(mockSelectRs.next()).thenReturn(false); // findByName → no duplicate

        long pk = model.add(buildBean("Java"));

        assertEquals(6L, pk);
        verify(mockDMLPstmt).executeUpdate();
        verify(mockConn).commit();
    }

    @Test
    void testAdd_throwsDuplicateWhenNameExists() throws Exception {
        when(mockSelectRs.next()).thenReturn(true, false); // findByName finds one row
        stubCourseRs(mockSelectRs, 99L, "Java");

        assertThrows(DuplicateRecordException.class, () -> model.add(buildBean("Java")));
        verify(mockDMLPstmt, never()).executeUpdate();
    }

    @Test
    void testDelete_success() throws Exception {
        CourseBean bean = new CourseBean();
        bean.setId(1L);

        model.delete(bean);

        verify(mockDMLPstmt).executeUpdate();
        verify(mockConn).commit();
    }

    @Test
    void testFindByPK_found() throws Exception {
        when(mockSelectRs.next()).thenReturn(true, false);
        stubCourseRs(mockSelectRs, 1L, "Java");

        CourseBean result = model.findByPK(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Java", result.getName());
    }

    @Test
    void testFindByPK_notFound() throws Exception {
        when(mockSelectRs.next()).thenReturn(false);

        assertNull(model.findByPK(99L));
    }

    @Test
    void testFindByName_found() throws Exception {
        when(mockSelectRs.next()).thenReturn(true, false);
        stubCourseRs(mockSelectRs, 2L, "Spring");

        CourseBean result = model.findByName("Spring");

        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals("Spring", result.getName());
    }

    @Test
    void testFindByName_notFound() throws Exception {
        when(mockSelectRs.next()).thenReturn(false);

        assertNull(model.findByName("Unknown"));
    }

    @Test
    void testUpdate_success() throws Exception {
        when(mockSelectRs.next()).thenReturn(false); // findByName → no duplicate

        CourseBean bean = buildBean("Hibernate");
        bean.setId(1L);
        model.update(bean);

        verify(mockDMLPstmt).executeUpdate();
        verify(mockConn).commit();
    }

    @Test
    void testUpdate_throwsDuplicateWhenNameTakenByOther() throws Exception {
        when(mockSelectRs.next()).thenReturn(true, false);
        stubCourseRs(mockSelectRs, 99L, "Hibernate"); // different ID

        CourseBean bean = buildBean("Hibernate");
        bean.setId(1L);

        assertThrows(DuplicateRecordException.class, () -> model.update(bean));
        verify(mockDMLPstmt, never()).executeUpdate();
    }

    @Test
    void testList_returnsAllRecords() throws Exception {
        when(mockSelectRs.next()).thenReturn(true, true, false);
        when(mockSelectRs.getLong("ID")).thenReturn(1L, 2L);
        when(mockSelectRs.getString("CREATED_BY")).thenReturn("admin");
        when(mockSelectRs.getString("MODIFIED_BY")).thenReturn("admin");
        when(mockSelectRs.getTimestamp("CREATED_DATETIME")).thenReturn(null);
        when(mockSelectRs.getTimestamp("MODIFIED_DATETIME")).thenReturn(null);
        when(mockSelectRs.getString("NAME")).thenReturn("Java", "Spring");
        when(mockSelectRs.getString("DESCRIPTION")).thenReturn("Core", "Framework");
        when(mockSelectRs.getString("DURATION")).thenReturn("40hrs", "30hrs");
        // Also stub index-based accessors for backwards compatibility
        when(mockSelectRs.getString(2)).thenReturn("Java", "Spring");
        when(mockSelectRs.getString(3)).thenReturn("Core", "Framework");
        when(mockSelectRs.getString(4)).thenReturn("40hrs", "30hrs");

        List<CourseBean> list = model.list(1, 10);

        assertEquals(2, list.size());
        assertEquals("Java", list.get(0).getName());
        assertEquals("Spring", list.get(1).getName());
    }

    @Test
    void testSearch_byName_returnsMatchingRecords() throws Exception {
        when(mockSelectRs.next()).thenReturn(true, false);
        stubCourseRs(mockSelectRs, 3L, "Spring");

        CourseBean criteria = new CourseBean();
        criteria.setName("Spring");
        List<CourseBean> list = model.search(criteria, 1, 10);

        assertEquals(1, list.size());
        assertEquals("Spring", list.get(0).getName());
    }

    private CourseBean buildBean(String name) {
        CourseBean bean = new CourseBean();
        bean.setName(name);
        bean.setDescription("Core topics");
        bean.setDuration("40hrs");
        bean.setCreatedBy("Admin");
        bean.setModifiedBy("Admin");
        bean.setCreatedDatetime(new Timestamp(System.currentTimeMillis()));
        bean.setModifiedDatetime(new Timestamp(System.currentTimeMillis()));
        return bean;
    }

    private void stubCourseRs(ResultSet rs, long id, String name) throws Exception {
        when(rs.getLong("ID")).thenReturn(id);
        when(rs.getString("CREATED_BY")).thenReturn("Admin");
        when(rs.getString("MODIFIED_BY")).thenReturn("Admin");
        when(rs.getTimestamp("CREATED_DATETIME")).thenReturn(null);
        when(rs.getTimestamp("MODIFIED_DATETIME")).thenReturn(null);
        when(rs.getString("NAME")).thenReturn(name);
        when(rs.getString("DESCRIPTION")).thenReturn("Core");
        when(rs.getString("DURATION")).thenReturn("40hrs");
        // Also stub index-based accessors for backwards compatibility
        when(rs.getString(2)).thenReturn(name);
        when(rs.getString(3)).thenReturn("Core");
        when(rs.getString(4)).thenReturn("40hrs");
    }
}
