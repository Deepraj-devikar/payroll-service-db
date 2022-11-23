package com.payrollservice;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;

public class PayrollService {

	public static void main(String[] args) {
		PayrollService payrollService = new PayrollService();
		if(payrollService.isDriverLoaded()) {
			payrollService.listDrivers();
			Connection connection = payrollService.getConnection();	
			ArrayList<EmployeePayroll> employeePayrollList = payrollService.readEmployeePayrolls(connection);
			System.out.println(employeePayrollList);
		}
	}

	private ArrayList<EmployeePayroll> readEmployeePayrolls(Connection connection) {
		String employeePayrollSQL = "SELECT e_p.id AS employee_payroll_id, e.id AS employee_id,  e.employee_name, e.gender, "
				+ "e_p.salary, e_p.basic_pay, e_p.taxable_pay, e_p.income_tax, e_p.net_pay, e.start_date "
				+ "FROM employee_payroll AS e_p "
				+ "LEFT JOIN employee AS e ON e_p.employee_id = e.id";
		ArrayList<EmployeePayroll> employeePayrollList = new ArrayList<EmployeePayroll>();
		try {
			PreparedStatement preparedStatementForEmployeePayroll = (PreparedStatement) connection.prepareStatement(employeePayrollSQL);
			boolean isPreparedStatementExecuted = preparedStatementForEmployeePayroll.execute();
			System.out.println("Prepered ststement "+(isPreparedStatementExecuted ? "" : "not " )+"executed successfully. and employee payroll data retrived.");
			ResultSet resultSetEmployeePayroll = preparedStatementForEmployeePayroll.getResultSet();
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
				String deductionsSQL = "SELECT * FROM deduction WHERE employee_payroll_id = "+tempEmployeePayroll.getId();
				PreparedStatement preparedStatementForDeduction = (PreparedStatement) connection.prepareStatement(deductionsSQL);
				preparedStatementForDeduction.execute();
				ResultSet resultSetDeduction = preparedStatementForDeduction.getResultSet();
				while(resultSetDeduction.next()) {
					tempEmployeePayroll.addDeduction(
							resultSetDeduction.findColumn("id"), 
							resultSetDeduction.getString("deduction_name"), 
							resultSetDeduction.getFloat("deduction_amount"));
				}
				employeePayrollList.add(tempEmployeePayroll);
			}
		} catch (SQLException e) {
			System.out.println("problem in prepared statement");
		}
		return employeePayrollList;
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
