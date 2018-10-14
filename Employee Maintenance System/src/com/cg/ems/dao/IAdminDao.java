package com.cg.ems.dao;

import java.util.List;

import com.cg.ems.bean.Employee;
import com.cg.ems.bean.User;
import com.cg.ems.exception.EMSException;

public interface IAdminDao {

		boolean addEmployee(Employee employee) throws EMSException;
		
		boolean updateEmployee(Employee employee) throws EMSException;
		
		List<Employee> getAllEmployee() throws EMSException;
		
		Employee getEmployeeById(String empId) throws EMSException;

		boolean addUserCredentials(User user) throws EMSException;
}
