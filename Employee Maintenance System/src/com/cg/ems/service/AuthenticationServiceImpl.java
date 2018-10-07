package com.cg.ems.service;

import com.cg.ems.bean.User;
import com.cg.ems.dao.AuthenticationDaoImpl;
import com.cg.ems.dao.IAuthenticationDao;
import com.cg.ems.exception.EMSException;

public class AuthenticationServiceImpl implements IAuthenticationService {

	IAuthenticationDao authenticationDao;
	
	public AuthenticationServiceImpl() {
		authenticationDao = new AuthenticationDaoImpl();
	}

	public User getUser(String userName, String userPassword) throws EMSException {
		return authenticationDao.getUser(userName, userPassword);
	}
}
