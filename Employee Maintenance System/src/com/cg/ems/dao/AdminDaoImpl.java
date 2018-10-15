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
import com.cg.ems.bean.User;
import com.cg.ems.exception.EMSException;
import com.cg.ems.util.ConnectionProvider;
import com.cg.ems.util.Messages;

public class AdminDaoImpl implements IAdminDao {

	static Logger logger = Logger.getLogger("applog");
	
	public AdminDaoImpl() {
		PropertyConfigurator.configure("resources//log4j.properties");
	}
	
	//------------------------Employee Management System --------------------------
	/*******************************************************************************************************
		- Function Name		:	addEmployee
		- Input Parameters	:	Employee object
		- Return Type		:	boolean
		- Throws			:  	EMSException
		- Author			:	
		- Creation Date		:	12/10/2018
		- Description		:	adding employee to database
	********************************************************************************************************/
	@Override
	public boolean addEmployee(Employee employee) throws EMSException {
		boolean success = false;
		
		if (employee != null) {
			try (Connection con = ConnectionProvider.getConnection();
					PreparedStatement st = con.prepareStatement(IQueryMapper.ADD_EMPLOYEE);) {
				st.setString(1, employee.getEmpId());
				st.setString(2, employee.getEmpFName());
				st.setString(3, employee.getEmpLName());
				st.setDate(4, employee.getEmpDOB());
				st.setDate(5, employee.getEmpDOJ());
				st.setInt(6, employee.getEmpDeptId());
				st.setString(7, employee.getEmpGrade());
				st.setString(8, employee.getEmpDesignation());
				st.setInt(9, employee.getEmpBasic());
				st.setString(10, Character.toString(employee.getEmpGender()));
				st.setString(11, employee.getEmpMarital());
				st.setString(12, employee.getEmpAddress());
				st.setString(13, employee.getEmpContact());
				st.setString(14, employee.getMgrId());
				st.setInt(15, employee.getEmpLeaveBal());
				int count = st.executeUpdate();

				if (count > 0) {
					success = true;
					logger.info("Record inserted in the database");
				}
				else {
					logger.error("Insertion failed");
				}

			} catch (SQLException e) {
				logger.error("Database Error : data not stored");
				throw new EMSException(Messages.NOT_INSERTED);
			}
		}
		return success;
	}
	
	/*******************************************************************************************************
		- Function Name		:	updateEmployee
		- Input Parameters	:	Employee object
		- Return Type		:	boolean
		- Throws			:  	EMSException
		- Author			:	
		- Creation Date		:	12/10/2018
		- Description		:	updating patient data
	********************************************************************************************************/
	@Override
	public boolean updateEmployee(Employee employee) throws EMSException {
		boolean success = false;
		
		try (Connection con = ConnectionProvider.getConnection();
				PreparedStatement st = con.prepareStatement(IQueryMapper.UPDATE_EMPLOYEE);) {
			st.setString(15, employee.getEmpId());

			st.setString(1, employee.getEmpFName());
			st.setString(2, employee.getEmpLName());
			st.setDate(3, employee.getEmpDOB());
			st.setDate(4, employee.getEmpDOJ());
			st.setInt(5, employee.getEmpDeptId());
			st.setString(6, employee.getEmpGrade());
			st.setString(7, employee.getEmpDesignation());
			st.setInt(8, employee.getEmpBasic());
			st.setString(9, Character.toString(employee.getEmpGender()));
			st.setString(10, employee.getEmpMarital());
			st.setString(11, employee.getEmpAddress());
			st.setString(12, employee.getEmpContact());
			st.setString(13, employee.getMgrId());
			st.setInt(14, employee.getEmpLeaveBal());
			int update = st.executeUpdate();
			if(update > 0) {
				success = true;
				logger.info("successfully updated");
			}
			else {
				logger.error("updation failed");
			}
		} catch (SQLException e) {
			logger.error("Database Error: data not updated");
			throw new EMSException(Messages.NOT_UPDATED);
		}
		return success;
	}
	
	/*******************************************************************************************************
		- Function Name		:	getAllEmployee
		- Input Parameters	:	None
		- Return Type		:	List of employee objects
		- Throws			:  	EMSException
		- Author			:	
		- Creation Date		:	12/10/2018
		- Description		:	Fetching details of all employees from database
	********************************************************************************************************/
	@Override
	public List<Employee> getAllEmployee() throws EMSException {

		List<Employee> empList = null;
		try (Connection con = ConnectionProvider.getConnection();
				PreparedStatement st = con.prepareStatement(IQueryMapper.LIST_EMPLOYEE);) {

			ResultSet rs = st.executeQuery();

			empList = new ArrayList<Employee>();
			while (rs.next()) {
				Employee emp = new Employee();
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
			logger.error("Database Error: data not selected");
			throw new EMSException(Messages.NOT_FETCHED);
		}
		return empList;
	}
	
	
	/*******************************************************************************************************
		- Function Name		:	getEmployeeById
		- Input Parameters	:	String EmployeeId
		- Return Type		:	Employee object
		- Throws			:  	EMSException
		- Author			:	
		- Creation Date		:	12/10/2018
		- Description		:	fetching employee by id
	********************************************************************************************************/

	@Override
	public Employee getEmployeeById(String empId) throws EMSException {
		Employee emp = null;
		try (Connection con = ConnectionProvider.getConnection();
				PreparedStatement st = con.prepareStatement(IQueryMapper.FIND_BY_ID);) {
			
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
			logger.error("Database Error:data not selected");
			throw new EMSException(Messages.NOT_FETCHED);
		}
		return emp;

	}
	
	/*******************************************************************************************************
		- Function Name		:	addUserCredentials
		- Input Parameters	:	User object
		- Return Type		:	boolean
		- Throws			:  	EMSException
		- Author			:	
		- Creation Date		:	12/10/2018
		- Description		:	adding user credentials
	********************************************************************************************************/

	@Override
	public boolean addUserCredentials(User user) throws EMSException {
		boolean success = false;
		Connection con = null;
		PreparedStatement st = null;
		if (user != null) {
			try {
				con = ConnectionProvider.getConnection();
				st = con.prepareStatement(IQueryMapper.ADD_USER);
				st.setString(1, user.getUserName());
				st.setString(2, user.getUserPassword());
				st.setString(3, user.getUserType());
				st.setString(4, user.getEmpId());
				int count = st.executeUpdate();
				if (count > 0) {
					success = true;
					logger.info("User credentials entered in database");
				}
				else {
					logger.error("Insertion error");
				}

			} catch (SQLException e) {
				logger.error("Database Error: Data not inserted");
				throw new EMSException(Messages.NOT_INSERTED);
			} finally {
				try {
					st.close();
					con.close();
				} catch (SQLException e) {
					logger.error("Database Error: Connection could not closed");
					throw new EMSException(Messages.CONNECTION_NOT_CLOSED);
				}
				
			}
		}
		return success;
	}

}
