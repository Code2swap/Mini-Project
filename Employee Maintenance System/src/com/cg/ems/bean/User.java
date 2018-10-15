package com.cg.ems.bean;

//Bean class for User
public class User {

	private String userId;
	// userName is unique
	private String userName;
	private String userPassword;
	// Admin or Employee
	private String userType;
	// to get the employee details for the user
	private String empId;
	
	//Default constructor for User
	public User() {			
	}
	
	//Parameterized constructor for User
	public User(String userId, String userName, String userPassword, String userType, String empId) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userPassword = userPassword;
		this.userType = userType;
		this.empId = empId;
	}
	//Getters and Setters for  User properties
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	//toString method for Department
	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", userPassword=" + userPassword + ", userType="
				+ userType + ", empId=" + empId + "]";
	}

}
