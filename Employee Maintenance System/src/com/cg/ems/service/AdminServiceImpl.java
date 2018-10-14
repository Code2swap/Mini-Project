package com.cg.ems.service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cg.ems.bean.Employee;
import com.cg.ems.bean.User;
import com.cg.ems.dao.AdminDaoImpl;
import com.cg.ems.dao.IAdminDao;
import com.cg.ems.exception.EMSException;

public class AdminServiceImpl implements IAdminService {

	IAdminDao adminDao;

	public AdminServiceImpl() {
		adminDao = new AdminDaoImpl();
	}

	@Override
	public boolean addEmployee(Employee employee) throws EMSException {
		return adminDao.addEmployee(employee);
	}

	@Override
	public boolean updateEmployee(Employee employee) throws EMSException {
		return adminDao.updateEmployee(employee);
	}

	@Override
	public List<Employee> getAllEmployee() throws EMSException {
		return adminDao.getAllEmployee();
	}

	@Override
	public Employee getEmployeeById(String empId) throws EMSException {
		return adminDao.getEmployeeById(empId);
	}

	@Override
	public boolean addUserCredentials(User user) throws EMSException {
		return adminDao.addUserCredentials(user);
	}

	public List<String> validateDetails(Employee employee) throws EMSException {

		List<String> validationErrors = new ArrayList<String>();

		// Validate Employee ID
		if (!isValidID(employee.getEmpId())) {
			validationErrors.add("Employee ID must be a 6 digit number and not be empty");
		}

		// Validate first name

		if (!isValidFName(employee.getEmpFName())) {
			validationErrors.add("Employee first name should not be empty and start with captital letter");
		}

		// validate last name
		if (!isValidLName(employee.getEmpLName())) {
			validationErrors.add("Employee last name should not be empty and start with captital letter");
		}

		// validate dates
		if (!isValidDate(employee.getEmpDOB(), employee.getEmpDOJ())) {
			validationErrors.add("Date of joining should be greater than date of birth");
		}

		// validate age
		if (!isValidAge(employee.getEmpDOB())) {
			validationErrors.add("Age must be between 18 to 58");
		}
		return validationErrors;
	}

	// validation methods
	private boolean isValidAge(Date empDOB) {
		Period diff = Period.between(empDOB.toLocalDate(), LocalDate.now());
		int age = diff.getYears();
		return (age >= 18 && age <= 58);
	}

	private boolean isValidDate(Date empDOB, Date empDOJ) {

		int dateCheck = empDOB.compareTo(empDOJ);
		if (dateCheck < 0) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isValidLName(String empLName) {

		if (empLName.isEmpty()) {
			return false;
		} else {
			Pattern namePattern = Pattern.compile("^[A-Z]{1}[a-z]{0,}$");
			Matcher nameMatcher = namePattern.matcher(empLName);
			return nameMatcher.matches();

		}

	}

	private boolean isValidFName(String empFName) {

		if (empFName.isEmpty()) {
			return false;
		} else {
			Pattern namePattern = Pattern.compile("^[A-Z]{1}[a-z]{0,}$");
			Matcher nameMatcher = namePattern.matcher(empFName);
			return nameMatcher.matches();

		}
	}

	private boolean isValidID(String empId) {
		if (empId.isEmpty()) {

			return false;

		} else {

			Pattern namePattern = Pattern.compile("^[1-9]{1}[0-9]{5}$");
			Matcher nameMatcher = namePattern.matcher(empId);
			return nameMatcher.matches();

		}
	}
}
