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

import org.apache.log4j.Logger;

import com.cg.ems.bean.EmployeeLeave;
import com.cg.ems.exception.EMSException;
import com.cg.ems.util.ConnectionProvider;
import com.cg.ems.util.Messages;

public class AutoApprovalDaoImpl implements IAutoApprovalDao {

	private Logger log = Logger.getLogger("RegisterEntryDAO");

	@Override
	public List<EmployeeLeave> autoApprove() throws EMSException {
		List<EmployeeLeave> leaveList = new ArrayList<EmployeeLeave>();
		PreparedStatement st1 = null;
		PreparedStatement st2 = null;
		PreparedStatement st3 = null;
		PreparedStatement st4 = null;
		Connection con = null;
		con = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
		try {
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
				st1 = con.prepareStatement(IQueryMapper.GET_BALANCE);
				st2 = con.prepareStatement(IQueryMapper.UPDATE_BALANCE);
				st3 = con.prepareStatement(IQueryMapper.APPROVE_LEAVE);

				con.setAutoCommit(false);
				st1.setString(1, empLeave.getEmpId());
				ResultSet resultSet = st1.executeQuery();
				rs.next();
				int leaveBal = resultSet.getInt(1);
				leaveBal -= empLeave.getLeaveDuration();
				if (leaveBal > 0) {
					Date appliedDate = empLeave.getAppliedDate();
					Calendar c = Calendar.getInstance();
					c.setTime(appliedDate);

					c.add(Calendar.DATE, 3);
					Date after3days = (Date) c.getTime();
					Date currDate = Date.valueOf(LocalDate.now());
					if (currDate.compareTo(after3days) > 0) {
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
					}
					leaveList.add(empLeave);
				} else {
					throw new EMSException(Messages.NOT_ENOUGH_LEAVE_BALANCE);
				}
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
				st1.close();
				st2.close();
				st3.close();
				st4.close();
				con.close();
			} catch (Exception e) {
				throw new EMSException(Messages.CONNECTION_NOT_CLOSED);
			}

		}

		return leaveList;
	}

}
