package com.cg.ems.test;

import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
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
	static List<String> empDeptNames;
	static List<String> empGrades;
	static List<String> empMarital;
	@BeforeClass
	public static void initialize() throws EMSException {
		System.out.println("Testing User DAO");
		userDao = new UserDaoImpl();
		
		employee = new Employee("300009", "First2", "Last2", null, null, 121 ,"M4",
				"Analyst", 400000, 'F', "Single" , "Bellandur", "9453394101", "100004", 12);	
		//first insert department with deptId = 121 and deptName = JEE
		// make sure there is not an employee with empId = 300009
		empDeptNames = Arrays.asList("JEE");
		empGrades = Arrays.asList("M4");
		empMarital = Arrays.asList("Single");
		adminDao = new AdminDaoImpl();
		adminDao.addEmployee(employee);
	}
	
	
	@Test
	public void getEmployeeById() throws EMSException {
		assertNotNull("Test case failed", userDao.getEmployeeById(employee.getEmpId()));
	}
	
	@Test
	public void searchById() throws EMSException {
		assertNotNull(userDao.searchById("30000", '?'));
	}

	@Test
	public void searchByFirstName() throws EMSException {
		assertNotNull(userDao.searchByFirstName("Firs", '*'));
	}

	@Test
	public void searchByLastName() throws EMSException {
		assertNotNull(userDao.searchByLastName("La", '*'));
	}

	//remove the parameters of following functions with valid data
	@Test
	public void searchByDept() throws EMSException {
		assertNotNull(userDao.searchByDept(empDeptNames));
	}

	@Test
	public void searchByGrade() throws EMSException {
		assertNotNull(userDao.searchByGrade(empGrades));
	}

	@Test
	public void searchByMarital() throws EMSException {
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
