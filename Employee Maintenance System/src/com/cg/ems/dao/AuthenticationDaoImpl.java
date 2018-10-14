package com.cg.ems.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cg.ems.bean.User;
import com.cg.ems.exception.EMSException;
import com.cg.ems.util.ConnectionProvider;
import com.cg.ems.util.Messages;

public class AuthenticationDaoImpl implements IAuthenticationDao {

	@Override
	public User getUser(String userName, String userPassword) throws EMSException {
		User user = null;
		Connection con = null;
		PreparedStatement st = null;
		try {
				con = ConnectionProvider.getConnection();
				st = con.prepareStatement(IQueryMapper.GET_USER);
			
			st.setString(1, userName);
			st.setString(2, userPassword);
			
			ResultSet rs = st.executeQuery();
			if(rs.next()){
				user = new User();
				user.setUserId(rs.getString(1));
				user.setUserName(rs.getString(2));
				user.setUserPassword(rs.getString(3));
				user.setUserType(rs.getString(4));
				user.setEmpId(rs.getString(5));
				//System.out.println(user);
			}
		} catch (SQLException e) {
			
			throw new EMSException("Unable To Fetch Records");
		} finally {

			try {
				st.close();
				con.close();
			} catch (SQLException e) {

				throw new EMSException(Messages.CONNECTION_NOT_CLOSED);
			}

		}
		return user;
	}
}
