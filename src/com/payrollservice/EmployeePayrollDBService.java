package com.payrollservice;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Enumeration;

public class EmployeePayrollDBService {
	private static EmployeePayrollDBService employeePayrollDBService;
	private boolean isDriverLoaded = false;
	private PreparedStatement employeePayrollsDataStatement;
	private PreparedStatement employeeIdByNameDataStatement;
	
	private EmployeePayrollDBService() {
	}
	
	public static EmployeePayrollDBService getInstance() {
		if(employeePayrollDBService == null) {
			employeePayrollDBService = new EmployeePayrollDBService();
		}
		return employeePayrollDBService;
	}
	
	private void loadDriver() {
		Enumeration<Driver> driverList = DriverManager.getDrivers();
		while(driverList.hasMoreElements() && !isDriverLoaded) {
			Driver driverClass = (Driver) driverList.nextElement();
			loadDriver(driverClass.getClass().getName());
		}
	}
	
	private void loadDriver(String driverName) {
		try {
			Class.forName(driverName);
			isDriverLoaded = true;
			System.out.println("Driver loaded");
		} catch(Exception exception) {
			System.out.println("Driver is not loaded");
		}
	}
	
	private Connection getConnection() {
		String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service";
		String userName = "root";
		String password = "root";
		Connection connection = null;
		try {
			if(!isDriverLoaded) {
				loadDriver();
			}
			System.out.println("Connection to database: "+jdbcURL);
			connection = DriverManager.getConnection(jdbcURL, userName, password);
			System.out.println("Connection is successfull!!!!!!!! "+connection);
		} catch (Exception exception) {
			System.out.println(exception);
		}
		return connection;
	}
	
	private void prepareStatementForEmployeePayrolls() {
		try {
			Connection connection = getConnection();
			String sql = "SELECT e_p.id AS employee_payroll_id, e.id AS employee_id,  e.employee_name, e.gender, "
					+ "e_p.salary, e_p.basic_pay, e_p.taxable_pay, e_p.income_tax, e_p.net_pay, e.start_date "
					+ "FROM employee_payroll AS e_p "
					+ "LEFT JOIN employee AS e ON e_p.employee_id = e.id";
			employeePayrollsDataStatement = connection.prepareStatement(sql);
		} catch(Exception exception) {
			exception.printStackTrace();
		}
	}
	
	private void prepareStatementForEmployeeIdByName() {
		try {
			Connection connection = getConnection();
			String sql = "SELECT id FROM employee WHERE employee_name = ?";
			employeeIdByNameDataStatement = connection.prepareStatement(sql);
		} catch(Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public ArrayList<EmployeePayroll> readEmployeePayrolls() {
		ArrayList<EmployeePayroll> employeePayrollList = new ArrayList<EmployeePayroll>();
		if(employeePayrollsDataStatement == null) {
			prepareStatementForEmployeePayrolls();
		}
		try {
			boolean isPreparedStatementExecuted = employeePayrollsDataStatement.execute();
			System.out.println("Prepered ststement "+(isPreparedStatementExecuted ? "" : "not " )+"executed successfully. and employee payroll data retrived.");
			ResultSet resultSetEmployeePayroll = employeePayrollsDataStatement.getResultSet();
			while(resultSetEmployeePayroll.next()) {
				EmployeePayroll tempEmployeePayroll = new EmployeePayroll(resultSetEmployeePayroll.getInt("employee_payroll_id"));
				tempEmployeePayroll.setEmployeeId(resultSetEmployeePayroll.getInt("employee_id"));
				tempEmployeePayroll.setEmployeeName(resultSetEmployeePayroll.getNString("employee_name"));
				tempEmployeePayroll.setEmployeeGender(resultSetEmployeePayroll.getString("gender").charAt(0));
				tempEmployeePayroll.setStartDate(resultSetEmployeePayroll.getString("start_date"));
				tempEmployeePayroll.setSalary(resultSetEmployeePayroll.getFloat("salary"));
				tempEmployeePayroll.setBasicPay(resultSetEmployeePayroll.getFloat("basic_pay"));
				tempEmployeePayroll.setTaxablePay(resultSetEmployeePayroll.getFloat("taxable_pay"));
				tempEmployeePayroll.setIncomeTax(resultSetEmployeePayroll.getFloat("income_tax"));
				tempEmployeePayroll.setNetPay(resultSetEmployeePayroll.getFloat("net_pay"));
				employeePayrollList.add(tempEmployeePayroll);
			}
		} catch(Exception exception) {
			exception.printStackTrace();
		}
		return employeePayrollList;
	}
	
	public String getEmployeeIdByName(String employeeName) {
		if(employeeIdByNameDataStatement == null) {
			prepareStatementForEmployeeIdByName();	
		}
		try {
			employeeIdByNameDataStatement.setString(1, employeeName);
			ResultSet resultSetEmployeePayroll = employeeIdByNameDataStatement.executeQuery();
			while(resultSetEmployeePayroll.next()) {
				return String.valueOf(resultSetEmployeePayroll.getInt("id"));
			}
		} catch(Exception exception) {
			System.out.println("problem in get employee id by name prepared statement. Exception is - "+exception);
		}
		return "";
	}
	
	public void updateEmployeePayroll(String id, ArrayList<String[]> data) {
		String updateEmployeePayrollSQL = "UPDATE employee_payroll SET";
		for(int i = 0; i < data.size(); i++) {
			String[] info = data.get(i);
			updateEmployeePayrollSQL += String.format("%s %s = '%s'", (i != 0 ? "," : ""), info[0], info[1]);
		}
		updateEmployeePayrollSQL += String.format(" WHERE id = '%s'", id);
		try (Connection connection = getConnection()){
			PreparedStatement preparedStatementForUpdateEmployeePayroll = (PreparedStatement) connection.prepareStatement(updateEmployeePayrollSQL);
			preparedStatementForUpdateEmployeePayroll.execute();
			System.out.println("employee payroll data updated successfully!!!");
		} catch(Exception exception) {
			System.out.println("problem in update employee payroll prepared statement");
		}
	}
}
