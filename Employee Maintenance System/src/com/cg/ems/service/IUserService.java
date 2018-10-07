package com.cg.ems.service;

import java.util.List;

import com.cg.ems.bean.Employee;
import com.cg.ems.exception.EMSException;

public interface IUserService {

	List<Employee> searchById(String empId, char wildcardChar) throws EMSException;
	
	List<Employee> searchByFirstName(String empFName, char wildcardChar) throws EMSException;
	
	List<Employee> searchByLastName(String empLName, char wildcardChar) throws EMSException;
	
	List<Employee> searchByDept(List<String> empDeptNames) throws EMSException;
	
	List<Employee> searchByGrade(List<String> empGrades) throws EMSException;
		
	Employee getEmployeeById(String empId) throws EMSException;

	List<Employee> searchByMarital(List<String> empMarital) throws EMSException;
}
