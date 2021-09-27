package com.bridgelabz.employeepayroll;

public class EmployeePayrollException extends RuntimeException{
	enum ExceptionType{
		CONNECTION_FAILED,
		INVALID_QUERY
	}
	
	ExceptionType exceptionType;

    public EmployeePayrollException(ExceptionType exceptionType, String message) {
        super(message);
        this.exceptionType = exceptionType;
    }

}
