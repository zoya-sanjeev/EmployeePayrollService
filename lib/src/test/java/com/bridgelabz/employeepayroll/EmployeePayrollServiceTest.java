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
}
