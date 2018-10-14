package com.cg.ems.ui;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.cg.ems.bean.Employee;
import com.cg.ems.bean.EmployeeLeave;
import com.cg.ems.exception.EMSException;
import com.cg.ems.service.ILeaveApplicationService;
import com.cg.ems.service.IUserService;
import com.cg.ems.service.LeaveApplicationServiceImpl;
import com.cg.ems.service.UserServiceImpl;
import com.cg.ems.util.Messages;

public class EmployeeConsole {

	private Scanner scan;
	private IUserService userService;
	private ILeaveApplicationService leaveService;
	private String empId;
	private String string;
	private int number;
	private List<String> stringArray;
	private char wildcardChar;
	private List<Employee> empList;
	private static Logger log = Logger.getLogger("Admin Console");
	
	public EmployeeConsole() {
		userService = new UserServiceImpl();
		leaveService = new LeaveApplicationServiceImpl();
	}

	// to identify and validate employee
	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public void start() {
		log.info("Employee console started");
		scan = new Scanner(System.in);
		int choice = 0;

		while (true) {
			String designation = null;
			try {
				designation = userService.getEmployeeById(empId).getEmpDesignation();
				if (designation.equals("Manager")) {
					
					showChoicesForManager();
					choice = scan.nextInt();
					switch (choice) {
					case 1:
						searchEmployee();
						break;
					case 2:
						applyLeave();
						break;
					case 3:
						manageLeave();
						break;
					case 4:
						backToMain();
						return;
					case 5:
						exit();
					default:
						tryAgain();
					}
				} else {
					showChoices();
					choice = scan.nextInt();
					switch (choice) {
					case 1:
						searchEmployee();
						break;
					case 2:
						applyLeave();
						break;
					case 3:
						backToMain();
						return;
					case 4:
						exit();
					default:
						tryAgain();
					}
				}
			} catch (InputMismatchException e) {
				log.error("Input type mismatch");
				System.err.println(Messages.INPUT_MISMATCH);
				// do not remove it
				scan.next();
			} catch (EMSException e) {
				log.error(e);
				System.err.println(e.getMessage());
			}
		}
	}

	// choices manage leaves, by only manager
	private void showChoicesToManageLeaves() {
		log.info("Displaying choices to manage leaves");
		System.out.println("[1] Approve Leave");
		System.out.println("[2] Reject Leave");
		System.out.println("[3] Back to Employee Console");
		System.out.println("[4] Exit");
		System.out.print("Your Choice ? ");

	}

	// choices for employees
	private void showChoices() {
		log.info("Displaying choices for common employees");
		System.out.println("[1] Search Employee");
		System.out.println("[2] Apply for Leave");
		System.out.println("[3] Back to Main");
		System.out.println("[4] Exit");
		System.out.print("Your Choice ? ");

	}
	
	// choices for manages
	private void showChoicesForManager() {
		log.info("Displaying choices for manager");
		System.out.println("[1] Search Employee");
		System.out.println("[2] Apply for Leave");
		System.out.println("[3] Manage Leaves");
		System.out.println("[4] Back to Main");
		System.out.println("[5] Exit");
		System.out.print("Your Choice ? ");
	}

	// manage leave
	private void manageLeave() {
		log.info("Managing leaves");
		int choice = 0;
		int leaveId = -1;
		scan = new Scanner(System.in);
		try {
			List<EmployeeLeave> empLeaveList = leaveService.getAllAppliedLeaves(empId);
			boolean leaveIdExist = false;
			// if list is empty then show proper message
			if (empLeaveList.isEmpty()) {
				log.warn("No leaves are applied under this manager");
				System.out.println("No leaves are found for status: Applied" + " and having ManagerId: " + empId);
				return;
			}
			empLeaveList.forEach(System.out::println);
			System.out.println("Enter leaveId from the above Leave list to Approve/Reject ? ");

			leaveId = scan.nextInt();
			// check if this leaveId is valid or not in leave_history
			for (EmployeeLeave leave : empLeaveList) {
				if (leave.getLeaveId() == leaveId)
					leaveIdExist = true;
			}
			if (!leaveIdExist) {
				log.warn("Leave id is not correct");
				System.out.println("Sorry, entered leaveId not found! Please try again");
				return;
			}
			showChoicesToManageLeaves();
			choice = scan.nextInt();
			switch (choice) {
			case 1:
				if (leaveService.approveLeave(leaveId)) {
					log.info("Leave apllied");
					System.out.println("Leave successfully approved");
				}
				else {
					log.error("Can not appply for leave");
					System.err.println("Error occured, Not able to approve leave. Please try again...");
				}
				break;
			case 2:
				if (leaveService.rejectLeave(leaveId)) {
					log.info("Leave rejected");
					System.out.println("Leave successfully rejected");
				}
				else {
					log.error("Can not reject leave");
					System.err.println("Error occured, Not able to reject leave. Please try again...");
				}
				break;
			case 3:
				backToEmployeeConsole();
				return;
			case 4:
				exit();
			default:
				tryAgain();
			}
		} catch (InputMismatchException e) {
			log.error("Input type mismatch");
			System.err.println(Messages.INPUT_MISMATCH);
			// don not remove it
			scan.next();
		} catch (EMSException e) {
			log.error(e);
			System.err.println(e.getMessage());
		}
	}

