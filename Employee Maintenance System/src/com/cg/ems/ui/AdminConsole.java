package com.cg.ems.ui;

import java.sql.Date;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

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
	private static Logger log = Logger.getLogger("Admin Console");
	
	public AdminConsole() {
		PropertyConfigurator.configure("resources/log4j.properties");
		adminService = new AdminServiceImpl();
	}
	
	// start of admin console, everything is here
	public void start() {
		log.info("Admin console started");
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
				log.error(e);
				System.err.println(e.getMessage());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					System.err.println("Action interrupted");
				}
			}
		}
	}
	
	private void tryAgain() {
		log.warn("Invalid choice entered by the user");
		System.out.println("Invalid Choice, Please Try Again");
	}
	
	// to exit the application
	private void exit() {
		log.info("Program terminated");
		System.out.println("Exiting The Program...");
		System.exit(0);
	}

	// return to main
	private void backToMain() {
		log.info("Back to main console");
		System.out.println("Returning to Main...");
	}

	// get all employees from db and display
	private void displayAllEmployees() throws EMSException {
		log.info("Displaying all employees");
		List<Employee> employeeList = new ArrayList<>();
		employeeList = adminService.getAllEmployee();
		if(employeeList.isEmpty()) {
			log.warn("the employee table is empty");
			System.out.println("Currently No Employees are there");
		}
		
		/*System.out.println(String.format("%-10s %-10s %s", "ID","FNAME","LNAME"));
		for(Employee employee : employeeList){
			System.out.println(String.format("%-10s %-10s %s", employee.getEmpId(),employee.getEmpFName(),employee.getEmpLName()));
		}*/
		
		
		for(Employee employee: employeeList)
			System.out.println(employee.toString());
	}

	// modify employee to db
	private void modifyEmployee() throws EMSException {
		log.info("modifying employee");
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
				log.error("no data found");
				System.err.println("Sorry, No details found for the entered id");
				return;
			}
			System.out.println(employee.toString());
			
			System.out.println("Enter Updated Value for Respective Field (-1 for No Update)");
			// validations
			
			System.out.print("First Name ? ");
			strData = scan.next();
			if(strData.equals("-1")) strData = employee.getEmpFName();
			updatedEmployee.setEmpFName(strData);
			
			System.out.print("Last Name ? ");
			strData = scan.next();
			if(strData.equals("-1")) strData = employee.getEmpLName();
			updatedEmployee.setEmpLName(strData);
			
			System.out.print("Date of Birth (format:  yyyy-MM-dd) ? ");
			strData = scan.next();
			if(strData.equals("-1")) dateData = employee.getEmpDOB();
			else dateData = Date.valueOf(strData);
			updatedEmployee.setEmpDOB(dateData);
			
			System.out.print("Date of Joining ? (format:  yyyy-MM-dd) ");
			strData = scan.next();
			if(strData.equals("-1")) dateData = employee.getEmpDOJ();
			else dateData = Date.valueOf(strData);
			updatedEmployee.setEmpDOJ(dateData);
			
			System.out.print("Department Id ? ");
			intData = scan.nextInt();
			if(intData == -1) intData = employee.getEmpDeptId();
			updatedEmployee.setEmpDeptId(intData);
			
			System.out.print("Grade ? ");
			strData = scan.next();
			if(strData.equals("-1")) strData = employee.getEmpGrade();
			updatedEmployee.setEmpGrade(strData);
			
			System.out.print("Designation ? ");
			strData = scan.next();
			if(strData.equals("-1")) strData = employee.getEmpDesignation();
			updatedEmployee.setEmpDesignation(strData);
			
			System.out.print("Basic Salary ? ");
			intData = scan.nextInt();
			if(intData == -1) intData = employee.getEmpBasic();
			updatedEmployee.setEmpBasic(intData);
			
			System.out.print("Gender ? ");
			strData = scan.next();
			if(strData.equals("-1")) charData = employee.getEmpGender();
			else charData = strData.charAt(0);
			updatedEmployee.setEmpGender(charData);
			
			System.out.print("Marital Status ? ");
			strData = scan.next();
			if(strData.equals("-1")) strData = employee.getEmpMarital();
			updatedEmployee.setEmpMarital(strData);
			
			scan.nextLine();
			System.out.print("Home Address ? ");
			strData = scan.nextLine();
			if(strData.equals("-1")) strData = employee.getEmpAddress();
			updatedEmployee.setEmpAddress(strData);
			
			System.out.print("Contact Number ? ");
			strData = scan.next();
			if(strData.equals("-1")) strData = employee.getEmpContact();
			updatedEmployee.setEmpContact(strData);
			
			System.out.print("Manager Id ? ");
			strData = scan.next();
			if(strData.length() > 1 && !strData.equals("-1")) {
				System.out.println("Should be a single character or -1");
				log.error("string entered instead of character");
				return;
			}
			if(strData.equals("-1")) strData = employee.getMgrId();
			updatedEmployee.setMgrId(strData);
			
			System.out.println("Employee Leave Balance");
			intData = scan.nextInt();
			if(intData == -1) intData = employee.getEmpLeaveBal();
			updatedEmployee.setEmpLeaveBal(intData);
			
			// getting list of errors during validation
			List<String> validationErrors = adminService.validateDetails(updatedEmployee);
			if(!validationErrors.isEmpty()) {
				log.error("Validation errors");
				validationErrors.forEach(System.err::println);
				Thread.sleep(1000);
				System.out.println("Please Try Again");
				return;
			} else {
				log.info("Validation succesful");
			}
			
			success = adminService.updateEmployee(updatedEmployee);
			if(success) {
				log.info("Employee data updated");
				System.out.println("Employee Update Successfully");
			}
			else {
				log.error("Employee not updated");
				System.err.println("Unable to upadte, please try again");
			}
			
		} catch(IllegalArgumentException e) {
			log.error("Incorrect format for date");
			throw new EMSException(Messages.DATE_FORMAT);
		} catch (InputMismatchException e) {
			log.error("Input type mismatch");
			// do not remove this line
			scan.next();
			throw new EMSException(Messages.INPUT_MISMATCH);
		} catch (InterruptedException e) {
			log.error("Sleeping interrupted");
			throw new EMSException("Action interrupted");
		}
		
	}

	// after adding employee to db, admin has to add credential for the same
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
	
	// after adding employee to db, credential for the same is auto added
	private void addUserCredentialsAuto(Employee employee) throws EMSException {
		log.info("Adding user credentials");
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
			log.info("Credentials successfully added");
			System.out.println("User Credentials added successfully");
		}
		else {
			log.error("Credentials not added");
			System.err.println("Unable to add user credentials, please try again");
		}
	}
	
	// ad employee data to db
	private void addEmployee() throws EMSException {
		log.info("adding employee to db");
		System.out.println("Add Employee");
		scan = new Scanner(System.in);
		Employee employee = new Employee();
		
		try {
			System.out.print("Employee Id ? ");
			employee.setEmpId(scan.next());
			// check if entered employee id already exists in table
			if(adminService.getEmployeeById(employee.getEmpId()) != null) {
				log.error("Employee id already exist in table");
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
			scan.nextLine();
			employee.setEmpDesignation(scan.nextLine());
			
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
				log.error("Validation errors");
				validationErrors.forEach(System.err::println);
				Thread.sleep(1000);
				System.out.println("Please Try Again");
				return;
			}
			success = adminService.addEmployee(employee);
			if(success) {
				log.info("Validation successful");
				System.out.println("Employee added successfully");
				addUserCredentialsAuto(employee);
			}
			else {
				log.error("employee not added");
				System.err.println("Unable to add employee, please try again");
			}
			
		} catch(IllegalArgumentException e) {
			log.error("Incorrect format for date");
			throw new EMSException(Messages.DATE_FORMAT);
		} catch (InputMismatchException e) {
			log.error("Input type mismatch");
			// do not remove this line
			scan.next();
			throw new EMSException(Messages.INPUT_MISMATCH);
		} catch (InterruptedException e) {
			log.error("Sleeping interrupted");
			throw new EMSException("Action interrupted");
		}
	}

	// show choices to user
	private int showChoices() throws EMSException {
		log.info("Choices for user");
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
			log.error("Input type mismatch");
			// do not remove this line
			scan.next();
			throw new EMSException(Messages.INPUT_MISMATCH);
		}
		return choice;
	}
}
