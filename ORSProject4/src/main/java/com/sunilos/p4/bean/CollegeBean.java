package com.sunilos.p4.bean;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * College JavaBean encapsulates College attributes
 * 
 * @author Rays Technologies
 * @version 1.0
 * @Copyright (c) Rays Technologies
 * 
 */

public class CollegeBean extends BaseBean {

	/**
	 * Name of College
	 */
	private String name;
	/**
	 * Address of College
	 */
	private String address;
	/**
	 * State of College
	 */
	private String state;
	/**
	 * City of College
	 */
	private String city;
	/**
	 * Phoneno of College
	 */
	private String phoneNo;

	/**
	 * accessor
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	@Override
	public String getKey() {
		return id + "";
	}

	@Override
	public String getValue() {
		return name;
	}

	@Override
	public void setResultset(ResultSet rs) {
		try {
			super.setResultset(rs);
			this.setName(rs.getString(2));
			this.setAddress(rs.getString(3));
			this.setState(rs.getString(4));
			this.setCity(rs.getString(5));
			this.setPhoneNo(rs.getString(6));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