	// apply leave
	private void applyLeave() {
		log.info("Applying for leaves");
		System.out.println("Apply for Leave");
		Employee employee = null;
		try {
			Date today = Date.valueOf(LocalDate.now());
			employee = userService.getEmployeeById(empId);

			System.out.println(employee.toString());
			int leaveBal = employee.getEmpLeaveBal();
			System.out.println("You have Leaves Left: " + leaveBal);
			EmployeeLeave empLeave = new EmployeeLeave();

			System.out.print("From Date (format:  yyyy-MM-dd) ? ");
			empLeave.setFromDate(Date.valueOf(scan.next()));
			if (empLeave.getFromDate().compareTo(today) <= 0) {
				log.error("Incorrect FROM DATE for leave");
				System.err.println("FROM DATE must be ahead of today");
				return;
			}

			System.out.print("To Date (format:  yyyy-MM-dd) ? ");
			empLeave.setToDate(Date.valueOf(scan.next()));
			if (empLeave.getToDate().compareTo(empLeave.getFromDate()) < 0) {
				log.error("Incorrect TO DATE for leave");
				System.err.println("TO DATE must be ahead of FROM DATE");
				return;
			}

			int diffDays = 1 + (int) TimeUnit.MILLISECONDS
					.toDays(empLeave.getToDate().getTime() - empLeave.getFromDate().getTime());
			empLeave.setLeaveDuration(diffDays);
			
			if (diffDays > leaveBal) {
				log.warn("Not enough leaves");
				System.out.println("Sorry, Not Sufficient Leaves You have");
				return;
			}

			// leaveId is generated by sql sequence
			// applied date is set by sql

			empLeave.setEmpId(empId);
			empLeave.setStatus("Applied");
			if (leaveService.applyLeave(empLeave)) {
				log.info("Leave Apllied");
				System.out.println("Leave Successfully Applied");
			} else {
				log.error("Can not apply for leave");
				System.out.println("Unable to Apply Leave, Please try again");
			}
		} catch (IllegalArgumentException e) {
			log.error("Incorrect format for date");
			System.err.println(Messages.DATE_FORMAT);
		} catch (EMSException e) {
			log.error(e);
			System.err.println(e.getMessage());
		}

	}

	// search employee
	private void searchEmployee() {
		log.info("Searhcing employee");
		int choice = 0;
		while (true) {
			try {
				System.out.println("Search Employee");
				showSearchChoices();
				choice = scan.nextInt();
				switch (choice) {
				case 1:
					searchById();
					break;
				case 2:
					searchByFirstName();
					break;
				case 3:
					searchByLastName();
					break;
				case 4:
					searchByDepartment();
					break;
				case 5:
					searchByGrade();
					break;
				case 6:
					searchByMaritalStatus();
					break;
				case 7:
					backToEmployeeConsole();
					return;
				case 8:
					exit();
				default:
					tryAgain();
				}
			} catch (InputMismatchException e) {
				log.error("Input type mismatch");
				System.err.println(Messages.INPUT_MISMATCH);
				// do not remove it
				scan.next();
			}
		}

	}
	// search by marital status of employee
	private void searchByMaritalStatus() {

		int count = 0;
		stringArray = new ArrayList<>();

		try {
			System.out.println("Enter Number of Marital Status to be Searched in");
			number = scan.nextInt();

			System.out.println("Enter <Employee Marital Status/es>");
			while (count++ < number)
				stringArray.add(scan.next());

			System.out.println("Fetching Searched Employees...");
			empList = userService.searchByMarital(stringArray);
			showResults();
		} catch (InputMismatchException e) {
			log.error("Input type mismatch");
			System.err.println(Messages.INPUT_MISMATCH);
			scan.next();
		} catch (EMSException e) {
			log.error(e);
			System.out.println(e.getMessage());
		}
	}
	// search by grade of employee
	private void searchByGrade() {

		int count = 0;
		stringArray = new ArrayList<>();

		try {
			System.out.println("Enter Number of Grades to be Searched in");
			number = scan.nextInt();

			System.out.println("Enter <Employee Grade/s>");
			while (count++ < number)
				stringArray.add(scan.next());

			System.out.println("Fetching Searched Employees...");
			empList = userService.searchByGrade(stringArray);
			showResults();
		} catch (InputMismatchException e) {
			log.error("Input type mismatch");
			System.err.println(Messages.INPUT_MISMATCH);
			scan.next();
		} catch (EMSException e) {
			log.error(e);
			System.err.println(e.getMessage());
		}
	}
	// search by department of employee
	private void searchByDepartment() {

		int count = 0;
		stringArray = new ArrayList<>();

		try {
			System.out.println("Enter Number of Departments to be Searched in");
			number = scan.nextInt();

			System.out.println("Enter <Employee Department Name/s>");
			while (count++ < number)
				stringArray.add(scan.next());

			System.out.println("Fetching Searched Employees...");
			empList = userService.searchByDept(stringArray);
			showResults();
		} catch (InputMismatchException e) {
			log.error("Input type mismatch");
			System.err.println(Messages.INPUT_MISMATCH);
			scan.next();
		} catch (EMSException e) {
			log.error(e);
			System.err.println(e.getMessage());
		}
	}

