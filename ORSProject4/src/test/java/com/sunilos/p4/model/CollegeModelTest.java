package com.sunilos.p4.model;

import com.sunilos.p4.bean.CollegeBean;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CollegeModelTest {

    private CollegeModel model;
    private Connection mockConn;
    private PreparedStatement mockSelectPstmt;
    private PreparedStatement mockMaxPKPstmt;
    private PreparedStatement mockDMLPstmt;
    private ResultSet mockSelectRs;
    private ResultSet mockMaxPKRs;
    private MockedStatic<JDBCDataSource> mockJDBC;

    @BeforeEach
    void setUp() throws Exception {
        model = new CollegeModel();
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

        // Route each SQL to the correct PreparedStatement mock (case-insensitive SELECT)
        when(mockConn.prepareStatement(anyString())).thenAnswer(inv -> {
            String sql = ((String) inv.getArgument(0)).toUpperCase();
            if (sql.contains("MAX(ID)")) return mockMaxPKPstmt;
            if (sql.contains("SELECT"))  return mockSelectPstmt;
            return mockDMLPstmt; // INSERT / UPDATE / DELETE
        });

        mockJDBC = mockStatic(JDBCDataSource.class);
        mockJDBC.when(JDBCDataSource::getConnection).thenReturn(mockConn);
    }

    @AfterEach
    void tearDown() {
        mockJDBC.close();
    }

    // ── add ──────────────────────────────────────────────────────────────────

    @Test
    void testAdd_success() throws Exception {
        when(mockSelectRs.next()).thenReturn(false); // findByName → no duplicate

        long pk = model.add(buildBean("MIT"));

        assertEquals(6L, pk);
        verify(mockDMLPstmt).executeUpdate();
        verify(mockConn).commit();
    }

    @Test
    void testAdd_throwsDuplicateWhenNameExists() throws Exception {
        when(mockSelectRs.next()).thenReturn(true, false); // findByName finds one row
        stubCollegeRs(mockSelectRs, 99L, "MIT");

        assertThrows(DuplicateRecordException.class, () -> model.add(buildBean("MIT")));
        verify(mockDMLPstmt, never()).executeUpdate();
    }

    // ── delete ───────────────────────────────────────────────────────────────

    @Test
    void testDelete_success() throws Exception {
        CollegeBean bean = new CollegeBean();
        bean.setId(1L);

        model.delete(bean);

        verify(mockDMLPstmt).executeUpdate();
        verify(mockConn).commit();
    }

    // ── findByPK ─────────────────────────────────────────────────────────────

    @Test
    void testFindByPK_found() throws Exception {
        when(mockSelectRs.next()).thenReturn(true, false);
        stubCollegeRs(mockSelectRs, 1L, "MIT");

        CollegeBean result = model.findByPK(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("MIT", result.getName());
    }

    @Test
    void testFindByPK_notFound() throws Exception {
        when(mockSelectRs.next()).thenReturn(false);

        assertNull(model.findByPK(99L));
    }

    // ── findByName ────────────────────────────────────────────────────────────

    @Test
    void testFindByName_found() throws Exception {
        when(mockSelectRs.next()).thenReturn(true, false);
        stubCollegeRs(mockSelectRs, 2L, "LNCT");

        CollegeBean result = model.findByName("LNCT");

        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals("LNCT", result.getName());
    }

    @Test
    void testFindByName_notFound() throws Exception {
        when(mockSelectRs.next()).thenReturn(false);

        assertNull(model.findByName("Unknown"));
    }

    // ── update ────────────────────────────────────────────────────────────────

    @Test
    void testUpdate_success() throws Exception {
        when(mockSelectRs.next()).thenReturn(false); // findByName → no duplicate

        CollegeBean bean = buildBean("OIT");
        bean.setId(1L);
        model.update(bean);

        verify(mockDMLPstmt).executeUpdate();
        verify(mockConn).commit();
    }

    @Test
    void testUpdate_throwsDuplicateWhenNameTakenByOther() throws Exception {
        when(mockSelectRs.next()).thenReturn(true, false);
        stubCollegeRs(mockSelectRs, 99L, "OIT"); // different ID

        CollegeBean bean = buildBean("OIT");
        bean.setId(1L);

        assertThrows(DuplicateRecordException.class, () -> model.update(bean));
        verify(mockDMLPstmt, never()).executeUpdate();
    }

    // ── list ──────────────────────────────────────────────────────────────────

    @Test
    void testList_returnsAllRecords() throws Exception {
        when(mockSelectRs.next()).thenReturn(true, true, false);
        when(mockSelectRs.getLong("ID")).thenReturn(1L, 2L);
        when(mockSelectRs.getString("CREATED_BY")).thenReturn("admin");
        when(mockSelectRs.getString("MODIFIED_BY")).thenReturn("admin");
        when(mockSelectRs.getTimestamp("CREATED_DATETIME")).thenReturn(null);
        when(mockSelectRs.getTimestamp("MODIFIED_DATETIME")).thenReturn(null);
        when(mockSelectRs.getString(2)).thenReturn("MIT", "LNCT");
        when(mockSelectRs.getString(3)).thenReturn("Bhopal", "Bhopal");
        when(mockSelectRs.getString(4)).thenReturn("MP", "MP");
        when(mockSelectRs.getString(5)).thenReturn("Bhopal", "Indore");
        when(mockSelectRs.getString(6)).thenReturn("0755111", "0731222");

        List<CollegeBean> list = model.list(1, 10);

        assertEquals(2, list.size());
        assertEquals("MIT", list.get(0).getName());
        assertEquals("LNCT", list.get(1).getName());
    }

    // ── search ────────────────────────────────────────────────────────────────

    @Test
    void testSearch_byName_returnsMatchingRecords() throws Exception {
        when(mockSelectRs.next()).thenReturn(true, false);
        stubCollegeRs(mockSelectRs, 3L, "LNCT");

        CollegeBean criteria = new CollegeBean();
        criteria.setName("LNCT");
        List<CollegeBean> list = model.search(criteria, 1, 10);

        assertEquals(1, list.size());
        assertEquals("LNCT", list.get(0).getName());
    }

    // ── helpers ───────────────────────────────────────────────────────────────

    private CollegeBean buildBean(String name) {
        CollegeBean bean = new CollegeBean();
        bean.setName(name);
        bean.setAddress("Bhopal");
        bean.setState("MP");
        bean.setCity("Bhopal");
        bean.setPhoneNo("0755111222");
        bean.setCreatedBy("Admin");
        bean.setModifiedBy("Admin");
        bean.setCreatedDatetime(new Timestamp(System.currentTimeMillis()));
        bean.setModifiedDatetime(new Timestamp(System.currentTimeMillis()));
        return bean;
    }

    private void stubCollegeRs(ResultSet rs, long id, String name) throws Exception {
        when(rs.getLong("ID")).thenReturn(id);
        when(rs.getString("CREATED_BY")).thenReturn("Admin");
        when(rs.getString("MODIFIED_BY")).thenReturn("Admin");
        when(rs.getTimestamp("CREATED_DATETIME")).thenReturn(null);
        when(rs.getTimestamp("MODIFIED_DATETIME")).thenReturn(null);
        when(rs.getString(2)).thenReturn(name);
        when(rs.getString(3)).thenReturn("Bhopal");
        when(rs.getString(4)).thenReturn("MP");
        when(rs.getString(5)).thenReturn("Bhopal");
        when(rs.getString(6)).thenReturn("0755111222");
    }
}
