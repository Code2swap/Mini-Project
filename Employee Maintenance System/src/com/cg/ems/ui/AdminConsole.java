package com.cg.ems.ui;

import java.sql.Date;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.cg.ems.bean.Employee;
import com.cg.ems.bean.User;
import com.cg.ems.exception.EMSException;
import com.cg.ems.service.AdminServiceImpl;
import com.cg.ems.service.IAdminService;
import com.cg.ems.util.Messages;

public class AdminConsole {

	private Scanner scan;
	private IAdminService adminService;
	private boolean success;
	
	public AdminConsole() {
		adminService = new AdminServiceImpl();
	}

	public void start() {
		int choice = -1;
		scan = new Scanner(System.in);
		while(true) {
			try {
				choice = showChoices();
				switch(choice) {
				case 1: 
					addEmployee();
					break;
				case 2: 
					modifyEmployee();
					break;
				case 3: 
					displayAllEmployees();
					break;
				case 4:
					backToMain();
					return;
				case 5: 
					exit();
				default:
					tryAgain();
				}
			} catch (EMSException e) {
				System.err.println(e.getMessage());
			}
		}
	}

	private void tryAgain() {
		System.out.println("Invalid Choice, Please Try Again");
	}

	private void exit() {
		System.out.println("Exiting The Program...");
		System.exit(0);
	}

	private void backToMain() {
		System.out.println("Returning to Main...");
	}

	private void displayAllEmployees() throws EMSException {
		List<Employee> employeeList = new ArrayList<>();
		employeeList = adminService.getAllEmployee();
		if(employeeList.isEmpty())
			System.out.println("Currently No Employees are there");
		for(Employee employee: employeeList)
			System.out.println(employee.toString());
	}

	private void modifyEmployee() throws EMSException {
		
		System.out.println("Modify Existing Employee");
		Employee updatedEmployee = new Employee();
		
		String strData;
		int intData;
		char charData;
		Date dateData;
		
		
		try {
			System.out.print("Enter Employee Id to Modify His/Her Details ? ");
			updatedEmployee.setEmpId(scan.next());
			
			// check if entered employee id exists in table
			Employee employee = adminService.getEmployeeById(updatedEmployee.getEmpId());
			if(employee == null) {
				System.err.println("Sorry, No details found for the entered id");
				return;
			}
			System.out.println(employee.toString());
			
			System.out.println("Enter Updated Value for Respective Field (0 for No Update)");
			// validations
			
			System.out.print("First Name ? ");
			strData = scan.next();
			if(strData.equals("0")) strData = employee.getEmpFName();
			updatedEmployee.setEmpFName(strData);
			
			System.out.print("Last Name ? ");
			strData = scan.next();
			if(strData.equals("0")) strData = employee.getEmpLName();
			updatedEmployee.setEmpLName(strData);
			
			System.out.print("Date of Birth (format:  yyyy-MM-dd) ? ");
			strData = scan.next();
			if(strData.equals("0")) dateData = employee.getEmpDOB();
			else dateData = Date.valueOf(strData);
			updatedEmployee.setEmpDOB(dateData);
			
			System.out.print("Date of Joining ? (format:  yyyy-MM-dd) ");
			strData = scan.next();
			if(strData.equals("0")) dateData = employee.getEmpDOJ();
			else dateData = Date.valueOf(strData);
			updatedEmployee.setEmpDOJ(dateData);
			
			System.out.print("Department Id ? ");
			intData = scan.nextInt();
			if(intData == 0) intData = employee.getEmpDeptId();
			updatedEmployee.setEmpDeptId(intData);
			
			System.out.print("Grade ? ");
			strData = scan.next();
			if(strData.equals("0")) strData = employee.getEmpGrade();
			updatedEmployee.setEmpGrade(strData);
			
			System.out.print("Designation ? ");
			strData = scan.next();
			if(strData.equals("0")) strData = employee.getEmpDesignation();
			updatedEmployee.setEmpDesignation(strData);
			
			System.out.print("Basic Salary ? ");
			intData = scan.nextInt();
			if(intData == 0) intData = employee.getEmpBasic();
			updatedEmployee.setEmpBasic(intData);
			
			System.out.print("Gender ? ");
			charData = scan.next().charAt(0);
			if(charData == '0') charData = employee.getEmpGender();
			updatedEmployee.setEmpGender(charData);
			
			System.out.print("Marital Status ? ");
			strData = scan.next();
			if(strData.equals("0")) strData = employee.getEmpMarital();
			updatedEmployee.setEmpMarital(strData);
			
			scan.nextLine();
			System.out.print("Home Address ? ");
			strData = scan.nextLine();
			if(strData.equals("0")) strData = employee.getEmpAddress();
			updatedEmployee.setEmpAddress(strData);
			
			System.out.print("Contact Number ? ");
			strData = scan.next();
			if(strData.equals("0")) strData = employee.getEmpContact();
			updatedEmployee.setEmpContact(strData);
			
			System.out.print("manager Id ? ");
			strData = scan.next();
			if(strData.equals("0")) strData = employee.getMgrId();
			updatedEmployee.setMgrId(strData);
			
			updatedEmployee.setEmpLeaveBal(employee.getEmpLeaveBal());
			
			success = adminService.updateEmployee(updatedEmployee);
			if(success) System.out.println("Employee Update Successfully");
			else System.err.println("Unable to upadte, please try again");
			
		} catch(IllegalArgumentException e) {
			throw new EMSException(Messages.DATE_FORMAT);
		} catch (InputMismatchException e) {
			scan.next();
			throw new EMSException(Messages.INPUT_MISMATCH);
		}
		
	}

