package com.cg.ems.service;


import java.util.List;

import com.cg.ems.bean.EmployeeLeave;
import com.cg.ems.exception.EMSException;

public interface ILeaveApplicationService {

	boolean applyLeave(EmployeeLeave data) throws EMSException;
	boolean rejectLeave(int leaveId) throws EMSException;
	List<EmployeeLeave> getAllAppliedLeaves(String mgrId) throws EMSException;
	boolean approveLeave(int leaveId) throws EMSException;
	
}
