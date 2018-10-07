package com.cg.ems.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cg.ems.bean.Employee;
import com.cg.ems.exception.EMSException;
import com.cg.ems.util.ConnectionProvider;

public class AdminDaoImpl implements IAdminDao {

	@Override
	public boolean addEmployee(Employee employee) throws EMSException {
		boolean success = false;
		
		if (employee != null) {
			try (Connection con = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
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
				st.setLong(10, employee.getEmpGender());
				st.setString(11, employee.getEmpMarital());
				st.setString(12, employee.getEmpAddress());
				st.setString(13, employee.getEmpContact());
				st.setString(14, employee.getMgrId());

				int count = st.executeUpdate();

				if (count > 0)
					success = true;

			} catch (SQLException e) {

				throw new EMSException("Unable To Save Employee");
			}
		}
		return success;
	}

	@Override
	public boolean updateEmployee(Employee employee) throws EMSException {
		boolean success = false;
		
		try (Connection con = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
				PreparedStatement st = con.prepareStatement(IQueryMapper.UPDATE_EMPLOYEE);) {
			st.setString(14, employee.getEmpId());

			st.setString(1, employee.getEmpFName());
			st.setString(2, employee.getEmpLName());
			st.setDate(3, employee.getEmpDOB());
			st.setDate(4, employee.getEmpDOJ());
			st.setInt(5, employee.getEmpDeptId());
			st.setString(6, employee.getEmpGrade());
			st.setString(7, employee.getEmpDesignation());
			st.setInt(8, employee.getEmpBasic());
			st.setLong(9, employee.getEmpGender());
			st.setString(10, employee.getEmpMarital());
			st.setString(11, employee.getEmpAddress());
			st.setString(12, employee.getEmpContact());
			st.setString(13, employee.getMgrId());
			int update = st.executeUpdate();
			if(update > 0)
				success = true;
		} catch (SQLException e) {
			throw new EMSException("Unable To Update Employee");
		}
		return success;
	}

	@Override
	public List<Employee> getAllEmployee() throws EMSException {

		List<Employee> empList = null;
		try (Connection con = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
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
				emp.setEmpGender((char) rs.getLong(10));
				emp.setEmpMarital(rs.getString(11));
				emp.setEmpAddress(rs.getString(12));
				emp.setEmpContact(rs.getString(13));
				emp.setMgrId(rs.getString(14));

				empList.add(emp);
			}

		} catch (SQLException e) {

			throw new EMSException("Unable To Fetch Employee");
		}
		return empList;
	}

	@Override
	public Employee getEmployeeById(String empId) throws EMSException {
		Employee emp = null;
		try (Connection con = ConnectionProvider.DEFAULT_INSTANCE.getConnection();
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
				emp.setEmpGender((char) rs.getLong(10));
				emp.setEmpMarital(rs.getString(11));
				emp.setEmpAddress(rs.getString(12));
				emp.setEmpContact(rs.getString(13));
				emp.setMgrId(rs.getString(14));
			}

		} catch (SQLException e) {
			throw new EMSException("Unable To Fetch Employee");
		}
		return emp;

	}

}
