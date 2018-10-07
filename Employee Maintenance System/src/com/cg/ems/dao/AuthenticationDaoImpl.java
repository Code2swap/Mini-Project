package com.cg.ems.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cg.ems.bean.User;
import com.cg.ems.exception.EMSException;
import com.cg.ems.util.ConnectionProvider;

public class AuthenticationDaoImpl implements IAuthenticationDao {

	@Override
	public User getUser(String userName, String userPassword) throws EMSException {
		User user = null;
		try(
				Connection con = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
				PreparedStatement st = con.prepareStatement(IQueryMapper.GET_USER)
			){
			
			st.setString(1, userName);
			st.setString(2, userPassword);
			
			ResultSet rs = st.executeQuery();
			
			if(rs.next()){
				user = new User();
				user.setUserName(rs.getString(1));
				user.setUserPassword(rs.getString(2));
				user.setUserType(rs.getString(3));
				user.setEmpId(rs.getString(4));
			}
		} catch (SQLException e) {
			
			throw new EMSException("Unable To Fetch Records");
		}
		return user;
	}
}
