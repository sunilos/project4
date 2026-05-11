package com.sunilos.p4.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Student JavaBean encapsulates Student attributes
 * 
 * @author Rays Technologies
 * @version 1.0
 * @Copyright (c) Rays Technologies
 * 
 */

public class StudentBean extends BaseBean {

	public StudentBean() {
	}

	/**
	 * First Name of Student
	 */
	private String firstName;
	/**
	 * Last Name of Student
	 */
	private String lastName;
	/**
	 * Date of Birth of Student
	 */
	private Date dob;
	/**
	 * Mobileno of Student
	 */
	private String mobileNo;
	/**
	 * Email of Student
	 */
	private String email;
	/**
	 * CollegeId of Student
	 */
	private long collegeId;
	/**
	 * College name of Student
	 */
	private String collegeName;

	/**
	 * accessor
	 */

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(Long collegeId) {
		this.collegeId = collegeId;
	}

	public String getCollegeName() {
		return collegeName;
	}

	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}

	@Override
	public String getKey() {
		return id + "";
	}

	@Override
	public String getValue() {
		return firstName + " " + lastName;
	}

	@Override
	public void setResultset(ResultSet rs) {
		try {
			super.setResultset(rs);
			this.setCollegeId(rs.getLong(2));
			this.setCollegeName(rs.getString(3));
			this.setFirstName(rs.getString(4));
			this.setLastName(rs.getString(5));
			this.setDob(rs.getDate(6));
			this.setMobileNo(rs.getString(7));
			this.setEmail(rs.getString(8));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
