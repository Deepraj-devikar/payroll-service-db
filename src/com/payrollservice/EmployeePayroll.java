package com.payrollservice;

import java.util.ArrayList;

public class EmployeePayroll {
	public class Deduction{
		private int id;
		private String deductionName;
		private float deductionAmount;
		private Deduction(int id) {
			this.id = id;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getDeductionName() {
			return deductionName;
		}
		public void setDeductionName(String deductionName) {
			this.deductionName = deductionName;
		}
		public float getDeductionAmount() {
			return deductionAmount;
		}
		public void setDeductionAmount(float deductionAmount) {
			this.deductionAmount = deductionAmount;
		}
		@Override
		public String toString() {
			return "\n\t deduction name => "+deductionName+", deduction name => "+deductionAmount;
		}
	}
	private int id;
	private Employee employee;
	private float salary;
	private float basicPay;
	private float taxablePay;
	private float incomeTax;
	private float netPay;
	ArrayList<Deduction> deductions;
	
	public EmployeePayroll(int id) {
		this.id = id;
		deductions = new ArrayList<Deduction>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
		if(this.employee.getEmployeePayroll() == null) {
			this.employee.setEmployeePayroll(this);
		}
	}

	public float getSalary() {
		return salary;
	}

	public void setSalary(float salary) {
		this.salary = salary;
	}

	public float getBasicPay() {
		return basicPay;
	}

	public void setBasicPay(float basicPay) {
		this.basicPay = basicPay;
	}

	public float getTaxablePay() {
		return taxablePay;
	}

	public void setTaxablePay(float taxablePay) {
		this.taxablePay = taxablePay;
	}

	public float getIncomeTax() {
		return incomeTax;
	}

	public void setIncomeTax(float incomeTax) {
		this.incomeTax = incomeTax;
	}

	public float getNetPay() {
		return netPay;
	}

	public void setNetPay(float netPay) {
		this.netPay = netPay;
	}

	public ArrayList<Deduction> getDeductions() {
		return deductions;
	}

	public void addDeduction(int id, String deductionName, Float deductionAmount) {
		Deduction deduction = new Deduction(id);
		deduction.setDeductionName(deductionName);
		deduction.setDeductionAmount(deductionAmount);
		this.deductions.add(deduction);
	}	
	@Override
	public String toString() {
		return employee.toString()+
				"\n Salary => "+salary+
				"\n Basic Pay => "+basicPay+
				"\n Taxable Pay => "+taxablePay+
				"\n Income Tax => "+incomeTax+
				"\n Net Pay => "+netPay+
				"\n Deductions => "+deductions.toString();
	}
}