package com.payrollservice;

import java.util.ArrayList;

public class PayrollService {

	public static void main(String[] args) {		
		EmployeePayrollDBService employeePayrollDBService = EmployeePayrollDBService.getInstance();
		String terissaPayrollId = employeePayrollDBService.getEmployeeIdByName("Terissa");
		System.out.println("Terissa payroll ID => "+terissaPayrollId);
		ArrayList<String[]> dataToUpdate = new ArrayList<String[]>();
		String[] info1 = {"basic_pay", "3000000.00"};
		String[] info2 = {"salary", "3500000.00"};
		dataToUpdate.add(info1);
		dataToUpdate.add(info2);
		employeePayrollDBService.updateEmployeePayroll(terissaPayrollId, dataToUpdate);
		ArrayList<EmployeePayroll> employeePayrollList = employeePayrollDBService.readEmployeePayrolls();
		System.out.println(employeePayrollList);
	}
}
