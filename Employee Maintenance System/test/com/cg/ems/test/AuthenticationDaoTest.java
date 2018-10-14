package com.cg.ems.test;

import static org.junit.Assert.assertEquals;

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
		user = new User("1009","LUCY","lucy123","EMPLOYEE", "100009");
	}
	
	@Test
	public void getUser() throws EMSException {
		assertEquals("Getting User Failed", user, authenticationDao.getUser("LUCY","lucy123"));
	}
	
	@AfterClass
	public static void destroy() {
		System.out.println("\nTest Ended");
		authenticationDao = null;
		user = null;
	}
}
