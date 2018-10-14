package com.cg.ems.service;

import com.cg.ems.bean.User;
import com.cg.ems.exception.EMSException;

public interface IAuthenticationService {

	User getUser(String userName, String userPassword) throws EMSException;
}
