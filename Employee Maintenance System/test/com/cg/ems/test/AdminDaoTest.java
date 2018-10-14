package com.cg.ems.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cg.ems.bean.Employee;
import com.cg.ems.dao.AdminDaoImpl;
import com.cg.ems.exception.EMSException;

public class AdminDaoTest {
	
	static AdminDaoImpl adminDao;
	static Employee employee;
	
	@BeforeClass
	public static void initialize() {
		System.out.println("Testing Admin DAO");
		adminDao = new AdminDaoImpl();
		employee = new Employee("200001","Test","User",null,null,10,"M1","TestManager",500,
				'M', "Married" , "Whitefield","9874563256","100004", 12);	
		//change the value of date as per validations written
	}
	
	
	@Test
	public void addEmployee() throws EMSException {
		assertNotNull(adminDao.addEmployee(employee));
	}
	
	@Test
	public void getAllEmployee() throws EMSException {
		assertNotNull(adminDao.getAllEmployee());
	}

	@Test
	public void getEmployeeById() throws EMSException {
		assertEquals("Test case failed", employee, adminDao.getEmployeeById("200001"));
	}
	
	
	@AfterClass
	public static void destroy() {
		System.out.println("\nTest Ended");
		adminDao = null;
		employee = null;
	}
}
