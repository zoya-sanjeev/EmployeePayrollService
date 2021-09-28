package com.bridgelabz.employeepayroll;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.bridgelabz.employeepayroll.EmployeePayrollException.ExceptionType;

public class EmployeePayrollDBService {
	
	private static EmployeePayrollDBService employeePayrollDBService;
	private PreparedStatement employeePayrollStatement;
	
	private EmployeePayrollDBService() {
		
	}
	
	private void prepareStatementForEmployeeData()throws EmployeePayrollException {
		try {
			Connection connection =this.getConnection();
			String sql = "select * from employee_payroll where name=?;";
			employeePayrollStatement=connection.prepareStatement(sql);
		}catch(SQLException e) {
			throw new EmployeePayrollException(ExceptionType.INVALID_QUERY, "Check query");
		}
		
	}
	
	private Connection getConnection() throws EmployeePayrollException {
		Connection connection;
		try {
			String jdbcURL="jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
			String userName="root";
			String password="abcd1234";
			System.out.println("Connecting to database"+ jdbcURL);
			connection=DriverManager.getConnection(jdbcURL,userName,password);
			System.out.println("Connection is successfull"+ connection);
		}catch(SQLException e) {
			throw new EmployeePayrollException(ExceptionType.CONNECTION_FAILED, "Connection Failed");
		}
		return connection;
	}
	
	public static EmployeePayrollDBService getInstance() {
		if(employeePayrollDBService == null)
			employeePayrollDBService = new EmployeePayrollDBService();
		return employeePayrollDBService;
	}
	
	public List<EmployeePayrollData> readData() throws EmployeePayrollException{
		String sql = "select * from employee_payroll;";
		List<EmployeePayrollData> employeePayrollList= new ArrayList<>();
		try(Connection connection =this.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			employeePayrollList= this.getEmployeePayrollData(result);
			
		}catch(SQLException e) {
			throw new EmployeePayrollException(ExceptionType.INVALID_QUERY, "Check query");
		}
		return employeePayrollList;
	}

