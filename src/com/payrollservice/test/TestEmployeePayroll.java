package com.payrollservice.test;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.payrollservice.EmployeePayroll;
import com.payrollservice.EmployeePayrollDBService;

public class TestEmployeePayroll {
	private ArrayList<EmployeePayroll> employeePayrollList;
	@Before
	public void setUp() throws Exception {
		EmployeePayrollDBService employeePayrollDBService = EmployeePayrollDBService.getInstance();
		employeePayrollList = employeePayrollDBService.readEmployeePayrolls();
	}

	@Test
	public void test() {
		employeePayrollList.stream()
		.filter(employeePayroll -> employeePayroll.getEmployeeName() == "Terissa")
		.forEach(employeePayroll -> {
			Assert.assertEquals(3000000.00, employeePayroll.getBasicPay(), 0.001);
			Assert.assertEquals(3500000.00, employeePayroll.getSalary(), 0.001);
		});
	}

}
