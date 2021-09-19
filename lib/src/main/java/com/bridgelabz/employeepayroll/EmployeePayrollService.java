package com.bridgelabz.employeepayroll;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeePayrollService {
	
	private List<EmployeePayrollData> employeePayrollList;
	
	public EmployeePayrollService() {
		this.employeePayrollList=new ArrayList<EmployeePayrollData>();
	}

	private void readEmployeePayrollData(Scanner consoleInputReader) {
		System.out.println("Enter Employee ID");
		int id=consoleInputReader.nextInt();
		System.out.println("Enter employee name");
		String name=consoleInputReader.next();
		System.out.println("Enter Employee Salary");
		Double salary =consoleInputReader.nextDouble();
		employeePayrollList.add(new EmployeePayrollData(id, name, salary));
	}
	public static void main(String[] args) {
		EmployeePayrollService employeePayrollService= new EmployeePayrollService();
		Scanner consoleInputReader=new Scanner(System.in);
		

	}

}
