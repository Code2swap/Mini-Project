package com.cg.ems.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.cg.ems.bean.EmployeeLeave;
import com.cg.ems.exception.EMSException;
import com.cg.ems.util.ConnectionProvider;
import com.cg.ems.util.Messages;

public class LeaveApplicationDaoImpl implements ILeaveApplicationDao {

	static Logger logger = Logger.getLogger("applog");
	
	public LeaveApplicationDaoImpl() {
		
		PropertyConfigurator.configure("resources//log4j.properties");
	}
	
	//------------------------Employee Management System --------------------------
	/*******************************************************************************************************
		- Function Name		:	applyLeave
		- Input Parameters	:	EmployeeLeave object
		- Return Type		:	boolean
		- Throws			:  	EMSException
		- Author			:	
		- Creation Date		:	12/10/2018
		- Description		:	employee applies for a leave
	********************************************************************************************************/	
	@Override
	public boolean applyLeave(EmployeeLeave empLeave) throws EMSException {

		boolean result = false;
		PreparedStatement st = null;
		Connection con = null;
		if (empLeave != null) {

			try {
				con = ConnectionProvider.getConnection();
				st = con.prepareStatement(IQueryMapper.APPLY_LEAVE);
				con.setAutoCommit(false);

				st.setString(1, empLeave.getEmpId());
				st.setInt(2, empLeave.getLeaveDuration());
				st.setDate(3, empLeave.getFromDate());
				st.setDate(4, empLeave.getToDate());
				st.setString(5, empLeave.getStatus());

				int count = st.executeUpdate();
				if (count > 0) {
					con.commit();
					result = true;
					logger.info("Committed successfully");
				}

			} catch (SQLException e) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					logger.error("Database error: Unable to rollback");
					throw new EMSException(Messages.UNABLE_TO_ROLLBACK);
				}
				logger.error("Database error: Unable to complete operation");
				throw new EMSException(Messages.UNABLE_TO_COMPLETE_OPERATION);
			} finally {

				try {
					st.close();
					con.close();
				} catch (Exception e) {
					logger.error("Database error: Unable to close connection");
					throw new EMSException(Messages.CONNECTION_NOT_CLOSED);
				}
			}
		}

		return result;
	}
	
	//------------------------Employee Management System --------------------------
		/*******************************************************************************************************
			- Function Name		:	approveLeave
			- Input Parameters	:	int leaveId
			- Return Type		:	boolean
			- Throws			:  	EMSException
			- Author			:	
			- Creation Date		:	12/10/2018
			- Description		:	approves leave taken by the employee
		********************************************************************************************************/	
	

	@Override
	public boolean approveLeave(int leaveId) throws EMSException {
		boolean result = false;
		PreparedStatement st1 = null;
		PreparedStatement st2 = null;
		PreparedStatement st3 = null;
		Connection con = null;
		EmployeeLeave empLeave = getEmpLeaveById(leaveId);
		if (empLeave != null) {
			try {
				con = ConnectionProvider.getConnection();
				st1 = con.prepareStatement(IQueryMapper.GET_BALANCE);
				st2 = con.prepareStatement(IQueryMapper.UPDATE_BALANCE);
				st3 = con.prepareStatement(IQueryMapper.APPROVE_LEAVE);

				con.setAutoCommit(false);
				st1.setString(1, empLeave.getEmpId());
				ResultSet rs = st1.executeQuery();
				rs.next();
				int leaveBal = rs.getInt(1);
				leaveBal -= empLeave.getLeaveDuration();
				if (leaveBal > 0) {
					st2.setInt(1, leaveBal);
					st2.setString(2, empLeave.getEmpId());
					int count1 = st2.executeUpdate();
					st3.setString(1, "Approved");
					st3.setInt(2, empLeave.getLeaveId());
					int count2 = st3.executeUpdate();
					if (count1 > 0 && count2 > 0) {
						con.commit();
						result = true;
						logger.info("Commit succesfull");
					}
				} else {
					logger.error("Not enough leave balance");
					throw new EMSException(Messages.NOT_ENOUGH_LEAVE_BALANCE);
				}
			} catch (SQLException e) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					logger.error("Database error: Unable to rollback");
					throw new EMSException(Messages.UNABLE_TO_ROLLBACK);
				}
				logger.error("Database error: Unable to complete operation");
				throw new EMSException(Messages.UNABLE_TO_COMPLETE_OPERATION);
			} finally {

				try {
					st1.close();
					st2.close();
					st3.close();
					con.close();
				} catch (Exception e) {
					logger.error("Database error: Unable to close connection");
					throw new EMSException(Messages.CONNECTION_NOT_CLOSED);
				}
			}
		}
		return result;
	}
	
	//------------------------Employee Management System --------------------------
		/*******************************************************************************************************
			- Function Name		:	getEmpLeaveById
			- Input Parameters	:	int leaveId
			- Return Type		:	EmployeeLeave object
			- Throws			:  	EMSException
			- Author			:	
			- Creation Date		:	12/10/2018
			- Description		:	employee leave details fetched after submitting id
		********************************************************************************************************/	
	public EmployeeLeave getEmpLeaveById(int leaveId) throws EMSException {
		EmployeeLeave empLeave = null;
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = ConnectionProvider.getConnection();
			stmt = con.prepareStatement(IQueryMapper.FIND_EMP_LEAVE_BY_ID);
			con.setAutoCommit(false);
			stmt.setInt(1, leaveId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				empLeave = new EmployeeLeave();
				empLeave.setLeaveId(rs.getInt("Leave_Id"));
				empLeave.setEmpId(rs.getString("Emp_id"));
				empLeave.setLeaveDuration(rs.getInt("noofdays_applied"));
				empLeave.setFromDate(rs.getDate("date_from"));
				empLeave.setToDate(rs.getDate("date_to"));
				empLeave.setStatus(rs.getString("status"));
				empLeave.setAppliedDate(rs.getDate("date_applied"));
			}

		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				logger.error("Database error: Unable to Rollback");
				throw new EMSException(Messages.UNABLE_TO_ROLLBACK);
			}
			logger.error("Database error: Unable to fetch record");
			throw new EMSException(Messages.NOT_FETCHED);
		}

		return empLeave;
	}

	@Override
	public boolean rejectLeave(int leaveId) throws EMSException {
		boolean result = false;
		Connection con = null;
		PreparedStatement st1 = null;
		try {
			con = ConnectionProvider.getConnection();
			st1 = con.prepareStatement(IQueryMapper.REJECT_LEAVE);

			con.setAutoCommit(false);
			st1.setString(1, "Rejected");
			st1.setInt(2, leaveId);
			int count3 = st1.executeUpdate();
			if (count3 > 0) {
				con.commit();
				result = true;
			}
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				logger.error("Database error: Unable to Rollback");
				throw new EMSException(Messages.UNABLE_TO_ROLLBACK);
			}
			logger.error("Database error: Unable to complete operation");
			throw new EMSException(Messages.UNABLE_TO_COMPLETE_OPERATION);

		} finally {

			try {
				st1.close();
				con.close();
			} catch (Exception e) {
				logger.error("Database error: Unable to close connection");
				throw new EMSException(Messages.CONNECTION_NOT_CLOSED);
			}
		}
		return result;
	}
	//------------------------Employee Management System --------------------------
			/*******************************************************************************************************
				- Function Name		:	getAllApliedLeaves
				- Input Parameters	:	String managerID
				- Return Type		:	List of EmployeeLeave object
				- Throws			:  	EMSException
				- Author			:	
				- Creation Date		:	12/10/2018
				- Description		:	list of all employee leave details fetched after submitting manager id 
			********************************************************************************************************/	
	@Override
	public List<EmployeeLeave> getAllAppliedLeaves(String mgrId) throws EMSException {
		List<EmployeeLeave> empLeaveList = new ArrayList<EmployeeLeave>();
		Connection con = null;
		PreparedStatement st1 = null;
		try {
			con = ConnectionProvider.getConnection();
			st1 = con.prepareStatement(IQueryMapper.GET_ALL_LEAVE);
			con.setAutoCommit(false);
			st1.setString(1, "Applied");
			st1.setString(2, mgrId);
			ResultSet rs = st1.executeQuery();

			while (rs.next()) {
				EmployeeLeave empLeave = new EmployeeLeave();
				empLeave.setLeaveId(rs.getInt("Leave_Id"));
				empLeave.setEmpId(rs.getString("Emp_id"));
				empLeave.setLeaveDuration(rs.getInt("noofdays_applied"));
				empLeave.setFromDate(rs.getDate("date_from"));
				empLeave.setToDate(rs.getDate("date_to"));
				empLeave.setStatus(rs.getString("status"));
				empLeave.setAppliedDate(rs.getDate("date_applied"));
				empLeaveList.add(empLeave);
			}
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				logger.error("Database error: Unable to Rollback");
				throw new EMSException(Messages.UNABLE_TO_ROLLBACK);
			}
			logger.error("Database error: Unable to complete operation");
			throw new EMSException(Messages.UNABLE_TO_COMPLETE_OPERATION);
		} finally {

			try {
				st1.close();
				con.close();
			} catch (Exception e) {
				logger.error("Database error: Unable to close connection");
				throw new EMSException(Messages.CONNECTION_NOT_CLOSED);
			}
		}

		return empLeaveList;
	}

}
