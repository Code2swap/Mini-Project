package com.cg.ems.bean;

import java.sql.Date;

//Bean class for Employee

public class Employee {

	private String empId;
	private String empFName;
	private String empLName;
	private Date empDOB;
	private Date empDOJ;
	private int empDeptId;
	private String empGrade;
	private String empDesignation;
	private int empBasic;
	private char empGender;
	private String empMarital;
	private String empAddress;
	private String empContact;
	private String mgrId;
	private int empLeaveBal;
	//Default constructor for Employee
	public Employee() {	
	}
	
	
	//Parameterized constructor for Employee
	public Employee(String empId, String empFName, String empLName,
			Date empDOB, Date empDOJ, int empDeptId, String empGrade,
			String empDesignation, int empBasic, char empGender,
			String empMarital, String empAddress, String empContact,
			String mgrId, int empLeaveBal) {
		super();
		this.empId = empId;
		this.empFName = empFName;
		this.empLName = empLName;
		this.empDOB = empDOB;
		this.empDOJ = empDOJ;
		this.empDeptId = empDeptId;
		this.empGrade = empGrade;
		this.empDesignation = empDesignation;
		this.empBasic = empBasic;
		this.empGender = empGender;
		this.empMarital = empMarital;
		this.empAddress = empAddress;
		this.empContact = empContact;
		this.mgrId = mgrId;
		this.empLeaveBal = empLeaveBal;
	}

	
	
	//Getters and Setters for Employee properties
	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getEmpFName() {
		return empFName;
	}

	public void setEmpFName(String empFName) {
		this.empFName = empFName;
	}

	public String getEmpLName() {
		return empLName;
	}

	public void setEmpLName(String empLName) {
		this.empLName = empLName;
	}

	public Date getEmpDOB() {
		return empDOB;
	}

	public void setEmpDOB(Date empDOB) {
		this.empDOB = empDOB;
	}

	public Date getEmpDOJ() {
		return empDOJ;
	}

	public void setEmpDOJ(Date empDOJ) {
		this.empDOJ = empDOJ;
	}

	public int getEmpDeptId() {
		return empDeptId;
	}

	public void setEmpDeptId(int empDeptId) {
		this.empDeptId = empDeptId;
	}

	public String getEmpGrade() {
		return empGrade;
	}

	public void setEmpGrade(String empGrade) {
		this.empGrade = empGrade;
	}

	public String getEmpDesignation() {
		return empDesignation;
	}

	public void setEmpDesignation(String empDesignation) {
		this.empDesignation = empDesignation;
	}

	public int getEmpBasic() {
		return empBasic;
	}

	public void setEmpBasic(int empBasic) {
		this.empBasic = empBasic;
	}

	public char getEmpGender() {
		return empGender;
	}

	public void setEmpGender(char empGender) {
		this.empGender = empGender;
	}

	public String getEmpMarital() {
		return empMarital;
	}

	public void setEmpMarital(String empMarital) {
		this.empMarital = empMarital;
	}

	public String getEmpAddress() {
		return empAddress;
	}

	public void setEmpAddress(String empAddress) {
		this.empAddress = empAddress;
	}

	public String getEmpContact() {
		return empContact;
	}

	public void setEmpContact(String empContact) {
		this.empContact = empContact;
	}

	public String getMgrId() {
		return mgrId;
	}

	public void setMgrId(String mgrId) {
		this.mgrId = mgrId;
	}

	public int getEmpLeaveBal() {
		return empLeaveBal;
	}

	public void setEmpLeaveBal(int empLeaveBal) {
		this.empLeaveBal = empLeaveBal;
	}

	
	//toString method for Employee
	@Override
	public String toString() {
		return "Employee [empId=" + empId + ", empFName=" + empFName
				+ ", empLName=" + empLName + ", empDOB=" + empDOB + ", empDOJ="
				+ empDOJ + ", empDeptId=" + empDeptId + ", empGrade="
				+ empGrade + ", empDesignation=" + empDesignation
				+ ", empBasic=" + empBasic + ", empGender=" + empGender
				+ ", empMarital=" + empMarital + ", empAddress=" + empAddress
				+ ", empContact=" + empContact + ", mgrId=" + mgrId
				+ ", empLeaveBal=" + empLeaveBal + "]";
	}


}
