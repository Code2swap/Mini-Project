package com.cg.ems.service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
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
			validationErrors.add("Employee first name should be Alphabetic only, not empty and start with captital letter");
		}

		// validate last name
		if (!isValidLName(employee.getEmpLName())) {
			validationErrors.add("Employee last name should be Alphabetic only, not empty and start with captital letter");
		}

		// validate dates
		if (!isValidDate(employee.getEmpDOB(), employee.getEmpDOJ())) {
			validationErrors.add("Date of joining should be greater than date of birth");
		}

		// validate age
		if (!isValidAge(employee.getEmpDOB())) {
			validationErrors.add("Age must be between 18 to 58");
		}

		// validate Gender
		if (!isValidGender(employee.getEmpGender())) {
			validationErrors.add("Must be Male(M) or Female(F)");
		}
		//validate grade 
		if(!isValidGrade(employee.getEmpGrade())) {
			validationErrors.add("Grade must be between M1 to M7");
		}
		// validate marital status
		if (!isValidMaritalStatus(employee.getEmpMarital())) {
			validationErrors.add("Marital status must be from Single, Married, Divorced, Separated or Widowed");
		}
		
		//validate designation
		if(!isValidDesignation(employee.getEmpDesignation())) {
			validationErrors.add("Designation must be less than 50 words");
		}
		
		//validate address
		if(!isValidAddress(employee.getEmpAddress())) {
			validationErrors.add("Address should have AlphaNumeric characters only");
		}
		
		// validate Phone Number
		if (!isValidContact(employee.getEmpContact())) {
			validationErrors.add("Must be of 10 digits and start with 7,8 or 9");
		}
		return validationErrors;
	}
	
	//validation methods
	public boolean isValidAddress(String empAddress) {

		Pattern namePattern = Pattern.compile("^[A-Za-z0-9 ]{0,}$");
		Matcher nameMatcher = namePattern.matcher(empAddress);
		return nameMatcher.matches();

	}

	public boolean isValidDesignation(String empDesignation) {
		return empDesignation.length() <= 50;

	}

	public boolean isValidGrade(String empGrade) {
		if (empGrade.equals("M1")) {
			return true;
		} else if (empGrade.equals("M2")) {
			return true;
		} else if (empGrade.equals("M3")) {
			return true;
		} else if (empGrade.equals("M4")) {
			return true;
		} else if (empGrade.equals("M5")) {
			return true;
		} else if (empGrade.equals("M6")) {
			return true;
		} else if (empGrade.equals("M7")) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isValidContact(String empContact) {

		Pattern namePattern = Pattern.compile("^[7/8/9]{1}[0-9]{9}$");
		Matcher nameMatcher = namePattern.matcher(empContact);
		return nameMatcher.matches();

	}

	private boolean isValidGender(char empGender) {
		if (empGender == 'M' || empGender == 'F') {
			return true;
		} else {
			return false;
		}
	}

	private boolean isValidMaritalStatus(String empMarital) {

		if (empMarital.equals("Single")) {
			return true;
		} else if (empMarital.equals("Married")) {
			return true;
		} else if (empMarital.equals("Divorced")) {
			return true;
		} else if (empMarital.equals("Separated")) {
			return true;
		} else if (empMarital.equals("Widowed")) {
			return true;
		} else {

			return false;
		}
	}

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
