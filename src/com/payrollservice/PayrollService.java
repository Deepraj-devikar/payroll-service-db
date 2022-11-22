package com.payrollservice;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

public class PayrollService {

	public static void main(String[] args) {
		PayrollService payrollService = new PayrollService();
		if(payrollService.isDriverLoaded()) {
			payrollService.listDrivers();
			Connection connection = payrollService.getConnection();	
		}
	}

	private void listDrivers() {
		Enumeration<Driver> driverList = DriverManager.getDrivers();
		while(driverList.hasMoreElements()) {
			Driver driverClass = (Driver) driverList.nextElement();
			System.out.println("  "+driverClass.getClass().getName());
		}
	}

	private boolean isDriverLoaded() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver loaded");
			return true;
		} catch(Exception exception) {
			System.out.println("Driver is not loaded");
			return false;
		}
	}

	private Connection getConnection() {
		String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service";
		String userName = "root";
		String password = "root";
		Connection connection = null;
		try {
			System.out.println("Connection to database: "+jdbcURL);
			connection = DriverManager.getConnection(jdbcURL, userName, password);
			System.out.println("Connection is successfull!!!!!!!! "+connection);
		} catch (Exception exception) {
			System.out.println(exception);
		}
		return connection;
	}
}