	public List<EmployeePayrollData> getEmployeePayrollData(String name) {
		List<EmployeePayrollData> employeePayrollList=null;
		if(this.employeePayrollStatement == null)
			this.prepareStatementForEmployeeData();
		try {
			employeePayrollStatement.setString(1, name);
			ResultSet resultSet = employeePayrollStatement.executeQuery();
			employeePayrollList= this.getEmployeePayrollData(resultSet);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return employeePayrollList;
	}
	

	public List<EmployeePayrollData> getEmployeePayrollData(ResultSet resultSet) {
		List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
		try {
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				Double salary=resultSet.getDouble("salary");
				LocalDate startDate=resultSet.getDate("start_date").toLocalDate();
				employeePayrollList.add(new EmployeePayrollData(id, name, salary, startDate)); 		
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return employeePayrollList;
	}
	

	public int updateEmployeeData(String name, double salary) {
		return this.updateEmployeeDataUsingStatement(name,salary);
	}

	private int updateEmployeeDataUsingStatement(String name, double salary) throws EmployeePayrollException{
		String sql=String.format("update employee_payroll set salary='%.2f' where name= '%s';", salary,name);
		try(Connection connection =this.getConnection()) {
			Statement statement = connection.createStatement();
			return statement.executeUpdate(sql);
		}catch(SQLException e) {
			throw new EmployeePayrollException(ExceptionType.INVALID_QUERY, "Check query");
		}
	}
	
	
	public List<EmployeePayrollData> getEmployeesInGivenStartRange(LocalDate startDate, LocalDate endDate) throws EmployeePayrollException {
		String sql=String.format("select * from employee_payroll where start_date between '%s' and '%s';",Date.valueOf(startDate), Date.valueOf(endDate) );
		List<EmployeePayrollData>listOfEmployees=new ArrayList<>();
		try(Connection connection =this.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			listOfEmployees=this.getEmployeePayrollData(result);
		}catch(SQLException e) {
			throw new EmployeePayrollException(ExceptionType.INVALID_QUERY, "Check query");
		}
		return listOfEmployees;
	}
	
	
	public double getSumOfSalariesBasedOnGender(char gender) throws EmployeePayrollException {
		String sql=String.format("select e.gender, sum(p.basic_pay) from employee e, payroll p where e.employee_id=p.employee_id group by gender;;", gender);
		double sumOfSalaries=0.0;
		try(Connection connection =this.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			if(gender=='F') {
				result.next();
				sumOfSalaries=result.getDouble(2);
			}else {
				result.next();
				result.next();
				sumOfSalaries=result.getDouble(2);
			}
		}catch (SQLException e) {
			throw new EmployeePayrollException(ExceptionType.INVALID_QUERY, "Check query");
		}
		return sumOfSalaries;
	}
	public double getMinOfSalariesBasedOnGender(char gender) throws EmployeePayrollException{
		String sql=String.format("select e.gender, min(p.basic_pay) from employee e, payroll p where e.employee_id=p.employee_id group by gender;", gender);
		double minOfSalaries=0.0;
		try(Connection connection =this.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			if(gender=='F') {
				result.next();
				minOfSalaries=result.getDouble(2);
			}else {
				result.next();
				result.next();
				minOfSalaries=result.getDouble(2);
			}
		}catch (SQLException e) {
			throw new EmployeePayrollException(ExceptionType.INVALID_QUERY, "Check query");
		}
		return minOfSalaries;
	}
	public double getMaxOfSalariesBasedOnGender(char gender) throws EmployeePayrollException{
		String sql=String.format("select e.gender, max(p.basic_pay) from employee e, payroll p where e.employee_id=p.employee_id group by gender;", gender);
		double maxOfSalaries=0.0;
		try(Connection connection =this.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			if(gender=='F') {
				result.next();
				maxOfSalaries=result.getDouble(2);
			}else {
				result.next();
				result.next();
				maxOfSalaries=result.getDouble(2);
			}
		}catch (SQLException e) {
			throw new EmployeePayrollException(ExceptionType.INVALID_QUERY, "Check query");
		}
		return maxOfSalaries;
	}
	public double getAvgOfSalariesBasedOnGender(char gender) throws EmployeePayrollException{
		String sql=String.format("select e.gender, avg(p.basic_pay) from employee e, payroll p where e.employee_id=p.employee_id group by gender;", gender);
		double avgOfSalaries=0.0;
		try(Connection connection =this.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			if(gender=='F') {
				result.next();
				avgOfSalaries=result.getDouble(2);
			}else {
				result.next();
				result.next();
				avgOfSalaries=result.getDouble(2);
			}
		}catch (SQLException e) {
			throw new EmployeePayrollException(ExceptionType.INVALID_QUERY, "Check query");
		}
		return avgOfSalaries;
	}
	public int getCountBasedOnGender(char gender) throws EmployeePayrollException{
		String sql=String.format("select gender, count(*) from employee e group by gender;", gender);
		int countOfEmployees=0;
		try(Connection connection =this.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			if(gender=='F') {
				result.next();
				countOfEmployees=result.getInt(2);
			}else {
				result.next();
				result.next();
				countOfEmployees=result.getInt(2);
			}
		}catch (SQLException e) {
			throw new EmployeePayrollException(ExceptionType.INVALID_QUERY, "Check query");
		}
		return countOfEmployees;
	}
	public EmployeePayrollData addEmployeeToPayrollUC7(String name, double salary, LocalDate startDate, char gender) throws EmployeePayrollException, SQLException {
		int employeeId = -1;
		EmployeePayrollData employeePayrollData = null;
		String sql = String.format("insert into employee_payroll(name,salary, start_date,gender)"+ 
				"values('%s','%s','%s','%c' )", name,salary, Date.valueOf(startDate),gender );
		try(Connection connection = this.getConnection()){
			Statement statement = connection.createStatement();
			int rowAffected = statement.executeUpdate(sql,statement.RETURN_GENERATED_KEYS);
			if(rowAffected == 1) {
				ResultSet resultSet = statement.getGeneratedKeys();
				if(resultSet.next())
					employeeId = resultSet.getInt(1);
			}
			employeePayrollData = new EmployeePayrollData(employeeId, name, salary, startDate);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return employeePayrollData;
	}


	public EmployeePayrollData addEmployeeToPayroll(String name, double salary, LocalDate startDate, char gender) throws EmployeePayrollException, SQLException {
		int employeeId = -1;
		Connection connection = null;
		EmployeePayrollData employeePayrollData = null;
		connection = this.getConnection();
		
		try (Statement statement = connection.createStatement()){
			
			String sql = String.format("insert into employee_payroll (name, salary, start_date, gender ) VALUES ('%s', '%s', '%s', '%s');", name,salary, Date.valueOf(startDate), gender );
			
			int rowAffected = statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
			if(rowAffected == 1) {
				ResultSet resultSet = statement.getGeneratedKeys();
				if(resultSet.next())
					employeeId = resultSet.getInt(1);
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		try(Statement statement = connection.createStatement()){

			double deductions = salary * 0.2;
			double taxablePay = salary - deductions;
			double tax = taxablePay * 0.1;
			double netPay = salary - tax;
			String sql = String.format("insert into payroll_details "
					+ "(employee_id, basic_pay, deductions, taxable_pay, tax, net_pay)"
					+ " values ('%s', '%s', '%s', '%s', '%s','%s')",employeeId, salary, deductions, taxablePay, tax, netPay);
			int rowAffected = statement.executeUpdate(sql);
			if (rowAffected == 1) {
				employeePayrollData = new EmployeePayrollData(employeeId, name, salary, startDate);
			}			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return employeePayrollData;
	}
	



}
