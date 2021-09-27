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

public class EmployeePayrollDBService {
	
	private static EmployeePayrollDBService employeePayrollDBService;
	private PreparedStatement employeePayrollStatement;
	
	private EmployeePayrollDBService() {
		
	}
	public static EmployeePayrollDBService getInstance() {
		if(employeePayrollDBService == null)
			employeePayrollDBService = new EmployeePayrollDBService();
		return employeePayrollDBService;
	}

	private Connection getConnection() throws SQLException {
		String jdbcURL="jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
		String userName="root";
		String password="abcd1234";
		Connection connection;
		System.out.println("Connecting to database"+ jdbcURL);
		connection=DriverManager.getConnection(jdbcURL,userName,password);
		System.out.println("Connection is successfull"+ connection);
		return connection;
	}
	
	public List<EmployeePayrollData> readData() throws SQLException{
		String sql = "select p.employee_id, e.employee_name, p.basic_pay, e.start_date "
				+ " from employee e, payroll p"
				+ " where e.employee_id=p.employee_id";
		List<EmployeePayrollData> employeePayrollList= new ArrayList<>();
		try(Connection connection =this.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			employeePayrollList= this.getEmployeePayrollData(result);
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return employeePayrollList;
	}

	public int updateEmployeeData(String name, double salary) {
		return this.updateEmployeeDataUsingStatement(name,salary);
	}

	private int updateEmployeeDataUsingStatement(String name, double salary) {
		String sql=String.format("update payroll set basic_pay= %.2f where employee_id = ( select employee_id from employee where employee_name ='%s');", salary,name);
		try(Connection connection =this.getConnection()) {
			Statement statement = connection.createStatement();
			return statement.executeUpdate(sql);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return 0;
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
	private List<EmployeePayrollData> getEmployeePayrollData(ResultSet resultSet) {
		List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
		try {
			while(resultSet.next()) {
				int id = resultSet.getInt("employee_id");
				String name = resultSet.getString("employee_name");
				Double salary=resultSet.getDouble("basic_pay");
				LocalDate startDate=resultSet.getDate("start_date").toLocalDate();
				employeePayrollList.add(new EmployeePayrollData(id, name, salary, startDate)); 		
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return employeePayrollList;
	}
	
	public List<EmployeePayrollData> getEmployeesInGivenStartRange(String date) throws SQLException {
		String sql=String.format("select e.employee_id, e.employee_name, p.basic_pay, e.start_date from employee e, payroll p where e.employee_id=p.employee_id and start_date between cast('%s' as date) and date(now());", date);
		List<EmployeePayrollData>listOfEmployees=new ArrayList<>();
		try(Connection connection =this.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			listOfEmployees=this.getEmployeePayrollData(result);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return listOfEmployees;
	}
	
	private void prepareStatementForEmployeeData() {
		try {
			Connection connection =this.getConnection();
			String sql = "select p.employee_id, e.employee_name, p.basic_pay, e.start_date "
					+ " from employee e, payroll p"
					+ " where e.employee_id=p.employee_id and e.employee_name=?";
			employeePayrollStatement=connection.prepareStatement(sql);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	public double getSumOfSalariesBasedOnGender(char gender) {
		String sql=String.format("select sum(p.basic_pay) from employee e, payroll p where e.employee_id=p.employee_id and e.gender='%c';", gender);
		double sumOfSalaries=0.0;
		try(Connection connection =this.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			sumOfSalaries=result.getDouble(gender);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return sumOfSalaries;
	}
	



}
