package com.cg.ems.ui;

import java.sql.Date;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


import java.util.concurrent.TimeUnit;

import com.cg.ems.bean.Employee;
import com.cg.ems.bean.EmployeeLeave;
import com.cg.ems.exception.EMSException;
import com.cg.ems.service.ILeaveApplicationService;
import com.cg.ems.service.IUserService;
import com.cg.ems.service.LeaveApplicationServiceImpl;
import com.cg.ems.service.UserService;
import com.cg.ems.util.Messages;

public class EmployeeConsole {
	
	private Scanner scan;
	IUserService userService;
	ILeaveApplicationService leaveService;
	private String empId;
	String string;
	int number;
	List<String> stringArray;
	char wildcardChar;
	
	public EmployeeConsole() {
		userService = new UserService();
		leaveService = new LeaveApplicationServiceImpl();
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public void start() {
		scan = new Scanner(System.in);
		int choice = 0;
		
		while(true) {
			String designation = null;
			try {
				designation = userService.getEmployeeById(empId).getEmpDesignation();
				if(designation.equals("Manager")) {
					showChoicesForManager();
					choice = scan.nextInt();
					switch(choice) {
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
				}
				else {
					showChoices();
					choice = scan.nextInt();
					switch(choice) {
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
			} catch(InputMismatchException e) {
				scan.next();
				System.err.println(Messages.INPUT_MISMATCH);
			} catch (EMSException e) {
				System.err.println(e.getMessage());
			}
		}
	}
	
	
	private void showChoicesToManageLeaves() {

		System.out.println("[1] Approve Leave");
		System.out.println("[2] Reject Leave");
		System.out.println("[3] Back to Employee Console");
		System.out.println("[4] Exit");
		System.out.print("Your Choice ? ");
		
		
	}
	
	private void showChoices() {

		System.out.println("[1] Search Employee");
		System.out.println("[2] Apply for Leave");
		System.out.println("[3] Back to Main");
		System.out.println("[4] Exit");
		System.out.print("Your Choice ? ");
		
		
	}
	private void showChoicesForManager() {

		System.out.println("[1] Search Employee");
		System.out.println("[2] Apply for Leave");
		System.out.println("[3] Manage Leaves");
		System.out.println("[4] Back to Main");
		System.out.println("[5] Exit");
		System.out.print("Your Choice ? ");
	}
	
	private void manageLeave() {
		int choice = 0;
		int leaveId = -1;
		scan = new Scanner(System.in);
		try {
			List<EmployeeLeave> empLeaveList = leaveService.getAllAppliedLeaves();
			empLeaveList.forEach(System.out::println);
			// if list is empty then show proper message
			System.out.println("Enter leaveId from the above Leave list to Approve/Reject ? ");
			leaveId = scan.nextInt();
			// check if this leaveId is valid or not in leave_history
			showChoicesToManageLeaves();
			choice = scan.nextInt();
			switch(choice) {
			case 1:
				if(leaveService.approveLeave(leaveId))
					System.out.println("Leave successfully approved");
				else
					System.err.println("Error occured, Not able to approve leave. Please try again...");
				break;
			case 2:
				if(leaveService.rejectLeave(leaveId))
					System.out.println("Leave successfully rejected");
				else
					System.err.println("Error occured, Not able to reject leave. Please try again...");
				break;
			case 3:
				backToEmployeeConsole();
				return;
			case 4:
				exit();
			default:
				tryAgain();
			}
		} catch(InputMismatchException e) {
			scan.next();
			System.err.println(Messages.INPUT_MISMATCH);
		} catch (EMSException e) {
			System.err.println(e.getMessage());
		}
	}

	private void applyLeave() {
		System.out.println("Apply for Leave");
		Employee employee = null;
		try {
			
			employee = userService.getEmployeeById(empId);
			
			System.out.println(employee.toString());
			int leaveBal = employee.getEmpLeaveBal();
			System.out.println("You have Leaves Left: " + leaveBal);
			EmployeeLeave empLeave = new EmployeeLeave();
			
			System.out.println("From Date ? ");
			empLeave.setFromDate(Date.valueOf(scan.next()));
			System.out.println("To Date ? ");
			empLeave.setToDate(Date.valueOf(scan.next()));
			
			int diffDays = (int )TimeUnit.MILLISECONDS.toDays(empLeave.getToDate().getTime()
					- empLeave.getFromDate().getTime());
			empLeave.setLeaveDuration(diffDays);
			System.out.println(diffDays);
			System.out.println(leaveBal);
			if(diffDays < 0 || diffDays > leaveBal) {
				System.out.println("Sorry, Not Sufficient Leaves You have");
				return;
			}
			
			// leaveId is generated by sql sequence
			//applied date is set by sql so
			
			empLeave.setEmpId(empId);
			empLeave.setStatus("Applied");
			leaveService.applyLeave(empLeave);
			System.out.println("Leave Successfully Applied");
		} catch (EMSException e) {
			System.err.println(e.getMessage());
		}
		
	}

	private void searchEmployee() {
		
		int choice = 0;
		while(true) {
			try {
				System.out.println("Search Employee");
				showSearchChoices();
				choice = scan.nextInt();
				switch(choice) {
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
			} catch(InputMismatchException e) {
				scan.next();
				System.err.println(Messages.INPUT_MISMATCH);
			}
		}
		
	}

	private void searchByMaritalStatus() {
		
		int count = 0;
		stringArray = new ArrayList<>();
		
		try {
			System.out.println("Enter Number of Marital Status to be Searched in");
			number = scan.nextInt();
			
			System.out.println("Enter <Employee Marital Status/es>");
			while(count++ < number) stringArray.add(scan.next());
			
			System.out.println("Fetching Searched Employees...");
			userService.searchByMarital(stringArray).forEach(System.out::println);
			
		} catch(InputMismatchException e) {
			scan.next();
			System.err.println(Messages.INPUT_MISMATCH);
		} catch (EMSException e) {
			System.out.println(e.getMessage());
		}
	}

	private void searchByGrade() {
		
		int count = 0;
		stringArray = new ArrayList<>();
		
		
		try {
			System.out.println("Enter Number of Grades to be Searched in");
			number = scan.nextInt();
			
			System.out.println("Enter <Employee Grade/s>");
			while(count++ < number) stringArray.add(scan.next());
			
			System.out.println("Fetching Searched Employees...");
			userService.searchByGrade(stringArray).forEach(System.out::println);
			
		} catch(InputMismatchException e) {
			scan.next();
			System.err.println(Messages.INPUT_MISMATCH);
		} catch (EMSException e) {
			System.err.println(e.getMessage());
		}
	}

	private void searchByDepartment() {
		
		int count = 0;
		stringArray = new ArrayList<>();
		
		try {
			System.out.println("Enter Number of Departments to be Searched in");
			number = scan.nextInt();
			
			System.out.println("Enter <Employee Department Name/s>");
			while(count++ < number) stringArray.add(scan.next());
			
			System.out.println("Fetching Searched Employees...");
			userService.searchByDept(stringArray).forEach(System.out::println);
			
		} catch(InputMismatchException e) {
			scan.next();
			System.err.println(Messages.INPUT_MISMATCH);
		} catch (EMSException e) {
			System.err.println(e.getMessage());
		}
	}

	private void searchByLastName() {
		System.out.println("Enter <Employee Last Name> and <Wildcard Character>");
		string = scan.next();
		wildcardChar = scan.next().charAt(0);
		if(wildcardChar != '*' && wildcardChar != '?') {
			System.out.println("Invalid Character, Please Try Again");
			return;
		}
		try {
			System.out.println("Fetching Searched Employees...");
			userService.searchByLastName(string, wildcardChar).forEach(System.out::println);
		} catch (EMSException e) {
			System.err.println(e.getMessage());
		}
	}

	private void searchByFirstName() {
		System.out.println("Enter <Employee First Name> and <Wildcard Character>");
		string = scan.next();
		wildcardChar = scan.next().charAt(0);
		if(wildcardChar != '*' && wildcardChar != '?') {
			System.out.println("Invalid Character, Please Try Again");
			return;
		}
		try {
			System.out.println("Fetching Searched Employees...");
			userService.searchByFirstName(string, wildcardChar).forEach(System.out::println);
		} catch (EMSException e) {
			System.err.println(e.getMessage());
		}
	}

	private void searchById() {
		System.out.println("Enter <Employee Id> and <Wildcard Character>");
		string = scan.next();
		wildcardChar = scan.next().charAt(0);
		if(wildcardChar != '*' && wildcardChar != '?') {
			System.out.println("Invalid Character, Please Try Again");
			return;
		}
		try {
			System.out.println("Fetching Searched Employees...");
			userService.searchById(string, wildcardChar).forEach(System.out::println);
		} catch (EMSException e) {
			System.err.println(e.getMessage());
		}
	}

	private void backToEmployeeConsole() {
		System.out.println("Returning to Employee Console...");
	}

	private void showSearchChoices() {
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
	
	
}
