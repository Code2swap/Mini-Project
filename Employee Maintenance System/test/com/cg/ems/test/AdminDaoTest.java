package com.cg.ems.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;

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
		employee = new Employee("300004", "First2", "Last2", null, null, 121 , "M4", "Analyst", 400000, 'F', "Single" , "Bellandur", "9453394101", "100004", 12);
		//change the value of date as per validations written
	}
	
	
	@Test
	public void addEmployee() throws EMSException {
		assertEquals(true, adminDao.addEmployee(employee));
	}
	
	@Test
	public void getAllEmployee() throws EMSException {
		assertNotNull(adminDao.getAllEmployee());
	}

	@Test
	public void getEmployeeById() throws EMSException {
		// make sure there is an employee with empId = 300001
		assertNull(adminDao.getEmployeeById("400001"));
		assertNotSame(employee, adminDao.getEmployeeById("300001"));
	}
	
	
	@AfterClass
	public static void destroy() {
		System.out.println("\nTest Ended");
		adminDao = null;
		employee = null;
	}
}
