package com.bridgelabz.employeepayroll;

import org.junit.Assert;
import org.junit.Test;

import com.bridgelabz.employeepayroll.EmployeePayrollService.IOService;

import java.sql.SQLException;
import java.util.*;
public class EmployeePayrollServiceTest {
	
	@Test public void given3EmployeesWhenWrittenToFileShouldMatchEmployeeEntries() {
		EmployeePayrollData[] arrayOfEmployees= {
				new EmployeePayrollData(1,"Jeff Bezos",10000.0),
				new EmployeePayrollData(2,"Bill Gates", 5000.0),
				new EmployeePayrollData(3,"Mark Zuckerberg", 3000.0)
		};
		EmployeePayrollService employeePayrollService;
		employeePayrollService = new EmployeePayrollService(Arrays.asList(arrayOfEmployees));
		employeePayrollService.writeEmployeePayrollData(IOService.FILE_IO);
		employeePayrollService.printData(IOService.FILE_IO);
		long entries = employeePayrollService.countEntries(IOService.FILE_IO);
		Assert.assertEquals(3, entries);
	}
	
	@Test public void givenFilesOnReadingFromFilesShouldMatchEmployeeCount() {
		
		EmployeePayrollService employeePayrollService=new EmployeePayrollService();
		long entries=employeePayrollService.readEmployeePayrollData(IOService.FILE_IO);
	}
	@Test
	public void givenEmployeePayrollInDB_WhenRetrieved_ShouldMatchEmployeeCount() throws SQLException {
		EmployeePayrollService employeePayrollService=new EmployeePayrollService();
		List<EmployeePayrollData> employeePayrollData= employeePayrollService.readEmployeePayrollDBData(IOService.DB_IO);
		Assert.assertEquals(3, employeePayrollData.size());
	}
	
	@Test
	public void givenNewSalaryForEmployee_WhenUpdates_ShouldSyncWithDB() throws SQLException {
		EmployeePayrollService employeePayrollService= new EmployeePayrollService();
		List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollDBData(IOService.DB_IO);
		employeePayrollService.updateEmployeeSalary("Terisa",3000000.00);
		boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Terisa");
		Assert.assertTrue(result);
	}
	
	@Test 
	public void givenDateRangeForEmployee_WhenRetrieved_shouldMatchGivenCount() throws SQLException {
		EmployeePayrollService employeePayrollService= new EmployeePayrollService();
		String date="2018-01-01";
		List<EmployeePayrollData> lisdtOfEmployeesInDateRange=employeePayrollService.getEmployeesInGivenStartDateRange(date);
		Assert.assertEquals(3, lisdtOfEmployeesInDateRange.size());
	}
	
	@Test
	public void givenEmployeePayrollInDB_shouldReturnSumOfSalaryOfFemaleEmployees() throws SQLException{
		EmployeePayrollService employeePayrollService= new EmployeePayrollService();
		char gender='F';
		double sumOfSalary=employeePayrollService.getSumOfSalaryBasedOnGender(gender);
		Assert.assertEquals(3000000.00, sumOfSalary, 0.0);
	}
	
	@Test
	public void givenEmployeePayrollInDB_shouldReturnSumOfSalaryOfMaleEmployees() throws SQLException{
		EmployeePayrollService employeePayrollService= new EmployeePayrollService();
		char gender='M';
		double sumOfSalary=employeePayrollService.getSumOfSalaryBasedOnGender(gender);
		Assert.assertEquals(400000.00, sumOfSalary, 0.0);
	}
	
	@Test
	public void givenEmployeePayrollInDB_shouldReturnMinSalaryOfSalaryOfFemaleEmployees() throws SQLException{
		EmployeePayrollService employeePayrollService= new EmployeePayrollService();
		char gender='F';
		double minOfSalary=employeePayrollService.getMinOfSalaryBasedOnGender(gender);
		Assert.assertEquals(3000000.00, minOfSalary, 0.0);
	}
	
	@Test
	public void givenEmployeePayrollInDB_shouldReturnMinSalaryOfSalaryOfMaleEmployees() throws SQLException{
		EmployeePayrollService employeePayrollService= new EmployeePayrollService();
		char gender='M';
		double minOfSalary=employeePayrollService.getMinOfSalaryBasedOnGender(gender);
		Assert.assertEquals(100000.00, minOfSalary, 0.0);
	}
	
	@Test
	public void givenEmployeePayrollInDB_shouldReturnMaxSalaryOfSalaryOfFemaleEmployees() throws SQLException{
		EmployeePayrollService employeePayrollService= new EmployeePayrollService();
		char gender='F';
		double maxOfSalary=employeePayrollService.getMaxOfSalaryBasedOnGender(gender);
		Assert.assertEquals(3000000.00, maxOfSalary, 0.0);
	}
	@Test
	public void givenEmployeePayrollInDB_shouldReturnMaxSalaryOfSalaryOfMaleEmployees() throws SQLException{
		EmployeePayrollService employeePayrollService= new EmployeePayrollService();
		char gender='M';
		double maxOfSalary=employeePayrollService.getMaxOfSalaryBasedOnGender(gender);
		Assert.assertEquals(300000.00, maxOfSalary, 0.0);
	}
	
	@Test
	public void givenEmployeePayrollInDB_shouldReturnAverageSalaryOfFemaleEmployees() throws SQLException{
		EmployeePayrollService employeePayrollService= new EmployeePayrollService();
		char gender='F';
		double avgOfSalary=employeePayrollService.getAverageOfSalaryBasedOnGender(gender);
		Assert.assertEquals(3000000.00, avgOfSalary, 0.0);
	}
	
	@Test
	public void givenEmployeePayrollInDB_shouldReturnAverageSalaryOfMaleEmployees() throws SQLException{
		EmployeePayrollService employeePayrollService= new EmployeePayrollService();
		char gender='M';
		double avgOfSalary=employeePayrollService.getAverageOfSalaryBasedOnGender(gender);
		Assert.assertEquals(200000.00, avgOfSalary, 0.0);
	}
	@Test
	public void givenEmployeePayrollInDB_shouldReturnCountOfFemaleEmployees() throws SQLException{
		EmployeePayrollService employeePayrollService= new EmployeePayrollService();
		char gender='F';
		int count=employeePayrollService.getCountBasedOnGender(gender);
		Assert.assertEquals(1,count);
	}
	@Test
	public void givenEmployeePayrollInDB_shouldReturnCountOfMaleEmployees() throws SQLException{
		EmployeePayrollService employeePayrollService= new EmployeePayrollService();
		char gender='M';
		int count=employeePayrollService.getCountBasedOnGender(gender);
		Assert.assertEquals(2, count);
	}
	
}
