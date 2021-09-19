package com.bridgelabz.employeepayroll;

import org.junit.Assert;
import org.junit.Test;

import com.bridgelabz.employeepayroll.EmployeePayrollService.IOService;

import java.util.*;
public class EmployeePayrollServiceTest {
	
	@Test public void given3EmployeesWhenWrittenToFileShouldMatchEmployeeEntries() {
		EmployeePayrollData[] arrayOfEmployees= {
				new EmployeePayrollData(1,"Jeff Bezos",10000.0),
				new EmployeePayrollData(2,"Bill Gates", 5000.0),
				new EmployeePayrollData(3,"Mark Zuckerberg", 3000.0)
		};
		EmployeePayrollService employeePayService=new EmployeePayrollService();
		employeePayService=(EmployeePayrollService) Arrays.asList(arrayOfEmployees);
		employeePayService.writeEmployeePayrollData(IOService.FILE_IO);
		long entries = employeePayService.countEntries();
		Assert.assertEquals(3, entries);
	}
}
