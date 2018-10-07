package com.cg.ems.dao;

import java.util.List;

import com.cg.ems.bean.EmployeeLeave;
import com.cg.ems.exception.EMSException;

public interface IAutoApprovalDao {

	List<EmployeeLeave> autoApprove() throws EMSException;

}
