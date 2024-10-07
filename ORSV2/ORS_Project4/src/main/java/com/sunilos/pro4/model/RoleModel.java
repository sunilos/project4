package com.sunilos.pro4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sunilos.pro4.bean.RoleBean;
import com.sunilos.pro4.exception.ApplicationException;
import com.sunilos.pro4.exception.DatabaseException;
import com.sunilos.pro4.exception.DuplicateRecordException;
import com.sunilos.pro4.util.JDBCDataSource;

/**
 * JDBC Implementation of Role Model.
 * 
 * @author Sunil OS
 *
 */
public class RoleModel {

	private static Logger log = Logger.getLogger(RoleModel.class);

	public Integer nextPK() throws DatabaseException {
		log.debug("Model nextPK Started");
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM ST_ROLE");

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);

			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new DatabaseException("Exception : Exception in getting PK");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Modal nextPK End");
		return pk + 1;
	}

	public long add(RoleBean bean) throws ApplicationException, DuplicateRecordException {

		log.debug("Modal add Started");
		Connection conn = null;
		int pk = 0;

//		RoleBean duplicateRole=findByName(bean.getName());
//		
//		if(duplicateRole !=null){
//			throw new DuplicateRecordException("Role already exists");
//			
//		}
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();

			System.out.println(pk + "in ModelJDBC");
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ST_ROLE VALUES(?,?,?,?,?,?,?)");

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getDescription());
			pstmt.setString(4, bean.getCreatedBy());
			pstmt.setString(5, bean.getModifiedBy());
			pstmt.setTimestamp(6, bean.getCreatedDatetime());
			pstmt.setTimestamp(7, bean.getModifiedDatetime());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("Database Exception...",e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				// throw new ApplicationException("Exception : add rollback
				// exception"+ex.getMessage());
			}
			// throw new ApplicationException("Exception :Exception in add Role");
		} finally {
			JDBCDataSource.closeConnection(conn);

		}
		log.debug("Modal add End");
		return pk;
	}

	public void delete(RoleBean bean) throws ApplicationException {

		log.debug("Modal delete Started");
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("Delete FROM ST_ROLE WHERE ID=?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			log.error("Database Exception...", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				// throw new ApplicationException("Exception : Delete rollback
				// exception"+ex.getMessage());
			}
			// throw new ApplicationException("Exception : Exception in delete Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Modal delete Started");
	}

	public RoleBean findByName(String name) throws ApplicationException {
		log.debug("Modal findBy EmailId Started");
		StringBuffer sql = new StringBuffer("SELECT*FROM ST_ROLE WHERE NAME=?");

		RoleBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, name);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new RoleBean();
				bean.setId(1);
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreatedDatetime(rs.getTimestamp(6));
				bean.setModifiedDatetime(rs.getTimestamp(7));
			}
			rs.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			// throw new ApplicationException("Exception : Exception in geting User by
			// emailId");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Modal findBy EmailId End");
		return bean;

	}

	public RoleBean findByPK(long pk) throws ApplicationException {
		log.debug("Modal findByPK Started");

		StringBuffer sql = new StringBuffer("SELECT* FROM ST_ROLE WHERE ID=?");
		RoleBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new RoleBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreatedDatetime(rs.getTimestamp(6));
				bean.setModifiedDatetime(rs.getTimestamp(7));

			}

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting User by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Modal findByPK End");
		return bean;
	}
//@Transactional
	public void update(RoleBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model update Started");
		Connection conn = null;

		/*
		 * RoleBean duplicataRole = findByName(bean.getName()); // Check if updated Role
		 * already exist if (duplicataRole != null && duplicataRole.getId() !=
		 * bean.getId()) { throw new DuplicateRecordException("Role already exists"); }
		 */
		try {
			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ST_ROLE SET NAME=?,DESCRIPTION=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getDescription());
			pstmt.setString(3, bean.getCreatedBy());
			pstmt.setString(4, bean.getModifiedBy());
			pstmt.setTimestamp(5, bean.getCreatedDatetime());
			pstmt.setTimestamp(6, bean.getModifiedDatetime());
			pstmt.setLong(7, bean.getId());
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
			throw new ApplicationException("Exception in updating Role ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model update End");
	}

	public List search(RoleBean bean) throws ApplicationException {
		return search(bean);
	}

	public List search(RoleBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model search Started");
		StringBuffer sql = new StringBuffer("SELECT*FROM ST_ROLE WHERE 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id= " + bean.getId());
			}
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND NAME like '" + bean.getName() + "%'");
			}
			if (bean.getDescription() != null && bean.getDescription().length() > 0) {
				sql.append(" AND DESCRIPTION like '" + bean.getDescription() + "%'");
			}

		}
		System.out.println(sql);

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" Limit " + pageNo + "," + pageSize);
			

		}
		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new RoleBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreatedDatetime(rs.getTimestamp(6));
				bean.setModifiedDatetime(rs.getTimestamp(7));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			// log.error("DatabaseException.....", e);
			// throw new ApplicationException("Exception : Exception in search Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model search End");
		return list;
	}

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list Started");

		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from ST_ROLE");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + " , " + pageSize);
		}

		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				RoleBean bean = new RoleBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreatedDatetime(rs.getTimestamp(6));
				bean.setModifiedDatetime(rs.getTimestamp(7));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error(" Database Exception....", e);
			// throw new ApplicationException("Exception : Exception Geting list of Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model list End");
		return list;
	}

}
