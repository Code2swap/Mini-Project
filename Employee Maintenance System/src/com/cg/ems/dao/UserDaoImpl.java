package com.cg.ems.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.cg.ems.bean.Employee;
import com.cg.ems.exception.EMSException;
import com.cg.ems.util.ConnectionProvider;
import com.cg.ems.util.Messages;
import com.cg.ems.dao.IQueryMapper;

public class UserDaoImpl implements IUserDao {

	static Logger logger = Logger.getLogger("applog");
	
	public UserDaoImpl() {
		
		PropertyConfigurator.configure("resources//log4j.properties");
		
	}
	
	//------------------------Employee Management System --------------------------
		/*******************************************************************************************************
			- Function Name		:	getEmployeeById
			- Input Parameters	:	String Employee id
			- Return Type		:	Employee object
			- Throws			:  	EMSException
			- Author			:	
			- Creation Date		:	12/10/2018
			- Description		:	fetch employee details by id
		********************************************************************************************************/	
	@Override
	public Employee getEmployeeById(String empId) throws EMSException {
		Employee emp = null;
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = ConnectionProvider.getConnection();
			st = con.prepareStatement(IQueryMapper.FIND_BY_ID);

			st.setString(1, empId);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				emp = new Employee();
				emp.setEmpId(rs.getString(1));
				emp.setEmpFName(rs.getString(2));
				emp.setEmpLName(rs.getString(3));
				emp.setEmpDOB(rs.getDate(4));
				emp.setEmpDOJ(rs.getDate(5));
				emp.setEmpDeptId(rs.getInt(6));
				emp.setEmpGrade(rs.getString(7));
				emp.setEmpDesignation(rs.getString(8));
				emp.setEmpBasic(rs.getInt(9));
				emp.setEmpGender(rs.getString(10).charAt(0));
				emp.setEmpMarital(rs.getString(11));
				emp.setEmpAddress(rs.getString(12));
				emp.setEmpContact(rs.getString(13));
				emp.setMgrId(rs.getString(14));
				emp.setEmpLeaveBal(rs.getInt(15));
			}

		} catch (SQLException e) {
			logger.error("Database error: Unable to fetch record");
			throw new EMSException(Messages.NOT_FETCHED);
		} finally {

			try {
				st.close();
				con.close();
			} catch (SQLException e) {
				logger.error("Database error: Unable to close connection");
				throw new EMSException(Messages.CONNECTION_NOT_CLOSED);
			}

		}
		return emp;

	}
	//------------------------Employee Management System --------------------------
			/*******************************************************************************************************
				- Function Name		:	searchById
				- Input Parameters	:	String Employee id,char wildcard character
				- Return Type		:	List of Employee object
				- Throws			:  	EMSException
				- Author			:	
				- Creation Date		:	12/10/2018
				- Description		:	fetch employee details by id and wildcard character
			********************************************************************************************************/	
	@Override
	public List<Employee> searchById(String empId, char wildcardChar) throws EMSException {

		Employee emp = null;
		Connection con = null;
		PreparedStatement st = null;
		List<Employee> empList = null;

		try {

			con = ConnectionProvider.getConnection();
			st = con.prepareStatement(IQueryMapper.SEARCH_BY_ID);

			if (wildcardChar == '*') {
				st.setString(1, empId + '%');
			} else {
				st.setString(1, empId + '_');
			}

			ResultSet rs = st.executeQuery();
			empList = new ArrayList<Employee>();
			while (rs.next()) {
				emp = new Employee();
				emp.setEmpId(rs.getString(1));
				emp.setEmpFName(rs.getString(2));
				emp.setEmpLName(rs.getString(3));
				emp.setEmpDOB(rs.getDate(4));
				emp.setEmpDOJ(rs.getDate(5));
				emp.setEmpDeptId(rs.getInt(6));
				emp.setEmpGrade(rs.getString(7));
				emp.setEmpDesignation(rs.getString(8));
				emp.setEmpBasic(rs.getInt(9));
				emp.setEmpGender(rs.getString(10).charAt(0));
				emp.setEmpMarital(rs.getString(11));
				emp.setEmpAddress(rs.getString(12));
				emp.setEmpContact(rs.getString(13));
				emp.setMgrId(rs.getString(14));
				emp.setEmpLeaveBal(rs.getInt(15));
				empList.add(emp);
			}

		} catch (SQLException e) {
			logger.error("Database error: Unable to fetch record");
			throw new EMSException(Messages.NOT_FETCHED);
		} finally {

			try {
				st.close();
				con.close();
			} catch (SQLException e) {
				logger.error("Database error: Unable to close cnnection");
				throw new EMSException(Messages.CONNECTION_NOT_CLOSED);
			}

		}
		return empList;

	}
	
	//------------------------Employee Management System --------------------------
	/*******************************************************************************************************
		- Function Name		:	searchByFirstName
		- Input Parameters	:	String Employee Name,char wildcard character
		- Return Type		:	List of Employee object
		- Throws			:  	EMSException
		- Author			:	
		- Creation Date		:	12/10/2018
		- Description		:	fetch employee details by first name and wildcard character
	********************************************************************************************************/	

	@Override
	public List<Employee> searchByFirstName(String empFName, char wildcardChar) throws EMSException {

		Employee emp = null;
		Connection con = null;
		PreparedStatement st = null;
		List<Employee> empList = null;

		try {

			con = ConnectionProvider.getConnection();
			st = con.prepareStatement(IQueryMapper.SEARCH_BY_FIRST_NAME);

			if (wildcardChar == '*') {
				st.setString(1, empFName + '%');
			} else {
				st.setString(1, empFName + '_');
			}

			ResultSet rs = st.executeQuery();
			empList = new ArrayList<Employee>();
			while (rs.next()) {
				emp = new Employee();
				emp.setEmpId(rs.getString(1));
				emp.setEmpFName(rs.getString(2));
				emp.setEmpLName(rs.getString(3));
				emp.setEmpDOB(rs.getDate(4));
				emp.setEmpDOJ(rs.getDate(5));
				emp.setEmpDeptId(rs.getInt(6));
				emp.setEmpGrade(rs.getString(7));
				emp.setEmpDesignation(rs.getString(8));
				emp.setEmpBasic(rs.getInt(9));
				emp.setEmpGender(rs.getString(10).charAt(0));
				emp.setEmpMarital(rs.getString(11));
				emp.setEmpAddress(rs.getString(12));
				emp.setEmpContact(rs.getString(13));
				emp.setMgrId(rs.getString(14));
				emp.setEmpLeaveBal(rs.getInt(15));
				empList.add(emp);
			}

		} catch (SQLException e) {
			logger.error("Database error: Unable to fetch record");
			throw new EMSException(Messages.NOT_FETCHED);
		} finally {

			try {
				st.close();
				con.close();
			} catch (SQLException e) {
				logger.error("Database error: Unable to close connection");
				throw new EMSException(Messages.CONNECTION_NOT_CLOSED);
			}

		}
		return empList;
	}

	//------------------------Employee Management System --------------------------
	/*******************************************************************************************************
		- Function Name		:	searchByLastName
		- Input Parameters	:	String Employee last name,char wildcard character
		- Return Type		:	List of Employee object
		- Throws			:  	EMSException
		- Author			:	
		- Creation Date		:	12/10/2018
		- Description		:	fetch employee details by last name and wildcard character
	********************************************************************************************************/	
	@Override
	public List<Employee> searchByLastName(String empLName, char wildcardChar) throws EMSException {
		Employee emp = null;
		Connection con = null;
		PreparedStatement st = null;
		List<Employee> empList = null;

		try {

			con = ConnectionProvider.getConnection();
			st = con.prepareStatement(IQueryMapper.SEARCH_BY_LAST_NAME);

			if (wildcardChar == '*') {
				st.setString(1, empLName + '%');
			} else {
				st.setString(1, empLName + '_');
			}

			ResultSet rs = st.executeQuery();
			empList = new ArrayList<Employee>();
			while (rs.next()) {
				emp = new Employee();
				emp.setEmpId(rs.getString(1));
				emp.setEmpFName(rs.getString(2));
				emp.setEmpLName(rs.getString(3));
				emp.setEmpDOB(rs.getDate(4));
				emp.setEmpDOJ(rs.getDate(5));
				emp.setEmpDeptId(rs.getInt(6));
				emp.setEmpGrade(rs.getString(7));
				emp.setEmpDesignation(rs.getString(8));
				emp.setEmpBasic(rs.getInt(9));
				emp.setEmpGender(rs.getString(10).charAt(0));
				emp.setEmpMarital(rs.getString(11));
				emp.setEmpAddress(rs.getString(12));
				emp.setEmpContact(rs.getString(13));
				emp.setMgrId(rs.getString(14));
				emp.setEmpLeaveBal(rs.getInt(15));
				empList.add(emp);
			}

		} catch (SQLException e) {
			logger.error("Database error: Unable to fetch record");
			throw new EMSException(Messages.NOT_FETCHED);
		} finally {

			try {
				st.close();
				con.close();
			} catch (SQLException e) {
				logger.error("Database error: Unable to close connection");
				throw new EMSException(Messages.CONNECTION_NOT_CLOSED);
			}

		}
		return empList;

	}
	
	//------------------------Employee Management System --------------------------
	/*******************************************************************************************************
		- Function Name		:	searchByDept
		- Input Parameters	:	List of String Departments
		- Return Type		:	List of Employee object
		- Throws			:  	EMSException
		- Author			:	
		- Creation Date		:	12/10/2018
		- Description		:	fetch employee details by passing department list 
	********************************************************************************************************/	

	@Override
	public List<Employee> searchByDept(List<String> empDeptNames) throws EMSException {

		Employee emp = null;
		Connection con = null;
		PreparedStatement st = null;
		String query = "SELECT * FROM Employee WHERE Emp_Dept_Id IN"
				+ "(SELECT DISTINCT Dept_Id FROM Department WHERE Dept_Name IN (";
		List<Employee> empList;
		int size = empDeptNames.size();
		for (int count = 0; count < size; count++) {
			if (count == 0)
				query += "?";
			else
				query += ", ?";
		}
		query += "))";
		try {

			con = ConnectionProvider.getConnection();
			st = con.prepareStatement(query);
			for (int count = 0; count < size; count++)
				st.setString(count + 1, empDeptNames.get(count));
			ResultSet rs = st.executeQuery();
			empList = new ArrayList<Employee>();
			while (rs.next()) {
				emp = new Employee();
				emp.setEmpId(rs.getString(1));
				emp.setEmpFName(rs.getString(2));
				emp.setEmpLName(rs.getString(3));
				emp.setEmpDOB(rs.getDate(4));
				emp.setEmpDOJ(rs.getDate(5));
				emp.setEmpDeptId(rs.getInt(6));
				emp.setEmpGrade(rs.getString(7));
				emp.setEmpDesignation(rs.getString(8));
				emp.setEmpBasic(rs.getInt(9));
				emp.setEmpGender(rs.getString(10).charAt(0));
				emp.setEmpMarital(rs.getString(11));
				emp.setEmpAddress(rs.getString(12));
				emp.setEmpContact(rs.getString(13));
				emp.setMgrId(rs.getString(14));
				emp.setEmpLeaveBal(rs.getInt(15));
				empList.add(emp);
			}

		} catch (SQLException e) {
			logger.error("Database error: Unable to fetch data");
			throw new EMSException(Messages.NOT_FETCHED);
		} finally {

			try {
				st.close();
				con.close();
			} catch (SQLException e) {
				logger.error("Database error: Unable to close connection");
				throw new EMSException(Messages.CONNECTION_NOT_CLOSED);
			}

		}
		return empList;

	}

	//------------------------Employee Management System --------------------------
	/*******************************************************************************************************
		- Function Name		:	searchByGrade
		- Input Parameters	:	List of String grades 
		- Return Type		:	List of Employee object
		- Throws			:  	EMSException
		- Author			:	
		- Creation Date		:	12/10/2018
		- Description		:	fetch employee details by list of grades 
	********************************************************************************************************/	
	@Override
	public List<Employee> searchByGrade(List<String> empGrades) throws EMSException {

		Employee emp = null;
		Connection con = null;
		PreparedStatement st = null;
		List<Employee> empList = null;
		String query = "SELECT * FROM Employee WHERE Emp_Grade IN (";
		int size = empGrades.size();
		for (int count = 0; count < size; count++) {
			if (count == 0)
				query += "?";
			else
				query += ", ?";
		}
		query += ")";
		
		try {

			con = ConnectionProvider.getConnection();
			st = con.prepareStatement(query);
			for (int count = 0; count < size; count++)
				st.setString(count + 1, empGrades.get(count));
			ResultSet rs = st.executeQuery();
			empList = new ArrayList<Employee>();
			while (rs.next()) {
				emp = new Employee();
				emp.setEmpId(rs.getString(1));
				emp.setEmpFName(rs.getString(2));
				emp.setEmpLName(rs.getString(3));
				emp.setEmpDOB(rs.getDate(4));
				emp.setEmpDOJ(rs.getDate(5));
				emp.setEmpDeptId(rs.getInt(6));
				emp.setEmpGrade(rs.getString(7));
				emp.setEmpDesignation(rs.getString(8));
				emp.setEmpBasic(rs.getInt(9));
				emp.setEmpGender(rs.getString(10).charAt(0));
				emp.setEmpMarital(rs.getString(11));
				emp.setEmpAddress(rs.getString(12));
				emp.setEmpContact(rs.getString(13));
				emp.setMgrId(rs.getString(14));
				emp.setEmpLeaveBal(rs.getInt(15));
				empList.add(emp);
			}

		} catch (SQLException e) {
			logger.error("Database error: Unable to fetch record");
			throw new EMSException(Messages.NOT_FETCHED);
		} finally {

			try {
				st.close();
				con.close();
			} catch (SQLException e) {
				logger.error("Database error: Unable to close connection");
				throw new EMSException(Messages.CONNECTION_NOT_CLOSED);
			}

		}
		return empList;

	}

	//------------------------Employee Management System --------------------------
	/*******************************************************************************************************
		- Function Name		:	searchByMarital
		- Input Parameters	:	list of String marital status
		- Return Type		:	List of Employee object
		- Throws			:  	EMSException
		- Author			:	
		- Creation Date		:	12/10/2018
		- Description		:	fetch employee details by Marital status
	********************************************************************************************************/	
	@Override
	public List<Employee> searchByMarital(List<String> empMarital) throws EMSException {

		Employee emp = null;
		Connection con = null;
		PreparedStatement st = null;
		List<Employee> empList = null;
		String query = "SELECT * FROM Employee WHERE Emp_Marital_Status IN (";
		int size = empMarital.size();
		for (int count = 0; count < size; count++) {
			if (count == 0)
				query += "?";
			else
				query += ", ?";
		}
		query += ")";
		
		try {

			con = ConnectionProvider.getConnection();
			st = con.prepareStatement(query);

			for (int count = 0; count < size; count++)
				st.setString(count + 1, empMarital.get(count));

			ResultSet rs = st.executeQuery();
			empList = new ArrayList<Employee>();
			while (rs.next()) {
				emp = new Employee();
				emp.setEmpId(rs.getString(1));
				emp.setEmpFName(rs.getString(2));
				emp.setEmpLName(rs.getString(3));
				emp.setEmpDOB(rs.getDate(4));
				emp.setEmpDOJ(rs.getDate(5));
				emp.setEmpDeptId(rs.getInt(6));
				emp.setEmpGrade(rs.getString(7));
				emp.setEmpDesignation(rs.getString(8));
				emp.setEmpBasic(rs.getInt(9));
				emp.setEmpGender(rs.getString(10).charAt(0));
				emp.setEmpMarital(rs.getString(11));
				emp.setEmpAddress(rs.getString(12));
				emp.setEmpContact(rs.getString(13));
				emp.setMgrId(rs.getString(14));
				emp.setEmpLeaveBal(rs.getInt(15));
				empList.add(emp);
			}

		} catch (SQLException e) {
			logger.error("Database error: Unable to fetch record");
			throw new EMSException(Messages.NOT_FETCHED);
		} finally {

			try {
				st.close();
				con.close();
			} catch (SQLException e) {
				logger.error("Database error: Unable to close connection");
				throw new EMSException(Messages.CONNECTION_NOT_CLOSED);
			}

		}
		return empList;

	}
}