	private void addUserCredentials(Employee employee) throws EMSException {
		System.out.println("Enter User Credentials");
		User user = new User();
		user.setEmpId(employee.getEmpId());
		System.out.print("Username ? ");
		user.setUserName(scan.next());
		System.out.print("User Password ? ");
		user.setUserPassword(scan.next());
		System.out.println("User Type (EMPLOYEE / ADMIN) ? ");
		user.setUserType(scan.next());
		adminService.addUserCredentials(user);
	}
	
	private void addUserCredentialsAuto(Employee employee) throws EMSException {
		
		User user = new User();
		String userName = employee.getEmpFName().toUpperCase();
		if(userName.length() > 3) {
			userName = userName.substring(0, 4);
		}
		String  userPassword = userName.toLowerCase() + "123";
		user.setEmpId(employee.getEmpId());
		user.setUserName(userName);
		user.setUserPassword(userPassword);
		user.setUserType("EMPLOYEE");
		if(adminService.addUserCredentials(user)) {
			System.out.println("User Credentials added successfully");
		}
		else {
			System.err.println("Unable to add user credentials, please try again");
		}
	}
	private void addEmployee() throws EMSException {
		
		System.out.println("Add Employee");
		scan = new Scanner(System.in);
		Employee employee = new Employee();
		
		try {
			System.out.print("Employee Id ? ");
			employee.setEmpId(scan.next());
			// check if entered employee id already exists in table
			if(adminService.getEmployeeById(employee.getEmpId()) != null) {
				System.err.println("Employee Id already exists, please try again");
				return;
			}
			
			System.out.print("First Name ? ");
			employee.setEmpFName(scan.next());
			
			System.out.print("Last Name ? ");
			employee.setEmpLName(scan.next());
			
			System.out.print("Date of Birth (format:  yyyy-dd-MM) ? ");
			employee.setEmpDOB(Date.valueOf(scan.next()));
			
			System.out.print("Date of Joining ? ");
			employee.setEmpDOJ(Date.valueOf(scan.next()));
			
			System.out.print("Department Id ? ");
			employee.setEmpDeptId(scan.nextInt());
			
			System.out.print("Grade ? ");
			employee.setEmpGrade(scan.next());
			
			System.out.print("Designation ? ");
			employee.setEmpDesignation(scan.next());
			
			System.out.print("Basic Salary ? ");
			employee.setEmpBasic(scan.nextInt());
			
			System.out.print("Gender ? ");
			employee.setEmpGender(scan.next().charAt(0));
			
			System.out.print("Marital Status ? ");
			employee.setEmpMarital(scan.next());
			
			System.out.print("Home Address ? ");
			scan.nextLine();
			employee.setEmpAddress(scan.nextLine());
			
			System.out.print("Contact Number ? ");
			employee.setEmpContact(scan.next());
			
			System.out.print("manager Id ? ");
			employee.setMgrId(scan.next());
			
			employee.setEmpLeaveBal(14);
			
			List<String> validationErrors = adminService.validateDetails(employee);
			if(!validationErrors.isEmpty()) {
				validationErrors.forEach(System.err::println);
				Thread.sleep(1000);
				System.out.println("Please Try Again");
				return;
			}
			success = adminService.addEmployee(employee);
			if(success) {
				System.out.println("Employee added successfully");
				addUserCredentialsAuto(employee);
			}
			else {
				System.err.println("Unable to add employee, please try again");
			}
			
		} catch(IllegalArgumentException e) {
			throw new EMSException(Messages.DATE_FORMAT);
		} catch (InputMismatchException e) {
			scan.next();
			throw new EMSException(Messages.INPUT_MISMATCH);
		} catch (InterruptedException e) {
			System.out.println("Action interrupted");
		}

		
	}

	private int showChoices() throws EMSException {
		int choice  = -1;
		System.out.println("[1] Add Employee");
		System.out.println("[2] Modify Existing Employee");
		System.out.println("[3] Display All Existing Employee");
		System.out.println("[4] Go Back to Main");
		System.out.println("[5] Exit");
		System.out.print("Your Choice ? ");
		
		try {
			choice = scan.nextInt();
		} catch (Exception e) {
			scan.next();
			throw new EMSException(Messages.INPUT_MISMATCH);
		}
		return choice;
	}
}