	// search by last name of employee
	private void searchByLastName() {
		System.out.println("Enter <Employee Last Name> and <Wildcard Character>");
		string = scan.next();
		wildcardChar = scan.next().charAt(0);
		if (wildcardChar != '*' && wildcardChar != '?') {
			log.error("Wildcard character is invalid");
			System.out.println("Invalid Character, Please Try Again");
			return;
		}
		try {
			System.out.println("Fetching Searched Employees...");
			empList = userService.searchByLastName(string, wildcardChar);
			showResults();
		} catch (EMSException e) {
			log.error(e);
			System.err.println(e.getMessage());
		}
	}
	// search by first name of employee
	private void searchByFirstName() {
		System.out.println("Enter <Employee First Name> and <Wildcard Character>");
		string = scan.next();
		wildcardChar = scan.next().charAt(0);
		if (wildcardChar != '*' && wildcardChar != '?') {
			log.error("Wildcard character is invalid");
			System.out.println("Invalid Character, Please Try Again");
			return;
		}
		try {
			System.out.println("Fetching Searched Employees...");
			empList = userService.searchByFirstName(string, wildcardChar);
			showResults();
		} catch (EMSException e) {
			log.error(e);
			System.err.println(e.getMessage());
		}
	}

	// search by id of employee
	private void searchById() {
		System.out.println("Enter <Employee Id> and <Wildcard Character>");
		string = scan.next();
		wildcardChar = scan.next().charAt(0);
		if (wildcardChar != '*' && wildcardChar != '?') {
			log.error("Wildcard character is invalid");
			System.out.println("Invalid Character, Please Try Again");
			return;
		}
		try {
			System.out.println("Fetching Searched Employees...");
			empList = userService.searchById(string, wildcardChar);
			showResults();
		} catch (EMSException e) {
			log.error(e);
			System.err.println(e.getMessage());
		}
	}

	// back to employee console
	private void backToEmployeeConsole() {
		log.info("returning to employee console");
		System.out.println("Returning to Employee Console...");
	}

	private void showSearchChoices() {
		log.info("showing choices to search employee");
		System.out.println("[1] By Id");
		System.out.println("[2] By First Name");
		System.out.println("[3] By Last Name");
		System.out.println("[4] By Department");
		System.out.println("[5] By Grade");
		System.out.println("[6] By Marital Status");
		System.out.println("[7] Go Back to Employee Console");
		System.out.println("[8] Exit");
		System.out.print("Your Choice ? ");
	}

	private void showResults() {
		if (empList.isEmpty()) {
			log.warn("Search results is empty");
			System.out.println("Sorry, No results found");
		} else {
			log.info("Showing searched results");
			empList.forEach(System.out::println);
		}
	}

	private void tryAgain() {
		log.warn("Invalid input choice");
		System.out.println("Invalid Choice, Please Try Again");
	}

	// terminate application
	private void exit() {
		log.info("Terminating the application");
		System.out.println("Exiting The Program...");
		System.exit(0);
	}

	// back to main console
	private void backToMain() {
		log.info("returning to main");
		System.out.println("Returning to Main...");
	}

}
