package com.payrollservice.test;

import java.sql.Connection;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.payrollservice.EmployeePayroll;
import com.payrollservice.PayrollService;

public class TestEmployeePayroll {
	private ArrayList<EmployeePayroll> employeePayrollList;
	@Before
	public void setUp() throws Exception {
		PayrollService payrollService = new PayrollService();
		Connection connection = null;
		if(payrollService.isDriverLoaded()) {
			connection = payrollService.getConnection();
		}
		employeePayrollList = payrollService.readEmployeePayrolls(connection);
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
