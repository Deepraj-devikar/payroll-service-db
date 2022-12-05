package com.payrollservice;

public class Employee {
	public class Address{
		private int id;
		private String line1, line2, city, state, country, zip;
		private Address() {
			line1 = "";
			line2 = "";
			city = "";
			state = "";
			country = "";
			zip = "";
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getLine1() {
			return line1;
		}
		public void setLine1(String line1) {
			this.line1 = line1;
		}
		public String getLine2() {
			return line2;
		}
		public void setLine2(String line2) {
			this.line2 = line2;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public String getZip() {
			return zip;
		}
		public void setZip(String zip) {
			this.zip = zip;
		}
	}
	private int id;
	private String employeeName;
	private char employeeGender;
	private String startDate;
	private Address address;
	private EmployeePayroll employeePayroll;
	public Employee(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public char getEmployeeGender() {
		return employeeGender;
	}
	public void setEmployeeGender(char employeeGender) {
		this.employeeGender = employeeGender;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public EmployeePayroll getEmployeePayroll() {
		return employeePayroll;
	}
	public void setEmployeePayroll(EmployeePayroll employeePayroll) {
		this.employeePayroll = employeePayroll;
		if(this.employeePayroll.getEmployee() == null) {
			this.employeePayroll.setEmployee(this);
		}
	}
	public Address getAddress() {
		if(address == null) {
			address = new Address();
		}
		return address;
	}
	@Override
	public String toString() {
		return "\n Employee name => "+employeeName+
				"\n Gender => "+(employeeGender == 'M' ? "Male" : "Female")+
				"\n Start Date => "+startDate;
	}
}
