package com.cg.ems.test;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cg.ems.bean.Employee;
import com.cg.ems.bean.EmployeeLeave;
import com.cg.ems.bean.User;
import com.cg.ems.dao.AuthenticationDaoImpl;
import com.cg.ems.dao.AutoApprovalDaoImpl;
import com.cg.ems.dao.IAutoApprovalDao;
import com.cg.ems.exception.EMSException;

public class AutoApprovalDaoTest {

	static IAutoApprovalDao autoApprovalDao;
	static EmployeeLeave empLeave;

	@BeforeClass
	public static void initialize() throws EMSException {
		System.out.println("Testing Authentication DAO");
		autoApprovalDao = new AutoApprovalDaoImpl();
	}
	
	@Test
	public void autoApprove() throws EMSException { 
		assertNotNull( autoApprovalDao.autoApprove());
	}
	@AfterClass
	public static void destroy() {
		System.out.println("\nTest Ended");
		autoApprovalDao = null;
		empLeave = null;
	}

}
