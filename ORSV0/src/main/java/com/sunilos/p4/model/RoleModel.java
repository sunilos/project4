package com.sunilos.p4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.sunilos.p4.bean.RoleBean;
import com.sunilos.p4.exception.ApplicationException;
import com.sunilos.p4.exception.DatabaseException;
import com.sunilos.p4.exception.DuplicateRecordException;
import com.sunilos.p4.util.JDBCDataSource;

/**
 * JDBC Implementation of Role Model
 * 
 * @author Rays Technologies
 * @version 1.0
 * @Copyright (c) Rays Technologies
 */
public class RoleModel extends BaseModel<RoleBean> {

	private static Logger log = Logger.getLogger(RoleModel.class);

	/**
	 * Find User by Role
	 * 
	 * @param name : get parameter
	 * @return bean
	 * @throws DatabaseException
	 */

	public RoleBean findByName(String name) throws ApplicationException {
		return findByUniqueColumn("NAME", name);
	}

	/**
	 * Add a Role
	 * 
	 * @param bean
	 * @throws DatabaseException
	 * 
	 */
	@Override
	public long add(RoleBean bean) throws ApplicationException, DuplicateRecordException {

		String colums = "ID,NAME, DESCRIPTION";
		String values = "?,?,?";

		StringBuffer sql = new StringBuffer("INSERT INTO " + getTable());
		sql.append("(CREATED_DATETIME,MODIFIED_DATETIME,CREATED_BY,MODIFIED_BY, " + colums + ")");
		sql.append(" VALUES(NOW(),NOW(),'root@sunilos.com','root@sunilos.com'," + values + " )");

		System.out.println(sql);

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

	/**
	 * Update a Role
	 * 
	 * @param bean
	 * @throws DatabaseException
	 */

	@Override
	public void update(RoleBean bean) throws ApplicationException, DuplicateRecordException {

		String sql = "UPDATE " + getTable() + " SET NAME=?,DESCRIPTION=? WHERE ID=?";

		checkDuplicate(bean);

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getDescription());
			pstmt.setLong(3, bean.getId());
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
	public String getWhereClause(RoleBean bean) {

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

		}

		return sql.toString();
	}

	@Override
	public void checkDuplicate(RoleBean bean) {

		RoleBean duplicateBean = this.findByName(bean.getName());

		// Check if create Role already exist
		if (duplicateBean != null && duplicateBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Role already exists");
		}

		if (bean.getId() == 0 && duplicateBean != null) {
			throw new DuplicateRecordException("Role already exists");
		}
	}

	@Override
	public String getTable() {
		return "ST_ROLE";
	}

	@Override
	public RoleBean getBean() {
		return new RoleBean();
	}
}
