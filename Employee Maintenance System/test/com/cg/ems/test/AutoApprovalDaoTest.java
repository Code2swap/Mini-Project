package com.cg.ems.test;

import static org.junit.Assert.*;


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cg.ems.dao.AutoApprovalDaoImpl;
import com.cg.ems.dao.IAutoApprovalDao;
import com.cg.ems.exception.EMSException;

public class AutoApprovalDaoTest {

	static IAutoApprovalDao autoApprovalDao;

	@BeforeClass
	public static void initialize() throws EMSException {
		System.out.println("Testing Authentication DAO");
		autoApprovalDao = new AutoApprovalDaoImpl();
		
	}
	
	@Test
	public void autoApprove() throws EMSException { 
		// make sure there are no leave with status: Applied
		assertTrue(autoApprovalDao.autoApprove().isEmpty());
	}
	@AfterClass
	public static void destroy() {
		System.out.println("\nTest Ended");
		autoApprovalDao = null;
	}

}
