package com.cg.ems.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.cg.ems.bean.EmployeeLeave;
import com.cg.ems.exception.EMSException;
import com.cg.ems.util.ConnectionProvider;
import com.cg.ems.util.Messages;

public class LeaveApplicationDaoImpl implements ILeaveApplicationDao {

	private Logger log = Logger.getLogger("RegisterEntryDAO");

	@Override
	public boolean applyLeave(EmployeeLeave empLeave) throws EMSException {

		boolean result = false;
		PreparedStatement st = null;
		Connection con = null;
		if (empLeave != null) {

			try {
				con = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
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
				}

			} catch (SQLException e) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					throw new EMSException(Messages.UNABLE_TO_ROLLBACK);
				}
				log.error(e);
				throw new EMSException(Messages.UNABLE_TO_COMPLETE_OPERATION);
			} finally {

				try {
					st.close();
					con.close();
				} catch (Exception e) { 
					throw new EMSException(Messages.CONNECTION_NOT_CLOSED);
				}
			}
		}

		return result;
	}

	@Override
	public boolean approveLeave(EmployeeLeave empLeave) throws EMSException {
		boolean result = false;
		PreparedStatement st1 = null;
		PreparedStatement st2 = null;
		PreparedStatement st3 = null;
		Connection con = null;
		if (empLeave != null) {
				try {
					 con = ConnectionProvider.DEFAULT_INSTANCE
						.getConnection(); 
				 st1 = con
						.prepareStatement(IQueryMapper.GET_BALANCE);
						st2 = con
								.prepareStatement(IQueryMapper.UPDATE_BALANCE);
						 st3 = con
								.prepareStatement(IQueryMapper.APPROVE_LEAVE);

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
						}
					}else {
						throw new EMSException(
								Messages.NOT_ENOUGH_LEAVE_BALANCE);
					}
				} catch (SQLException e) {
					try {
						con.rollback();
					} catch (SQLException e1) {
						throw new EMSException(Messages.UNABLE_TO_ROLLBACK);
					}
					log.error(e);
					throw new EMSException(
							Messages.UNABLE_TO_COMPLETE_OPERATION);
				}
				finally {

					try {
						st1.close();
						st2.close();
						st3.close();
						con.close();
					} catch (Exception e) { 
						throw new EMSException(Messages.CONNECTION_NOT_CLOSED);
					}
				}
			}
		return result;
	}

	@Override
	public boolean rejectLeave(int leaveId) throws EMSException {
		boolean result = false;
		Connection con = null;
		PreparedStatement st1=null;
			try  {
				 con = ConnectionProvider.DEFAULT_INSTANCE
					.getConnection(); 
				  st1 = con
					.prepareStatement(IQueryMapper.REJECT_LEAVE);

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
					throw new EMSException(Messages.UNABLE_TO_ROLLBACK);
				}
				log.error(e);
				throw new EMSException(Messages.UNABLE_TO_COMPLETE_OPERATION);

			}
			finally {

				try {
					st1.close();
					con.close();
				} catch (Exception e) { 
					throw new EMSException(Messages.CONNECTION_NOT_CLOSED);
				}
			}
		return result;
	}

	@Override
	public List<EmployeeLeave> getAllAppliedLeaves() throws EMSException {
		List<EmployeeLeave> empList = new ArrayList<EmployeeLeave>();
		Connection con = null;
		PreparedStatement st1=null;
			try { con = ConnectionProvider.DEFAULT_INSTANCE
					.getConnection();
				 st1 = con.prepareStatement(IQueryMapper.GET_ALL_LEAVE); 
				con.setAutoCommit(false);
				st1.setString(1, "Applied");
				ResultSet rs = st1.executeQuery();

				while (rs.next()) {
					EmployeeLeave emp = new EmployeeLeave();
					emp.setLeaveId(rs.getInt("Leave_Id"));
					emp.setEmpId(rs.getString("Emp_id"));
					emp.setLeaveDuration(rs.getInt("noofdays_applied"));
					emp.setFromDate(rs.getDate("date_from"));
					emp.setToDate(rs.getDate("date_to"));
					emp.setStatus(rs.getString("status"));
					emp.setAppliedDate(rs.getDate("date_applied"));
					empList.add(emp);
				}
				if (empList.size() == 0)
					empList = null;
			} catch (SQLException e) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					throw new EMSException(Messages.UNABLE_TO_ROLLBACK);
				}
				log.error(e);
				throw new EMSException(Messages.UNABLE_TO_COMPLETE_OPERATION);
			}
			finally {

				try {
					st1.close();
					con.close();
				} catch (Exception e) { 
					throw new EMSException(Messages.CONNECTION_NOT_CLOSED);
				}
			}

		return empList;
	}

}
