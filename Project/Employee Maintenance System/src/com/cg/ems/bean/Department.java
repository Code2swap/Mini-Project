package com.cg.ems.bean;
//Bean class for Department

public class Department {

	private String deptName;
	private int deptId;
	
	//Default constructor for Department
	public Department() {
		super();
		
	}
	//Parameterized constructor for Department
	public Department(String deptName, int deptId) {
		super();
		this.deptName = deptName;
		this.deptId = deptId;
	}
	//Getters and Setters for  Department properties
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public int getDeptId() {
		return deptId;
	}
	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}
	
	//toString method for Department
	@Override
	public String toString() {
		return "Department [deptName=" + deptName + ", deptId=" + deptId + "]";
	}
	
}
