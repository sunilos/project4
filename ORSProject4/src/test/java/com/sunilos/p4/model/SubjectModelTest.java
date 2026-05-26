package com.sunilos.p4.model;

import com.sunilos.p4.bean.SubjectBean;
import com.sunilos.p4.exception.DuplicateRecordException;
import com.sunilos.p4.util.JDBCDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class SubjectModelTest {

    private SubjectModel model;
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
        model = new SubjectModel();
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

        // Provide a mock Statement for updatedTimestamp
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

        long pk = model.add(buildBean("Mathematics", 1L));

        assertEquals(6L, pk);
        verify(mockDMLPstmt).executeUpdate();
        verify(mockConn).commit();
    }

    @Test
    void testAdd_throwsDuplicateWhenNameExists() throws Exception {
        when(mockSelectRs.next()).thenReturn(true, false);
        stubSubjectRs(mockSelectRs, 99L, "Mathematics", 1L);

        assertThrows(DuplicateRecordException.class, () -> model.add(buildBean("Mathematics", 1L)));
        verify(mockDMLPstmt, never()).executeUpdate();
    }

    @Test
    void testDelete_success() throws Exception {
        SubjectBean bean = new SubjectBean();
        bean.setId(1L);

        model.delete(bean);

        verify(mockDMLPstmt).executeUpdate();
        verify(mockConn).commit();
    }

    @Test
    void testFindByPK_found() throws Exception {
        when(mockSelectRs.next()).thenReturn(true, false);
        stubSubjectRs(mockSelectRs, 1L, "Mathematics", 1L);

        SubjectBean result = model.findByPK(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Mathematics", result.getName());
    }

    @Test
    void testFindByPK_notFound() throws Exception {
        when(mockSelectRs.next()).thenReturn(false);

        assertNull(model.findByPK(99L));
    }

    @Test
    void testFindByName_found() throws Exception {
        when(mockSelectRs.next()).thenReturn(true, false);
        stubSubjectRs(mockSelectRs, 2L, "Physics", 1L);

        SubjectBean result = model.findByName("Physics");

        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals("Physics", result.getName());
    }

    @Test
    void testFindByName_notFound() throws Exception {
        when(mockSelectRs.next()).thenReturn(false);

        assertNull(model.findByName("Unknown"));
    }

    @Test
    void testUpdate_success() throws Exception {
        when(mockSelectRs.next()).thenReturn(false); // findByName → no duplicate

        SubjectBean bean = buildBean("Chemistry", 1L);
        bean.setId(1L);
        model.update(bean);

        verify(mockDMLPstmt).executeUpdate();
        verify(mockConn).commit();
    }

    @Test
    void testUpdate_throwsDuplicateWhenNameTakenByOther() throws Exception {
        when(mockSelectRs.next()).thenReturn(true, false);
        stubSubjectRs(mockSelectRs, 99L, "Chemistry", 1L);

        SubjectBean bean = buildBean("Chemistry", 1L);
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
        when(mockSelectRs.getString("NAME")).thenReturn("Mathematics", "Physics");
        when(mockSelectRs.getString("DESCRIPTION")).thenReturn("Numbers", "Mechanics");
        when(mockSelectRs.getLong("COURSE_ID")).thenReturn(1L, 1L);
        // Also stub index-based accessors for backwards compatibility
        when(mockSelectRs.getString(2)).thenReturn("Mathematics", "Physics");
        when(mockSelectRs.getString(3)).thenReturn("Numbers", "Mechanics");
        when(mockSelectRs.getLong(4)).thenReturn(1L, 1L);

        List<SubjectBean> list = model.list(1, 10);

        assertEquals(2, list.size());
        assertEquals("Mathematics", list.get(0).getName());
        assertEquals("Physics", list.get(1).getName());
    }

    @Test
    void testSearch_byName_returnsMatchingRecords() throws Exception {
        when(mockSelectRs.next()).thenReturn(true, false);
        stubSubjectRs(mockSelectRs, 3L, "Physics", 1L);

        SubjectBean criteria = new SubjectBean();
        criteria.setName("Physics");
        List<SubjectBean> list = model.search(criteria, 1, 10);

        assertEquals(1, list.size());
        assertEquals("Physics", list.get(0).getName());
    }

    private SubjectBean buildBean(String name, long courseId) {
        SubjectBean bean = new SubjectBean();
        bean.setName(name);
        bean.setDescription("Topic description");
        bean.setCourseId(courseId);
        bean.setCreatedBy("Admin");
        bean.setModifiedBy("Admin");
        bean.setCreatedDatetime(new Timestamp(System.currentTimeMillis()));
        bean.setModifiedDatetime(new Timestamp(System.currentTimeMillis()));
        return bean;
    }

    private void stubSubjectRs(ResultSet rs, long id, String name, long courseId) throws Exception {
        when(rs.getLong("ID")).thenReturn(id);
        when(rs.getString("CREATED_BY")).thenReturn("Admin");
        when(rs.getString("MODIFIED_BY")).thenReturn("Admin");
        when(rs.getTimestamp("CREATED_DATETIME")).thenReturn(null);
        when(rs.getTimestamp("MODIFIED_DATETIME")).thenReturn(null);
        when(rs.getString("NAME")).thenReturn(name);
        when(rs.getString("DESCRIPTION")).thenReturn("Topic description");
        when(rs.getLong("COURSE_ID")).thenReturn(courseId);
        // Also stub index-based accessors for list() method
        when(rs.getString(2)).thenReturn(name);
        when(rs.getString(3)).thenReturn("Topic description");
        when(rs.getLong(4)).thenReturn(courseId);
    }
}
