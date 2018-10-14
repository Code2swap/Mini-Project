package com.cg.ems.ui;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.cg.ems.bean.EmployeeLeave;
import com.cg.ems.bean.User;
import com.cg.ems.exception.EMSException;
import com.cg.ems.service.AuthenticationServiceImpl;
import com.cg.ems.service.AutoApprovalServiceImpl;
import com.cg.ems.service.IAuthenticationService;
import com.cg.ems.service.IAutoApprovalService;
import com.cg.ems.util.Messages;

public class MainUI {

	static AdminConsole admin = null;
	static EmployeeConsole emp = null;
	static IAuthenticationService service = new AuthenticationServiceImpl();
	static IAutoApprovalService approvalService = new AutoApprovalServiceImpl();
	private static Logger log = Logger.getLogger("Main Console");
	
	// start of the application
	public static void main(String[] args) {
		
		PropertyConfigurator.configure("resources/log4j.properties");
		int choice = -1;
		Scanner scan = new Scanner(System.in);
		while(true) {
				
			try {
				List<EmployeeLeave> empLeave = approvalService.autoApprove();
				if(!empLeave.isEmpty()) {
					System.out.println("The follwing leaves are auto approved");
					empLeave.forEach(System.out::println);
				}
				else {
					log.info("No leaves to auto approve");
				}
				choice = showChoices(scan);
				switch(choice) {
				case 1:
					userConsole(scan);
					break;
				case 2:
					exit();
					break;
				default:
					tryAgain();
				}
				
				if(choice == 2) break;
				
			} catch (EMSException e) {
				log.error(e);
				System.err.println(e.getMessage());
			}
		}
		scan.close();
		System.out.println("Program Terminated");
		System.out.println("Thankyou, Visit Again!!!!");

	}

	private static void userConsole(Scanner scan) throws EMSException {
		log.info("user login");
		User user = null;
		System.out.print("UserName? ");
		String userName = scan.next();
		System.out.print("Password? ");
		String userPassword = scan.next();
		user = service.getUser(userName, userPassword);
		if (user != null) {
			log.info("Login successful");
			if(user.getUserType().equals("ADMIN")) {
				admin = new AdminConsole();
				admin.start();
			}
			else {
				emp = new EmployeeConsole();
				emp.setEmpId(user.getEmpId());
				emp.start();
			}
		}
		else {
			log.error("Username or password is not correct");
			System.err.println("Invalid Username or Password");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				log.error("Sleeping interrupted");
				System.err.println("Event interrupted");
			}
		}
	}
	

	private static void tryAgain() {
		System.out.println("Invalid Choice, Please Try Again");
	}

	private static void exit() {
		System.out.println("Exiting The Program...");
	}
	
	private static int showChoices(Scanner scan) throws EMSException {
		int choice = -1;
		System.out.println("[1] Login");
		System.out.println("[2] Exit");
		System.out.print("Your Choice ? ");
		try {
			choice = scan.nextInt();
		} catch(InputMismatchException e) {
			scan.next();
			throw new EMSException(Messages.INPUT_MISMATCH);
		}
		return choice;
	}
}
