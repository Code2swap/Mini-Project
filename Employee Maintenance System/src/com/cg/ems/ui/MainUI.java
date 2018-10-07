package com.cg.ems.ui;

import java.util.Scanner;

import org.apache.log4j.PropertyConfigurator;

import com.cg.ems.bean.User;
import com.cg.ems.exception.EMSException;
import com.cg.ems.service.AuthenticationServiceImpl;
import com.cg.ems.service.IAuthenticationService;

public class MainUI {

	static AdminConsole admin = null;
	static EmployeeConsole emp = null;
	static IAuthenticationService service = new AuthenticationServiceImpl();
	
	public static void main(String[] args) {
		
		PropertyConfigurator.configure("resources/log4j.properties");
		int choice = -1;
		while(true) {
			Scanner scan = new Scanner(System.in);
			showChoices();
			choice = scan.nextInt();
			switch(choice) {
			case 1:
				adminConsole(scan);
				break;
			case 2:
				employeeConsole(scan);
				break;
			case 3:
				exit();
			default:
				tryAgain();
			}
			scan.close();
			if(choice == 3) break;
		}
		
		System.out.println("Program Terminated");
		System.out.println("Thankyou, Visit Again!!!!");

	}

	private static void employeeConsole(Scanner scan) {
		User user = null;
		System.out.print("UserName? ");
		String userName = scan.next();
		System.out.print("Password? ");
		String userPassword = scan.next();
		try {
			user = service.getUser(userName, userPassword);
		} catch (EMSException e) {
			System.out.println(e.getMessage());
		}
		if (user != null) {
			emp = new EmployeeConsole();
			emp.setEmpId(user.getEmpId());
			emp.start();
		} else
			System.out.println("Invalid Username or Password");
	}

	private static void adminConsole(Scanner scan) {
		User user = null;
		System.out.print("UserName ? ");
		String userName = scan.next();
		System.out.print("Password ? ");
		String userPassword = scan.next();
		
		try {
			user = service.getUser(userName, userPassword);
		} catch (EMSException e) {
			System.out.println(e.getMessage());
		}
		if (user != null) {
			admin = new AdminConsole();
			admin.start();
		} else
			System.out.println("Invalid Username or Password");
	}

	private static void tryAgain() {
		System.out.println("Invalid Choice, Please Try Again");
	}

	private static void exit() {
		System.out.println("Exiting The Program...");
	}
	
	private static void showChoices() {

		System.out.println("[1] Login as Admin");
		System.out.println("[2] Login as Employee");
		System.out.println("[3] Exit");
		System.out.print("Your Choice ? ");

	}
}
