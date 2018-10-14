package com.cg.ems.service;

import java.util.List;

import com.cg.ems.bean.Employee;
import com.cg.ems.dao.AdminDaoImpl;
import com.cg.ems.dao.IAdminDao;
import com.cg.ems.exception.EMSException;

public class AdminServiceImpl implements IAdminService {

	IAdminDao adminDao;
	
	public AdminServiceImpl() {
		adminDao = new AdminDaoImpl();
	}

	@Override
	public boolean addEmployee(Employee employee) throws EMSException {
		return adminDao.addEmployee(employee);
	}

	@Override
	public boolean updateEmployee(Employee employee) throws EMSException {
		return adminDao.updateEmployee(employee);
	}

	@Override
	public List<Employee> getAllEmployee() throws EMSException {
		return adminDao.getAllEmployee();
	}

	@Override
	public Employee getEmployeeById(String empId) throws EMSException {
		return adminDao.getEmployeeById(empId);
	}

}
