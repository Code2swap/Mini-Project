package com.cg.ems.service;


import java.util.List;

import com.cg.ems.bean.EmployeeLeave;
import com.cg.ems.dao.ILeaveApplicationDao;
import com.cg.ems.dao.LeaveApplicationDaoImpl;
import com.cg.ems.exception.EMSException;

public class LeaveApplicationServiceImpl implements ILeaveApplicationService {

	ILeaveApplicationDao leaveApplicationDao;
	
	public LeaveApplicationServiceImpl() {
		leaveApplicationDao = new LeaveApplicationDaoImpl();
	}

	



	@Override
	public boolean applyLeave(EmployeeLeave empLeave) throws EMSException {
		
		return leaveApplicationDao.applyLeave(empLeave);
	}


	@Override
	public boolean approveLeave(EmployeeLeave empLeave) throws EMSException {
		
		return leaveApplicationDao.approveLeave(empLeave);
	}





	@Override
	public boolean rejectLeave(int leaveId) throws EMSException {
		
		return leaveApplicationDao.rejectLeave(leaveId);
	}





	@Override
	public List<EmployeeLeave> getAllAppliedLeaves() throws EMSException {
	
		return leaveApplicationDao.getAllAppliedLeaves();
	}

}
