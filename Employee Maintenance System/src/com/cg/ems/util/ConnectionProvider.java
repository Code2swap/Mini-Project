package com.cg.ems.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;





import org.apache.log4j.Logger;

import com.cg.ems.exception.EMSException;


public class ConnectionProvider {

	public static ConnectionProvider defaultInstance = null;

	private static String driver;
	private static String user;
	private static String pwd;
	private static String url;

	private static Logger log = Logger.getLogger("DB Connection");

	// prepare everything to get db connection
	private ConnectionProvider() throws EMSException {
		try {
			Properties props = new Properties();
			props.load(new FileInputStream("resources/db.properties"));
			user = props.getProperty("db.user");
			driver = props.getProperty("db.driver");
			pwd = props.getProperty("db.password");
			url = props.getProperty("db.url");
			Class.forName(driver);
			log.info("Driver Loaded");
		} catch (ClassNotFoundException e) {
			log.error("Driver class not found");
			throw new EMSException(Messages.DRIVER_CLASS_NOT_FOUND);
		} catch (FileNotFoundException e) {
			log.error("The property file does not exist");
			throw new EMSException(Messages.FILE_NOT_FOUND);
		} catch (IOException e) {
			log.error("Can not read file");
			throw new EMSException(Messages.INPUT_OUTPUT_OPERATION_FAILED);
		}
	}
	// get db connection
	public static Connection getConnection() throws EMSException {
		Connection con = null;
		// making it singleton instance
		if(defaultInstance == null)
			defaultInstance = new ConnectionProvider();

		try {
			if (url != null && user != null && pwd != null) {
				log.info("Property file read successfully");
				con = DriverManager.getConnection(url, user, pwd);
			} else {
				log.error("property file is not configured");
				throw new EMSException(Messages.CONNECTION_NOT_CONFIGURED);
			}
		} catch (SQLException e) {
			log.error("Can not establish connection");
			throw new EMSException(Messages.CONNECTION_NOT_ESTABLISHED);
		}
		if(con != null) {
			log.info("Connection successful");
		} else {
			log.error("Connection is null, not established");
		}
		return con;
	}
}
