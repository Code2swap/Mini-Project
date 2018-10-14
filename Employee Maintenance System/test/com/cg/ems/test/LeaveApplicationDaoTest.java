package com.cg.ems.test;

import static org.junit.Assert.*;

import java.sql.Date;
import java.time.LocalDate;

import org.junit.BeforeClass;
import org.junit.Test;

import com.cg.ems.bean.EmployeeLeave;
import com.cg.ems.dao.LeaveApplicationDaoImpl;
import com.cg.ems.exception.EMSException;

public class LeaveApplicationDaoTest {
	static EmployeeLeave empLeave;
	static LeaveApplicationDaoImpl leaveApplicationDao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		leaveApplicationDao = new LeaveApplicationDaoImpl();
		// make sure there exist an employee with empId = 200001 in employee table
		empLeave = new EmployeeLeave(3002, "200001", Date.valueOf(LocalDate.now()), 3, Date.valueOf("2018-11-10"),Date.valueOf( "2018-11-12"), "Applied");
	}

	@Test
	public void applyLeave() throws EMSException {
		assertEquals(true, leaveApplicationDao.applyLeave(empLeave) );
	}
	@Test
	public void getLeaveById() throws EMSException {
		assertEquals(false, leaveApplicationDao.approveLeave(2001) );
	}
	@Test
	public void getEmployeeLeaveById() throws EMSException
	{
		assertEquals(null, leaveApplicationDao.getEmpLeaveById(empLeave.getLeaveId()));
	}
	@Test
	public void getAllAppliedLeaves() throws EMSException
	{
		assertNotNull(leaveApplicationDao.getAllAppliedLeaves(empLeave.getEmpId()));
	}

}
