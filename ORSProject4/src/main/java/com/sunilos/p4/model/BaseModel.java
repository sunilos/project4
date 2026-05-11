package com.sunilos.p4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sunilos.p4.bean.BaseBean;
import com.sunilos.p4.exception.ApplicationException;
import com.sunilos.p4.exception.DatabaseException;
import com.sunilos.p4.exception.DuplicateRecordException;
import com.sunilos.p4.util.JDBCDataSource;

/**
 * It contains generalized methods for a Model class. �All Model classes in the
 * application must inherit BasModel class.
 * 
 * @author Sunil Sahu
 *
 * @param <T>
 */
public abstract class BaseModel<T extends BaseBean> {

	protected static Logger log = Logger.getLogger(BaseModel.class);

	protected void updatedTimestamp(long id, Connection conn) throws SQLException {
		String createdBy = "root@sunilos.com";
		String modifiedBy = "root@sunilos.com";
		StringBuffer sql = new StringBuffer(" UPDATE " + getTable() + " SET  MODIFIED_DATETIME = NOW(), MODIFIED_BY = '"
				+ modifiedBy + "' " + "WHERE ID = " + id);
		Statement stmt = conn.createStatement();
		stmt.execute(sql.toString());
	}

	/**
	 * Creates the next primary key of the table. A primary key is a unique
	 * auto-generated integer number that represents a non-business primary key.
	 * 
	 * @return
	 * @throws DatabaseException
	 */
	public Integer nextPK() throws DatabaseException {

		log.debug("Model nextPK Started");
		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM " + getTable());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();

		} catch (SQLException e) {
			log.error("Database Exception..", e);
			throw new DatabaseException("Exception : Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model nextPK End");
		return pk + 1;

	}

	/**
	 * Finds and returns a record by primary key.
	 * 
	 * @param pk
	 * @return
	 * @throws ApplicationException
	 */

	public T findByPK(long pk) throws ApplicationException {
		log.debug("Model findByPK Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM " + getTable() + " WHERE ID=?");
		T bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = getBean();
				bean.setResultset(rs);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting User by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByPK End");
		return bean;
	}

	public T findByUniqueColumn(String column, String value) throws ApplicationException {

		log.debug("Model findBy EmailId Started");

		StringBuffer sql = new StringBuffer("SELECT * FROM " + getTable() + " WHERE " + column + "='" + value + "'");

		T bean = null;

		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = getBean();
				bean.setResultset(rs);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting User by emailId");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findBy EmailId End");
		return bean;
	}

	/**
	 * Adds a new record in the table
	 * 
	 * @param bean
	 * @return
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public abstract long add(T bean) throws ApplicationException, DuplicateRecordException;

	/**
	 * Updates a record in the table using its primary key.
	 * 
	 * @param bean
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public abstract void update(T bean) throws ApplicationException, DuplicateRecordException;

	/**
	 * Deletes a record in the table using its primary key.
	 * 
	 * @param bean
	 * @throws ApplicationException
	 */
	public void delete(T bean) throws ApplicationException {

		log.debug("Model delete Started");

		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM " + getTable() + " WHERE ID=?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model delete Started");
	}

	/**
	 * Delete a record by primary key
	 * 
	 * @param id
	 * @throws ApplicationException
	 */
	public void delete(int id) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM " + getTable() + " WHERE ID=?");
			pstmt.setLong(1, id);
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model delete Started");
	}

	/**
	 * Searches record on the basis of not-null values passed in the bean object.
	 * 
	 * @param bean
	 * @return
	 * @throws ApplicationException
	 */
	public List search(T bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	/**
	 * Searches record on the basis of not-null values passed in the bean object. It
	 * returns the number of records as per page size for a given page number.
	 * 
	 * @param bean
	 * @return
	 * @throws ApplicationException
	 */
	public List<T> search(T bean, int pageNo, int pageSize) throws ApplicationException {

		log.debug("Model search Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM " + getTable() + " WHERE 1=1");
		sql.append(this.getWhereClause(bean));

		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" Limit " + pageNo + ", " + pageSize);
			// sql.append(" limit " + pageNo + "," + pageSize);
		}

		ArrayList<T> list = new ArrayList<T>();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = getBean();
				bean.setResultset(rs);
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in search Student");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model search End");
		return list;
	}

	/**
	 * Returns all records of a table.
	 * 
	 * @param bean
	 * @return
	 * @throws ApplicationException
	 */
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 * Returns all records of a table. It returns the number of records as per page
	 * size for a given page number.
	 * 
	 * @param bean
	 * @return
	 * @throws ApplicationException
	 */
	public List<T> list(int pageNo, int pageSize) throws ApplicationException {

		log.debug("Model list Started");
		ArrayList list = new ArrayList();

		StringBuffer sql = new StringBuffer("select * from " + getTable());

		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				T bean = getBean();
				bean.setResultset(rs);
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting list of users");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model list End");
		return list;

	}

	public abstract String getWhereClause(T bean);

	/**
	 * Returns the name of the table of current model.
	 * 
	 * @return
	 */
	public abstract String getTable();

	public abstract T getBean();

	public void checkDuplicate(T bean) {
		// TODO
	}

}
