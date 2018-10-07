package com.cg.ems.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;



import org.apache.log4j.Logger;

import com.cg.ems.exception.EMSException;


public enum ConnectionProvider {

	DEFAULT_INSTANCE;

	private String driver;
	private String unm;
	private String pwd;
	private String url;

	private Logger log = Logger.getLogger("DB");

	private ConnectionProvider() {
		try {
			Properties props = new Properties();
			props.load(new FileInputStream("res/db.properties"));
			unm = props.getProperty("db.unm");
			driver = props.getProperty("db.driver");
			pwd = props.getProperty("db.pwd");
			url = props.getProperty("db.url");
			Class.forName(driver);
			log.info("Driver Loaded");
		} catch (ClassNotFoundException | IOException e) {
			log.error(e);
		}
	}

	public Connection getConnection() throws EMSException {
		Connection con = null;

		try {
			if (url != null && unm != null && pwd != null) {
				con = DriverManager.getConnection(url, unm, pwd);
			}else
				throw new EMSException("Connection Configuaration Not Loaded!");
		} catch (SQLException e) {
			log.error(e);
			throw new EMSException("Connection is not established!");
		}
		return con;
	}
}
