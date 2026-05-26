package com.sunilos.p4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.sunilos.p4.bean.CourseBean;
import com.sunilos.p4.exception.ApplicationException;
import com.sunilos.p4.exception.DuplicateRecordException;
import com.sunilos.p4.util.JDBCDataSource;

/**
 * JDBC Implementation of Course Model
 */
public class CourseModel extends BaseModel<CourseBean> {

    private static Logger log = Logger.getLogger(CourseModel.class);

    public CourseBean findByName(String name) throws ApplicationException {
        return findByUniqueColumn("NAME", name);
    }

    @Override
    public long add(CourseBean bean) throws ApplicationException, DuplicateRecordException {

        String colums = "ID,NAME, DESCRIPTION, DURATION";
        String values = "?,?,?,?";

        StringBuffer sql = new StringBuffer("INSERT INTO " + getTable());
        sql.append("(CREATED_DATETIME,MODIFIED_DATETIME,CREATED_BY,MODIFIED_BY, " + colums + ")");
        sql.append(" VALUES(NOW(),NOW(),'root@sunilos.com','root@sunilos.com'," + values + " )");

        checkDuplicate(bean);

        Connection conn = null;

        int pk = 0;

        try {

            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false); // Begin transaction

            pk = nextPK();

            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setInt(1, pk);
            pstmt.setString(2, bean.getName());
            pstmt.setString(3, bean.getDescription());
            pstmt.setString(4, bean.getDuration());
            pstmt.executeUpdate();

            conn.commit(); // End transaction
            pstmt.close();

        } catch (SQLException e) {
            JDBCDataSource.rollBack(conn);
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return pk;
    }

    @Override
    public void update(CourseBean bean) throws ApplicationException, DuplicateRecordException {

        String sql = "UPDATE " + getTable() + " SET NAME=?,DESCRIPTION=?,DURATION=? WHERE ID=?";

        checkDuplicate(bean);

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false); // Begin transaction

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, bean.getName());
            pstmt.setString(2, bean.getDescription());
            pstmt.setString(3, bean.getDuration());
            pstmt.setLong(4, bean.getId());
            pstmt.executeUpdate();

            updatedTimestamp(bean.getId(), conn);

            conn.commit(); // End transaction
            pstmt.close();

        } catch (SQLException e) {
            JDBCDataSource.rollBack(conn);
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    @Override
    public String getWhereClause(CourseBean bean) {

        StringBuffer sql = new StringBuffer();

        if (bean != null) {
            if (bean.getId() > 0) {
                sql.append(" AND id = " + bean.getId());
            }
            if (bean.getName() != null && bean.getName().length() > 0) {
                sql.append(" AND NAME like '" + bean.getName() + "%'");
            }
            if (bean.getDescription() != null && bean.getDescription().length() > 0) {
                sql.append(" AND DESCRIPTION like '" + bean.getDescription() + "%'");
            }
            if (bean.getDuration() != null && bean.getDuration().length() > 0) {
                sql.append(" AND DURATION like '" + bean.getDuration() + "%'");
            }

        }

        return sql.toString();
    }

    @Override
    public void checkDuplicate(CourseBean bean) {

        CourseBean duplicateBean = this.findByName(bean.getName());

        if (duplicateBean != null && duplicateBean.getId() != bean.getId()) {
            throw new DuplicateRecordException("Course already exists");
        }

        if (bean.getId() == 0 && duplicateBean != null) {
            throw new DuplicateRecordException("Course already exists");
        }
    }

    @Override
    public String getTable() {
        return "ST_COURSE";
    }

    @Override
    public CourseBean getBean() {
        return new CourseBean();
    }
}
