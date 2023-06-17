package in.co.sunrays.proj4.model;

import in.co.sunrays.proj4.bean.MarksheetBean;
import in.co.sunrays.proj4.bean.StudentBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.exception.DatabaseException;
import in.co.sunrays.proj4.exception.DuplicateRecordException;
import in.co.sunrays.proj4.util.JDBCDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * JDBC Implementation of Marksheet Model
 * 
 * @author SUNRAYS Technologies
 * @version 1.0
 * @Copyright (c) SUNRAYS Technologies
 */
public class MarksheetModel {

	Logger log = Logger.getLogger(MarksheetModel.class);

	public Integer nextPK() throws DatabaseException {
		log.debug("Model nextPK Started");
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			System.out.println("Connection Succesfully Establish");

			PreparedStatement pstmt = conn
					.prepareStatement("select max(ID) from ST_MARKSHEET");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();

		} catch (Exception e) {
			log.error(e);
			throw new DatabaseException("Exception in Marksheet getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model nextPK End");
		return pk + 1;
	}

	/**
	 * Adds a Marksheet
	 * 
	 * @param bean
	 * @throws DatabaseException
	 * 
	 */

	public long add(MarksheetBean bean) throws ApplicationException,
			DuplicateRecordException {

		log.debug("Model add Started");

		Connection conn = null;

		// get Student Name
		StudentModel sModel = new StudentModel();
		StudentBean studentbean = sModel.findByPK(bean.getStudentId());
		bean.setName(studentbean.getFirstName() + " " + studentbean.getLastName());

		MarksheetBean duplicateMarksheet = findByRollNo(bean.getRollNo());
		int pk = 0;

		if (duplicateMarksheet != null) {
			throw new DuplicateRecordException("Roll Number already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();

			// Get auto-generated next primary key
			pk = nextPK();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn
					.prepareStatement("INSERT INTO ST_MARKSHEET VALUES(?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getRollNo());
			pstmt.setLong(3, bean.getStudentId());
			pstmt.setString(4, bean.getName());
			pstmt.setInt(5, bean.getPhysics());
			pstmt.setInt(6, bean.getChemistry());
			pstmt.setInt(7, bean.getMaths());
			pstmt.setString(8, bean.getCreatedBy());
			pstmt.setString(9, bean.getModifiedBy());
			pstmt.setTimestamp(10, bean.getCreatedDatetime());
			pstmt.setTimestamp(11, bean.getModifiedDatetime());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			log.error(e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("add rollback exception "
						+ ex.getMessage());
			}
			throw new ApplicationException("Exception in add marksheet");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add End");
		return pk;
	}

	/**
	 * Deletes a Marksheet
	 * 
	 * @param bean
	 * @throws DatabaseException
	 */
	public void delete(MarksheetBean bean) throws ApplicationException {

		log.debug("Model delete Started");

		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn
					.prepareStatement("DELETE FROM ST_MARKSHEET WHERE ID=?");
			pstmt.setLong(1, bean.getId());
			System.out.println("Deleted MarkSheet");
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();

		} catch (Exception e) {
			log.error(e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				log.error(ex);
				throw new ApplicationException("Delete rollback exception "
						+ ex.getMessage());
			}
			throw new ApplicationException("Exception in delete marksheet");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model delete End");
	}

	/**
	 * Finds Marksheet by Roll No
	 * 
	 * @param rollNo
	 *            : get parameter
	 * @return bean
	 * @throws DuplicateRecordException
	 */
	
	public MarksheetBean findByRollNo(String rollNo) throws ApplicationException {
		log.debug("Model findByRollNo Started");

		StringBuffer sql = new StringBuffer(
				"SELECT * FROM ST_MARKSHEET WHERE ROLL_NO=?");
		MarksheetBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, rollNo);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new MarksheetBean();
				bean.setId(rs.getLong(1));
				bean.setRollNo(rs.getString(2));
				bean.setStudentId(rs.getLong(3));
				bean.setName(rs.getString(4));
				bean.setPhysics(rs.getInt(5));
				bean.setChemistry(rs.getInt(6));
				bean.setMaths(rs.getInt(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
			}
			rs.close();
		} catch (Exception e) {
			log.error(e);
			throw new ApplicationException(
					"Exception in getting marksheet by roll no");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model findByRollNo End");
		return bean;
	}

	/**
	 * Finds Marksheet by PK
	 * 
	 * @param pk
	 *            : get parameter
	 * @return bean
	 * @throws DatabaseException
	 */

	
	public MarksheetBean findByPK(long pk) throws ApplicationException {
		log.debug("Model findByPK Started");

		StringBuffer sql = new StringBuffer(
				"SELECT * FROM ST_MARKSHEET WHERE ID=?");
		MarksheetBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new MarksheetBean();
				bean.setId(rs.getLong(1));
				bean.setRollNo(rs.getString(2));
				bean.setStudentId(rs.getLong(3));
				bean.setName(rs.getString(4));
				bean.setPhysics(rs.getInt(5));
				bean.setChemistry(rs.getInt(6));
				bean.setMaths(rs.getInt(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));

			}
			rs.close();
		} catch (Exception e) {
			log.error(e);
			throw new ApplicationException(
					"Exception in getting marksheet by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByPK End");
		return bean;
	}

	/**
	 * Updates a Marksheet
	 * 
	 * @param bean
	 * @throws DatabaseException
	 */
	
	public void update(MarksheetBean bean) throws ApplicationException,
			DuplicateRecordException {

		log.debug("Model update Started");

		Connection conn = null;
		MarksheetBean beanExist = findByRollNo(bean.getRollNo());

		// Check if updated Roll no already exist
		if (beanExist != null && beanExist.getId() != bean.getId()) {
			throw new DuplicateRecordException("Roll No is already exist");
		}

		// get Student Name
		StudentModel sModel = new StudentModel();
		StudentBean studentbean = sModel.findByPK(bean.getStudentId());
		bean.setName(studentbean.getFirstName() + " " + studentbean.getLastName());

		try {
			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn
					.prepareStatement("UPDATE ST_MARKSHEET SET ROLL_NO=?,STUDENT_ID=?,NAME=?,PHYSICS=?,CHEMISTRY=?,MATHS=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			pstmt.setString(1, bean.getRollNo());
			pstmt.setLong(2, bean.getStudentId());
			pstmt.setString(3, bean.getName());
			pstmt.setInt(4, bean.getPhysics());
			pstmt.setInt(5, bean.getChemistry());
			pstmt.setInt(6, bean.getMaths());
			pstmt.setString(7, bean.getCreatedBy());
			pstmt.setString(8, bean.getModifiedBy());
			pstmt.setTimestamp(9, bean.getCreatedDatetime());
			pstmt.setTimestamp(10, bean.getModifiedDatetime());
			pstmt.setLong(11, bean.getId());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			log.error(e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Update rollback exception "
						+ ex.getMessage());
			}
			throw new ApplicationException("Exception in updating Marksheet ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model update End");

	}

	/**
	 * Searches Marksheet
	 * 
	 * @param bean
	 *            : Search Parameters
	 * @throws DatabaseException
	 */
	
	public List search(MarksheetBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	/**
	 * Searches Marksheet with pagination
	 * 
	 * @return list : List of Marksheets
	 * @param bean
	 *            : Search Parameters
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * 
	 * @throws DatabaseException
	 */
	
	public List search(MarksheetBean bean, int pageNo, int pageSize)
			throws ApplicationException {

		log.debug("Model  search Started");

		StringBuffer sql = new StringBuffer(
				"select * from ST_MARKSHEET where true");

		if (bean != null) {
			System.out.println("service" + bean.getName());
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			if (bean.getRollNo() != null && bean.getRollNo().length() > 0) {
				sql.append(" AND roll_no like '" + bean.getRollNo() + "%'");
			}
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND name like '" + bean.getName() + "%'");
			}
			if (bean.getPhysics() != null && bean.getPhysics() > 0) {
				sql.append(" AND physics = " + bean.getPhysics());
			}
			if (bean.getChemistry() != null && bean.getChemistry() > 0) {
				sql.append(" AND chemistry = " + bean.getChemistry());
			}
			if (bean.getMaths() != null && bean.getMaths() > 0) {
				sql.append(" AND maths = '" + bean.getMaths());
			}

		}

		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);
			// sql.append(" limit " + pageNo + "," + pageSize);
		}

		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new MarksheetBean();
				bean.setId(rs.getLong(1));
				bean.setRollNo(rs.getString(2));
				bean.setStudentId(rs.getLong(3));
				bean.setName(rs.getString(4));
				bean.setPhysics(rs.getInt(5));
				bean.setChemistry(rs.getInt(6));
				bean.setMaths(rs.getInt(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error(e);
			throw new ApplicationException("Update rollback exception "
					+ e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model  search End");
		return list;
	}

	/**
	 * Gets List of Marksheet
	 * 
	 * @return list : List of Marksheets
	 * @throws DatabaseException
	 */
	
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 * get List of Marksheet with pagination
	 * 
	 * @return list : List of Marksheets
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * @throws DatabaseException
	 */
	
	public List list(int pageNo, int pageSize) throws ApplicationException {

		log.debug("Model  list Started");

		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from ST_MARKSHEET");
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
				MarksheetBean bean = new MarksheetBean();
				bean.setId(rs.getLong(1));
				bean.setRollNo(rs.getString(2));
				bean.setStudentId(rs.getLong(3));
				bean.setName(rs.getString(4));
				bean.setPhysics(rs.getInt(5));
				bean.setChemistry(rs.getInt(6));
				bean.setMaths(rs.getInt(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error(e);
			throw new ApplicationException(
					"Exception in getting list of Marksheet");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model  list End");
		return list;

	}

	/**
	 * get Merit List of Marksheet with pagination
	 * 
	 * @return list : List of Marksheets
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * @throws DatabaseException
	 */
	
	public List getMeritList(int pageNo, int pageSize)
			throws ApplicationException {
		log.debug("Model  MeritList Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer(
				"SELECT `ID`,`ROLL_NO`, `NAME`, `PHYSICS`, `CHEMISTRY`, `MATHS` , (PHYSICS + CHEMISTRY + MATHS) as total from `ST_MARKSHEET` order by total desc");
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
				MarksheetBean bean = new MarksheetBean();
				bean.setId(rs.getLong(1));
				bean.setRollNo(rs.getString(2));
				bean.setName(rs.getString(3));
				bean.setPhysics(rs.getInt(4));
				bean.setChemistry(rs.getInt(5));
				bean.setMaths(rs.getInt(6));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error(e);
			throw new ApplicationException(
					"Exception in getting merit list of Marksheet");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model  MeritList End");
		return list;
	}

}
