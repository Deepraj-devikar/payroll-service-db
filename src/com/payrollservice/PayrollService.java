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
			ArrayList<String[]> wheres = new ArrayList<String[]>();
			String id = payrollService.getEmployeeIdByName(connection, "Terissa");
			ArrayList<String[]> data = new ArrayList<String[]>();
			String[] info = {"basic_pay", "3000000.00"};
			data.add(info);
			String[] info2 = {"salary", "3500000.00"};
			data.add(info2);
			payrollService.updateEmployeePayroll(connection, id, data);
			ArrayList<EmployeePayroll> employeePayrollList = payrollService.readEmployeePayrolls(connection);
			System.out.println(employeePayrollList);
		}
	}
	
	public String getEmployeeIdByName(Connection connection, String employeeName) {
		String employeePayrollSQL = "SELECT id FROM employee WHERE employee_name = '"+employeeName+"'";
		try {
			PreparedStatement preparedStatementForEmployeePayroll = (PreparedStatement) connection.prepareStatement(employeePayrollSQL);
			boolean isPreparedStatementExecuted = preparedStatementForEmployeePayroll.execute();
			System.out.println("Prepered ststement "+(isPreparedStatementExecuted ? "" : "not " )+"executed successfully. and employee payroll data retrived by id.");
			ResultSet resultSetEmployeePayroll = preparedStatementForEmployeePayroll.getResultSet();
			while(resultSetEmployeePayroll.next()) {
				return String.valueOf(resultSetEmployeePayroll.getInt("id"));
			}
		} catch(Exception exception) {
			System.out.println("problem in get employee id by name prepared statement. Exception is - "+exception);
		}
		return "";
	}

	public void updateEmployeePayroll(Connection connection, String id, ArrayList<String[]> data) {
		String updateEmployeePayrollSQL = "UPDATE employee_payroll SET";
		for(int i = 0; i < data.size(); i++) {
			String[] info = data.get(i);
			updateEmployeePayrollSQL += (i != 0 ? "," : "")+" "+info[0]+" = '"+info[1]+"'";
		}
		updateEmployeePayrollSQL += " WHERE id = '"+id+"'";
		try {
			PreparedStatement preparedStatementForUpdateEmployeePayroll = (PreparedStatement) connection.prepareStatement(updateEmployeePayrollSQL);
			preparedStatementForUpdateEmployeePayroll.execute();
		} catch(Exception exception) {
			System.out.println("problem in update employee payroll prepared statement");
		}
	}

	public ArrayList<EmployeePayroll> readEmployeePayrolls(Connection connection) {
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
			System.out.println("problem in read employee payrolls prepared statement");
		}
		return employeePayrollList;
	}

	public void listDrivers() {
		Enumeration<Driver> driverList = DriverManager.getDrivers();
		while(driverList.hasMoreElements()) {
			Driver driverClass = (Driver) driverList.nextElement();
			System.out.println("  "+driverClass.getClass().getName());
		}
	}

	public boolean isDriverLoaded() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver loaded");
			return true;
		} catch(Exception exception) {
			System.out.println("Driver is not loaded");
			return false;
		}
	}

	public Connection getConnection() {
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
