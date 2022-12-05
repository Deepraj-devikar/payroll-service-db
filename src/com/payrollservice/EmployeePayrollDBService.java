package com.payrollservice;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class EmployeePayrollDBService {
	private static EmployeePayrollDBService employeePayrollDBService;
	private boolean isDriverLoaded = false;
	private Hashtable<String, PreparedStatement> preparedStatements;
	private enum PreparedStatementOptions{
		ALL_EMPLOYEE_PAYROLL_DATA, 
		EMPLOYEE_PAYROLL_ID_BY_EMPLOYEE_NAME, 
		EMPLOYEE_PAYROLL_DEDUCTION
	};
	
	private EmployeePayrollDBService() {
		preparedStatements = new Hashtable<String, PreparedStatement>();
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
	
	private void makePreparedStatement(PreparedStatementOptions preparedStatementOptions) {
		if(preparedStatements.containsKey(preparedStatementOptions.toString())) {
			return;
		}
		try {
			Connection connection = getConnection();
			String sql = "";
			switch(preparedStatementOptions) {
			case ALL_EMPLOYEE_PAYROLL_DATA:
				sql = "SELECT e_p.id AS employee_payroll_id, e.id AS employee_id,  e.employee_name, e.gender, "
						+ "e_p.salary, e_p.basic_pay, e_p.taxable_pay, e_p.income_tax, e_p.net_pay, e.start_date "
						+ "FROM employee_payroll AS e_p "
						+ "LEFT JOIN employee AS e ON e_p.employee_id = e.id";
				break;
			case EMPLOYEE_PAYROLL_ID_BY_EMPLOYEE_NAME:
				sql = "SELECT id FROM employee WHERE employee_name = ?";
				break;
			case EMPLOYEE_PAYROLL_DEDUCTION:
				sql = "SELECT * FROM deduction WHERE employee_payroll_id = ?";
				break;
			}
			preparedStatements.put(preparedStatementOptions.toString(), connection.prepareStatement(sql));
		} catch(Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public ArrayList<EmployeePayroll> readEmployeePayrolls() {
		ArrayList<EmployeePayroll> employeePayrollList = new ArrayList<EmployeePayroll>();
		if(!preparedStatements.containsKey("ALL_EMPLOYEE_PAYROLL_DATA")) {
			makePreparedStatement(PreparedStatementOptions.ALL_EMPLOYEE_PAYROLL_DATA);
		}
		if(!preparedStatements.containsKey("EMPLOYEE_PAYROLL_DEDUCTION")) {
			makePreparedStatement(PreparedStatementOptions.EMPLOYEE_PAYROLL_DEDUCTION);
		}
		try {
			boolean isPreparedStatementExecuted = preparedStatements.get("ALL_EMPLOYEE_PAYROLL_DATA").execute();
			System.out.println("Prepered ststement "+(isPreparedStatementExecuted ? "" : "not " )+"executed successfully. and employee payroll data retrived.");
			ResultSet resultSetEmployeePayroll = preparedStatements.get("ALL_EMPLOYEE_PAYROLL_DATA").getResultSet();
			while(resultSetEmployeePayroll.next()) {
				EmployeePayroll tempEmployeePayroll = new EmployeePayroll(resultSetEmployeePayroll.getInt("employee_payroll_id"));
				tempEmployeePayroll.setSalary(resultSetEmployeePayroll.getFloat("salary"));
				tempEmployeePayroll.setBasicPay(resultSetEmployeePayroll.getFloat("basic_pay"));
				tempEmployeePayroll.setTaxablePay(resultSetEmployeePayroll.getFloat("taxable_pay"));
				tempEmployeePayroll.setIncomeTax(resultSetEmployeePayroll.getFloat("income_tax"));
				tempEmployeePayroll.setNetPay(resultSetEmployeePayroll.getFloat("net_pay"));
				Employee tempEmployee = new Employee(resultSetEmployeePayroll.getInt("employee_id"));
				tempEmployee.setEmployeeName(resultSetEmployeePayroll.getNString("employee_name"));
				tempEmployee.setEmployeeGender(resultSetEmployeePayroll.getString("gender").charAt(0));
				tempEmployee.setStartDate(resultSetEmployeePayroll.getString("start_date"));
				tempEmployee.setEmployeePayroll(tempEmployeePayroll);
				preparedStatements.get("EMPLOYEE_PAYROLL_DEDUCTION").setInt(1, tempEmployeePayroll.getId());
				ResultSet resultSetDeduction = preparedStatements.get("EMPLOYEE_PAYROLL_DEDUCTION").executeQuery();
				while(resultSetDeduction.next()) {
					tempEmployeePayroll.addDeduction(
							resultSetDeduction.getInt("id"), 
							resultSetDeduction.getString("deduction_name"), 
							resultSetDeduction.getFloat("deduction_amount"));
				}
				employeePayrollList.add(tempEmployeePayroll);
			}
		} catch(Exception exception) {
			exception.printStackTrace();
		}
		return employeePayrollList;
	}
	
	public String getEmployeeIdByName(String employeeName) {
		if(!preparedStatements.containsKey("EMPLOYEE_PAYROLL_ID_BY_EMPLOYEE_NAME")) {
			makePreparedStatement(PreparedStatementOptions.EMPLOYEE_PAYROLL_ID_BY_EMPLOYEE_NAME);
		}
		try {
			preparedStatements.get("EMPLOYEE_PAYROLL_ID_BY_EMPLOYEE_NAME").setString(1, employeeName);
			ResultSet resultSetEmployee = preparedStatements.get("EMPLOYEE_PAYROLL_ID_BY_EMPLOYEE_NAME").executeQuery();
			while(resultSetEmployee.next()) {
				return String.valueOf(resultSetEmployee.getInt("id"));
			}
		} catch(Exception exception) {
			System.out.println("problem in get employee id by name prepared statement. Exception is - "+exception);
		}
		return "";
	}
	
	public void showGenderAggregates() {
		Connection connection  = getConnection();
		try {
			Statement statement = connection.createStatement();
			String sql = "SELECT "
					+ "CASE "
					+ "    WHEN employee.gender = 'M' THEN 'Male'"
					+ "    WHEN employee.gender = 'F' THEN 'Female'"
					+ "END AS employee_gender, "
					+ "SUM(employee_payroll.salary), AVG(employee_payroll.salary), MIN(employee_payroll.salary), "
					+ "MAX(employee_payroll.salary), COUNT(employee_payroll.salary) "
					+ "FROM employee_payroll "
					+ "LEFT JOIN employee ON employee_payroll.employee_id = employee.id "
					+ "GROUP BY employee.gender";
			statement.execute(sql);
			ResultSet resultSet = statement.getResultSet();
			while(resultSet.next()) {
				System.out.println("GENDER => "+resultSet.getString("employee_gender"));
				System.out.println("SUM => "+resultSet.getString("SUM(employee_payroll.salary)"));
				System.out.println("AVG => "+resultSet.getString("AVG(employee_payroll.salary)"));
				System.out.println("MIN => "+resultSet.getString("MIN(employee_payroll.salary)"));
				System.out.println("MAX => "+resultSet.getString("MAX(employee_payroll.salary)"));
				System.out.println("COUNT => "+resultSet.getString("COUNT(employee_payroll.salary)"));
			}
		} catch(Exception exception) {
			System.out.println("problem in show gender aggregates . Exception is - "+exception);
		}
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
