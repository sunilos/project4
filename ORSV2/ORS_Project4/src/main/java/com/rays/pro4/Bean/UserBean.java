package com.rays.pro4.Bean;

import java.sql.Timestamp;
import java.util.Date;

/**
 * User JavaBean encapsulates User attributes.
 * 
 * @author Sunil OS
 *
 */
public class UserBean extends BaseBean {

	public static final String ACTIVE ="Active";
	public static final String INACTIVE="inactive";
	
	private String firstName; 
	private String lastName;
	private String login;
	private String password;
	private String confirmPassword;
	private Date dob;
	private String mobileNo;  
	private long roleId;
	private int unSuccessfulLogin;
	private String gender;
	private Timestamp lastLogin;
	private String lock=INACTIVE;
	private String registerdIP;
	private String lastLoginIP;
	
	
	
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
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
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
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	public int getUnSuccessfulLogin() {
		return unSuccessfulLogin;
	}
	public void setUnSuccessfulLogin(int unSuccessfulLogin) {
		this.unSuccessfulLogin = unSuccessfulLogin;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Timestamp getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Timestamp lastLogin) {
		this.lastLogin = lastLogin;
	}
	public String getLock() {
		return lock;
	}
	public void setLock(String lock) {
		this.lock = lock;
	}
	public String getRegisterdIP() {
		return registerdIP;
	}
	public void setRegisterdIP(String registerdIP) {
		this.registerdIP = registerdIP;
	}
	public String getLastLoginIP() {
		return lastLoginIP;
	}
	public void setLastLoginIP(String lastLoginIP) {
		this.lastLoginIP = lastLoginIP;
	}
	public static String getActive() {
		return ACTIVE;
	}
	public static String getInactive() {
		return INACTIVE;
	}


	  public String getkey() { 
		  // TODO Auto-generated method stub
	  return id+"";
	  }
	  
      public String getValue() { 
		  // TODO Auto-generated method stub 
       return firstName + " " + lastName; 
	 }
	  
	
	 
//	 
//	
//	  public String getkey() { 
//		  // TODO Auto-generated method stub 
//		  return id+"";
//	  } public String getValue() { 
//		  // TODO Auto-generated method stub
//		  return dob +""; 
//		  }
	  
//	 
	@Override
	public String toString() {
	
		return "UserBean [password=" + password + ", dob=" +  dob+ "]";
	}
	

	
}
