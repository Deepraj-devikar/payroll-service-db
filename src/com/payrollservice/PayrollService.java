package com.payrollservice;

import java.util.ArrayList;

public class PayrollService {

	public static void main(String[] args) {		
		EmployeePayrollDBService employeePayrollDBService = EmployeePayrollDBService.getInstance();
		ArrayList<EmployeePayroll> employeePayrollList = employeePayrollDBService.readEmployeePayrolls();
		System.out.println(employeePayrollList);
		System.out.println(employeePayrollDBService.getEmployeeIdByName("Terissa"));
	}
}
