package com.sunilos.pro4.bean;

/**
 * College JavaBean encapsulates College attributes.
 * @author Sunil OS
 *
 */
public class CollegeBean extends BaseBean{

	private String name;
	private String address;
	private String state;
	private String city;
	private String phoneNo;
	
	
	
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
	public String getkey() {
		// TODO Auto-generated method stub
		return id+" ";
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return name;
	}
	
}
