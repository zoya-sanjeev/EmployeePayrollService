package com.bridgelabz.employeepayroll;

public class PayrollDetails {
	int employeeId;
	double basicPay;
	double deductions;
	double taxablePay;
	double tax;
	double netPay;
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public double getBasicPay() {
		return basicPay;
	}
	public void setBasicPay(double basicPay) {
		this.basicPay = basicPay;
	}
	public double getDeductions() {
		return deductions;
	}
	public void setDeductions(double deductions) {
		this.deductions = deductions;
	}
	public double getTaxablePay() {
		return taxablePay;
	}
	public void setTaxablePay(double taxablePay) {
		this.taxablePay = taxablePay;
	}
	public double getTax() {
		return tax;
	}
	public void setTax(double tax) {
		this.tax = tax;
	}
	public double getNetPay() {
		return netPay;
	}
	public void setNetPay(double netPay) {
		this.netPay = netPay;
	}
	

}
