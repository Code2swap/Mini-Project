package com.cg.ems.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.cg.ems.bean.EmployeeLeave;
import com.cg.ems.exception.EMSException;
import com.cg.ems.util.ConnectionProvider;
import com.cg.ems.util.Messages;

public class AutoApprovalDaoImpl implements IAutoApprovalDao {

	static Logger logger = Logger.getLogger("applog");
	
	public AutoApprovalDaoImpl() {
		
		PropertyConfigurator.configure("resources//log4j.properties");
	}
	
	
	//------------------------Employee Management System --------------------------
			/*******************************************************************************************************
				- Function Name		:	autoApprove
				- Input Parameters	:	None
				- Return Type		:	List of Employee leaves
				- Throws			:  	EMSException
				- Author			:	
				- Creation Date		:	12/10/2018
				- Description		:	fetching list of employee leaves
			********************************************************************************************************/	
	

	@Override
	public List<EmployeeLeave> autoApprove() throws EMSException {
		List<EmployeeLeave> leaveList = new ArrayList<EmployeeLeave>();
		PreparedStatement st1 = null;
		PreparedStatement st2 = null;
		PreparedStatement st3 = null;
		PreparedStatement st4 = null;
		Connection con = null;
		con = ConnectionProvider.getConnection();
		try {
			st1 = con.prepareStatement(IQueryMapper.GET_BALANCE);
			st2 = con.prepareStatement(IQueryMapper.UPDATE_BALANCE);
			st3 = con.prepareStatement(IQueryMapper.APPROVE_LEAVE);
			st4 = con.prepareStatement(IQueryMapper.GET_APPLIED_LEAVE);
			st4.setString(1, "Applied");
			ResultSet rs = st4.executeQuery();
			while (rs.next()) {
				EmployeeLeave empLeave = new EmployeeLeave();
				empLeave.setLeaveId(rs.getInt("Leave_Id"));
				empLeave.setEmpId(rs.getString("Emp_id"));
				empLeave.setLeaveDuration(rs.getInt("noofdays_applied"));
				empLeave.setFromDate(rs.getDate("date_from"));
				empLeave.setToDate(rs.getDate("date_to"));
				empLeave.setStatus(rs.getString("status"));
				empLeave.setAppliedDate(rs.getDate("date_applied"));
				con.setAutoCommit(false);
				
				st1.setString(1, empLeave.getEmpId());
				ResultSet resultSet = st1.executeQuery();
				resultSet.next();
				
				int leaveBal = resultSet.getInt(1);
				leaveBal -= empLeave.getLeaveDuration();
				
				if (leaveBal > 0) {
					int diffDays = 1 + (int )TimeUnit.MILLISECONDS.toDays(Date.valueOf(LocalDate.now()).getTime() 
							- empLeave.getAppliedDate().getTime());
					if (diffDays > 3) {
						empLeave.setStatus("Approved");

						st2.setInt(1, leaveBal);
						st2.setString(2, empLeave.getEmpId());
						int count1 = st2.executeUpdate();
						st3.setString(1, "Approved");
						st3.setInt(2, empLeave.getLeaveId());
						int count2 = st3.executeUpdate();
						if (count1 > 0 && count2 > 0) {
							con.commit();
						}
						leaveList.add(empLeave);
					}
				} else {
					logger.error("Not enough leaves for the employee");
					throw new EMSException(Messages.NOT_ENOUGH_LEAVE_BALANCE);
				}
			}
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				logger.error("Database error: Unable to Rollback");
				throw new EMSException(Messages.UNABLE_TO_ROLLBACK);
			}
			logger.error("Database error:Unable to complete operation at this time");
			throw new EMSException(Messages.UNABLE_TO_COMPLETE_OPERATION);
		} finally {

			try {
				st1.close();
				st2.close();
				st3.close();
				st4.close();
				con.close();
			} catch (Exception e) {
				logger.error("Database error: Connection could not be closed");
				throw new EMSException(Messages.CONNECTION_NOT_CLOSED);
			}

		}

		return leaveList;
	}

}


