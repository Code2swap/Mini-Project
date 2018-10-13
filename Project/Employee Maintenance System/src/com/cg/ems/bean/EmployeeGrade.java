package com.cg.ems.bean;

//Bean class for EmployeeGrade
public class EmployeeGrade {

	private String gradeCode;
	private String description;
	private int minSalary;
	private int maxSalary;
	
	//Default constructor for EmployeeGrade
	public EmployeeGrade() {
		super();
		
	}

	//Parameterized constructor for EmployeeGrade
	public EmployeeGrade(String gradeCode, String description, int minSalary,
			int maxSalary) {
		super();
		this.gradeCode = gradeCode;
		this.description = description;
		this.minSalary = minSalary;
		this.maxSalary = maxSalary;
	}


	
	//Getters and Setters for EmployeeGrade properties
	public String getGradeCode() {
		return gradeCode;
	}


	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getMinSalary() {
		return minSalary;
	}


	public void setMinSalary(int minSalary) {
		this.minSalary = minSalary;
	}


	public int getMaxSalary() {
		return maxSalary;
	}


	public void setMaxSalary(int maxSalary) {
		this.maxSalary = maxSalary;
	}
	
	//toString method for EmployeeGrade
	@Override
	public String toString() {
		return "EmployeeGrade [gradeCode=" + gradeCode + ", description="
				+ description + ", minSalary=" + minSalary + ", maxSalary="
				+ maxSalary + "]";
	}

}
