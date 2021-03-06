package com.bridgelabz.employeepayroll;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeePayrollService {
	
	public enum IOService{CONSOLE_IO, FILE_IO, DB_IO, REST_IO};
	
	private static List<EmployeePayrollData> employeePayrollList;
	private static EmployeePayrollDBService employeePayollDBService;
	
	public EmployeePayrollService() {
		employeePayollDBService= EmployeePayrollDBService.getInstance();
		
	}

	public EmployeePayrollService(List<EmployeePayrollData> employeePayrollList) {
		this();
		this.employeePayrollList = employeePayrollList;
	}
	
	public void readEmployeePayrollData(Scanner consoleInputReader) {
		System.out.println("Enter Employee ID");
		int id=consoleInputReader.nextInt();
		System.out.println("Enter employee name");
		String name=consoleInputReader.next();
		System.out.println("Enter Employee Salary");
		Double salary =consoleInputReader.nextDouble();
		employeePayrollList.add(new EmployeePayrollData(id, name, salary));
	}
	
	public List<EmployeePayrollData> readEmployeePayrollDBData(IOService ioService) throws SQLException{
		if(ioService.equals(IOService.DB_IO))
			this.employeePayrollList = employeePayollDBService.readData();
		return this.employeePayrollList;
	}
	public void updateEmployeeSalary(String name, double salary) {
		int result =  employeePayollDBService.updateEmployeeData(name, salary);
		if(result==0)return;
		EmployeePayrollData employeePayrollData=this.getEmployeePayrollData(name);
		if(employeePayrollData != null) employeePayrollData.salary=salary;
	}

	private EmployeePayrollData getEmployeePayrollData(String name) {
		return this.employeePayrollList.stream()
				.filter(employeePayrollDataItem -> employeePayrollDataItem.name.equals(name))
				.findFirst()
				.orElse(null);
	}
	
	public List<EmployeePayrollData> getEmployeesInGivenStartDateRange(LocalDate startDate, LocalDate endDate) throws SQLException {
		List<EmployeePayrollData> employeesInGivenRange=employeePayollDBService.getEmployeesInGivenStartRange(startDate,endDate);
		return employeesInGivenRange;
	}
	public void writeEmployeePayrollData(EmployeePayrollService.IOService ioservice) {
		if(ioservice==IOService.CONSOLE_IO)
			System.out.println("Writing employee data \n"+ employeePayrollList);
		else if(ioservice==IOService.FILE_IO)
			new EmployeePayrollFileIOService().writeData(employeePayrollList);
	}
	public long readEmployeePayrollData(IOService ioservice) {
		if(ioservice.equals(IOService.FILE_IO))
			this.employeePayrollList=new EmployeePayrollFileIOService().readData();
		return employeePayrollList.size();
	}
	
	public void printData(IOService ioService) {
		if(ioService.equals(IOService.FILE_IO)) {
			new EmployeePayrollFileIOService().printData();
		}
	}
	
	public long countEntries(IOService ioService) {
		if(ioService.equals(IOService.FILE_IO)) {
			return new EmployeePayrollFileIOService().countEntries();
		}
		return 0;
	}
	public static void main(String[] args) {
		ArrayList<EmployeePayrollData> employeePayrollList = new ArrayList<>();
		EmployeePayrollService employeePayrollService = new EmployeePayrollService(employeePayrollList);
		Scanner consoleInputReader=new Scanner(System.in);
		employeePayrollService.readEmployeePayrollData(consoleInputReader);	
		employeePayrollService.writeEmployeePayrollData(IOService.CONSOLE_IO);
	}
	public boolean checkEmployeePayrollInSyncWithDB(String name) {
		List<EmployeePayrollData> employeePayrollDataList=employeePayollDBService.getEmployeePayrollData(name);
		return employeePayrollDataList.get(0).equals(getEmployeePayrollData(name));
		
	}

	public double getSumOfSalaryBasedOnGender(char gender) {
		double sumOfSalaries=employeePayollDBService.getSumOfSalariesBasedOnGender(gender);
		return sumOfSalaries;
	}

	public double getMinOfSalaryBasedOnGender(char gender) {
		double minOfSalaries=employeePayollDBService.getMinOfSalariesBasedOnGender(gender);
		return minOfSalaries;
	}

	public double getMaxOfSalaryBasedOnGender(char gender) {
		double maxOfSalaries=employeePayollDBService.getMaxOfSalariesBasedOnGender(gender);
		return maxOfSalaries;
	}

	public double getAverageOfSalaryBasedOnGender(char gender) {
		double avgOfSalaries=employeePayollDBService.getAvgOfSalariesBasedOnGender(gender);
		return avgOfSalaries;
	}

	public int getCountBasedOnGender(char gender) {
		int countOfSalaries=employeePayollDBService.getCountBasedOnGender(gender);
		return countOfSalaries;
	}

	public static void addEmployeeToPayroll(String name, double salary, LocalDate startDate, char gender) throws EmployeePayrollException, SQLException {
		employeePayrollList.add(employeePayollDBService.addEmployeeToPayroll(name,salary,startDate, gender));
		
	}

	

}
