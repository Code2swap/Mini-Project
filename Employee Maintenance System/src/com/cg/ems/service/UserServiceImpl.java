package com.cg.ems.service;

import java.util.List;

import com.cg.ems.bean.Employee;
import com.cg.ems.dao.IUserDao;
import com.cg.ems.dao.UserDaoImpl;
import com.cg.ems.exception.EMSException;

public class UserServiceImpl implements IUserService {

	IUserDao userDao;
	
	
	public UserServiceImpl() {
		userDao = new UserDaoImpl();
	}

	@Override
	public Employee getEmployeeById(String empId) throws EMSException {
		return userDao.getEmployeeById(empId);
	}
	
	@Override
	public List<Employee> searchById(String empId, char wildcardChar) throws EMSException {
		return userDao.searchById(empId, wildcardChar);
	}

	@Override
	public List<Employee> searchByFirstName(String empFName, char wildcardChar) throws EMSException {
		return userDao.searchByFirstName(empFName, wildcardChar);
	}

	@Override
	public List<Employee> searchByLastName(String empLName, char wildcardChar) throws EMSException {
		return userDao.searchByLastName(empLName, wildcardChar);
	}

	@Override
	public List<Employee> searchByDept(List<String> empDeptNames) throws EMSException {
		return userDao.searchByDept(empDeptNames);
	}

	@Override
	public List<Employee> searchByGrade(List<String> empGrades) throws EMSException {
		return userDao.searchByGrade(empGrades);
	}

	@Override
	public List<Employee> searchByMarital(List<String> empMarital) throws EMSException {
		return userDao.searchByMarital(empMarital);
	}
		
}
