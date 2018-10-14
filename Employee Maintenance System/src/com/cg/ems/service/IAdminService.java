package com.cg.ems.service;

import java.util.List;

import com.cg.ems.bean.Employee;
import com.cg.ems.bean.User;
import com.cg.ems.exception.EMSException;

public interface IAdminService {

	boolean addEmployee(Employee employee) throws EMSException;
	
	boolean updateEmployee(Employee employee) throws EMSException;
	
	List<Employee> getAllEmployee() throws EMSException;

	Employee getEmployeeById(String empId) throws EMSException;

	boolean addUserCredentials(User user) throws EMSException;
	
	List<String> validateDetails(Employee employee) throws EMSException;
}
