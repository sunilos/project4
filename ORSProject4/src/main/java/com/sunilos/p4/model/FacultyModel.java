package com.sunilos.p4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.log4j.Logger;

import com.sunilos.p4.bean.CollegeBean;
import com.sunilos.p4.bean.FacultyBean;
import com.sunilos.p4.exception.ApplicationException;
import com.sunilos.p4.exception.DuplicateRecordException;
import com.sunilos.p4.util.JDBCDataSource;

public class FacultyModel extends BaseModel<FacultyBean> {

    private static Logger log = Logger.getLogger(FacultyModel.class);

    public FacultyBean findByEmail(String email) throws ApplicationException {
        return findByUniqueColumn("EMAIL", email);
    }

    @Override
    public long add(FacultyBean bean) throws ApplicationException, DuplicateRecordException {
        log.debug("FacultyModel add Started");

        FacultyBean duplicate = findByEmail(bean.getEmail());
        if (duplicate != null) {
            throw new DuplicateRecordException("Email already exists");
        }

        CollegeModel cModel = new CollegeModel();
        CollegeBean college = cModel.findByPK(bean.getCollegeId());
        bean.setCollegeName(college.getName());

        Connection conn = null;
        int pk = 0;
        try {
            pk = nextPK();
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO ST_FACULTY(ID,COLLEGE_ID,COLLEGE_NAME,FIRST_NAME,LAST_NAME,EMAIL,MOBILE_NO,ADDRESS,GENDER,DATE_OF_BIRTH,CREATED_BY,MODIFIED_BY,CREATED_DATETIME,MODIFIED_DATETIME)"
              + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,NOW(),NOW())");
            pstmt.setInt(1, pk);
            pstmt.setLong(2, bean.getCollegeId());
            pstmt.setString(3, bean.getCollegeName());
            pstmt.setString(4, bean.getFirstName());
            pstmt.setString(5, bean.getLastName());
            pstmt.setString(6, bean.getEmail());
            pstmt.setString(7, bean.getMobileNo());
            pstmt.setString(8, bean.getAddress());
            pstmt.setString(9, bean.getGender());
            pstmt.setDate(10, new java.sql.Date(bean.getDob().getTime()));
            pstmt.setString(11, bean.getCreatedBy());
            pstmt.setString(12, bean.getModifiedBy());
            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            JDBCDataSource.rollBack(conn);
            throw new ApplicationException("Exception in add Faculty");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("FacultyModel add End");
        return pk;
    }

    @Override
    public void update(FacultyBean bean) throws ApplicationException, DuplicateRecordException {
        log.debug("FacultyModel update Started");

        FacultyBean existing = findByEmail(bean.getEmail());
        if (existing != null && existing.getId() != bean.getId()) {
            throw new DuplicateRecordException("Email already exists");
        }

        CollegeModel cModel = new CollegeModel();
        CollegeBean college = cModel.findByPK(bean.getCollegeId());
        bean.setCollegeName(college.getName());

        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement(
                "UPDATE ST_FACULTY SET COLLEGE_ID=?,COLLEGE_NAME=?,FIRST_NAME=?,LAST_NAME=?,EMAIL=?,MOBILE_NO=?,ADDRESS=?,GENDER=?,DATE_OF_BIRTH=? WHERE ID=?");
            pstmt.setLong(1, bean.getCollegeId());
            pstmt.setString(2, bean.getCollegeName());
            pstmt.setString(3, bean.getFirstName());
            pstmt.setString(4, bean.getLastName());
            pstmt.setString(5, bean.getEmail());
            pstmt.setString(6, bean.getMobileNo());
            pstmt.setString(7, bean.getAddress());
            pstmt.setString(8, bean.getGender());
            pstmt.setDate(9, new java.sql.Date(bean.getDob().getTime()));
            pstmt.setLong(10, bean.getId());
            pstmt.executeUpdate();
            updatedTimestamp(bean.getId(), conn);
            conn.commit();
            pstmt.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            JDBCDataSource.rollBack(conn);
            throw new ApplicationException("Exception in update Faculty");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("FacultyModel update End");
    }

    @Override
    public String getWhereClause(FacultyBean bean) {
        StringBuffer sql = new StringBuffer();
        if (bean != null) {
            if (bean.getId() > 0) {
                sql.append(" AND ID = " + bean.getId());
            }
            if (bean.getFirstName() != null && bean.getFirstName().length() > 0) {
                sql.append(" AND FIRST_NAME like '" + bean.getFirstName() + "%'");
            }
            if (bean.getLastName() != null && bean.getLastName().length() > 0) {
                sql.append(" AND LAST_NAME like '" + bean.getLastName() + "%'");
            }
            if (bean.getEmail() != null && bean.getEmail().length() > 0) {
                sql.append(" AND EMAIL like '" + bean.getEmail() + "%'");
            }
            if (bean.getCollegeId() > 0) {
                sql.append(" AND COLLEGE_ID = " + bean.getCollegeId());
            }
        }
        return sql.toString();
    }

    @Override
    public String getTable() { return "ST_FACULTY"; }

    @Override
    public FacultyBean getBean() { return new FacultyBean(); }
}
