package com.cg.ems.dao;

import com.cg.ems.bean.User;
import com.cg.ems.exception.EMSException;

public interface IAuthenticationDao {

	User getUser(String userName, String userPassword) throws EMSException;
}
