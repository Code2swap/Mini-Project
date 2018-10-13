package com.cg.ems.bean;

import java.sql.Date;

//Bean class for EmployeeLeave

public class EmployeeLeave {
	
	
	private int leaveId;
	private String empId;
	private Date appliedDate;
	private int leaveDuration;
	private Date fromDate;
	private Date toDate;
	private String status;
	
	
	
	public EmployeeLeave(String empId,
			int leaveDuration, Date fromDate, Date toDate, String status) {
		this.empId = empId;
		this.leaveDuration = leaveDuration;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.status = status;
	}



	@Override
	public String toString() {
		return "EmployeeLeave [empId=" + empId + ", leaveId=" + leaveId
				+ ", appliedDate=" + appliedDate + ", leaveDuration="
				+ leaveDuration + ", fromDate=" + fromDate + ", toDate="
				+ toDate + ", status=" + status + "]";
	}



	public EmployeeLeave() {
	}



	public String getEmpId() {
		return empId;
	}



	public void setEmpId(String empId) {
		this.empId = empId;
	}



	public int getLeaveId() {
		return leaveId;
	}



	public void setLeaveId(int leaveId) {
		this.leaveId = leaveId;
	}



	public Date getAppliedDate() {
		return appliedDate;
	}



	public void setAppliedDate(Date appliedDate) {
		this.appliedDate = appliedDate;
	}



	public int getLeaveDuration() {
		return leaveDuration;
	}



	public void setLeaveDuration(int leaveDuration) {
		this.leaveDuration = leaveDuration;
	}



	public Date getFromDate() {
		return fromDate;
	}



	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}



	public Date getToDate() {
		return toDate;
	}



	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
	
	
}
