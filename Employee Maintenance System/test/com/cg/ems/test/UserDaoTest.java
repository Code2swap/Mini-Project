package com.cg.ems.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cg.ems.bean.Employee;
import com.cg.ems.dao.AdminDaoImpl;
import com.cg.ems.dao.UserDaoImpl;
import com.cg.ems.exception.EMSException;

public class UserDaoTest {
	
	static UserDaoImpl userDao;
	static AdminDaoImpl adminDao;
	static Employee employee;
	
	@BeforeClass
	public static void initialize() throws EMSException {
		System.out.println("Testing User DAO");
		userDao = new UserDaoImpl();
		
		employee = new Employee("200001","Test","User",null,null,10,"M1","TestManager",500,
				'M', "Married" , "Whitefield","9874563256","100004", 12);	
		//change the value of date as per validations written
		
		adminDao = new AdminDaoImpl();
		adminDao.addEmployee(employee);
	}
	
	
	@Test
	public void getEmployeeById() throws EMSException {
		assertEquals("Test case failed", employee, userDao.getEmployeeById("200001"));
	}
	
	@Test
	public void searchById() throws EMSException {
		assertNotNull(userDao.searchById("200001", 'A'));
	}

	@Test
	public void searchByFirstName() throws EMSException {
		assertNotNull(userDao.searchByFirstName("Test", 'A'));
	}

	@Test
	public void searchByLastName() throws EMSException {
		assertNotNull(userDao.searchByLastName("User", 'A'));
	}

	//remove the parameters of following functions with valid data
	@Test
	public void searchByDept(List<String> empDeptNames) throws EMSException {
		assertNotNull(userDao.searchByDept(empDeptNames));
	}

	@Test
	public void searchByGrade(List<String> empGrades) throws EMSException {
		assertNotNull(userDao.searchByGrade(empGrades));
	}

	@Test
	public void searchByMarital(List<String> empMarital) throws EMSException {
		assertNotNull(userDao.searchByMarital(empMarital));
	}

	
	@AfterClass
	public static void destroy() {
		System.out.println("\nTest Ended");
		userDao = null;
		adminDao = null;
		employee = null;
	}
}
