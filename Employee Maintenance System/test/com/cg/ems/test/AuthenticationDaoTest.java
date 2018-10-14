package com.cg.ems.test;

import static org.junit.Assert.assertNotNull;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cg.ems.bean.User;
import com.cg.ems.dao.AuthenticationDaoImpl;
import com.cg.ems.dao.IAuthenticationDao;
import com.cg.ems.exception.EMSException;

public class AuthenticationDaoTest {
	
	static IAuthenticationDao authenticationDao;
	static User user;
	@BeforeClass
	public static void initialize() throws EMSException {
		System.out.println("Testing Authentication DAO");
		authenticationDao = new AuthenticationDaoImpl();
		user = new User("1008","LUCY","lucy123","EMPLOYEE", "300001");
		// add this user in User_Master table first
	}
	
	@Test
	public void getUser() throws EMSException {
		assertNotNull("Getting User Failed", authenticationDao.getUser("LUCY","lucy123"));
	}
	
	@AfterClass
	public static void destroy() {
		System.out.println("\nTest Ended");
		authenticationDao = null;
		user = null;
	}
}
