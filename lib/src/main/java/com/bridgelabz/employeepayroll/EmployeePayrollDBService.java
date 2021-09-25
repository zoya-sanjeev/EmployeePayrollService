package com.bridgelabz.employeepayroll;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollDBService {
	
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
		String sql = "select p.employee_id, e.employee_name, p.basic_pay "
				+ " from employee e, payroll p"
				+ " where e.employee_id=p.employee_id";
		List<EmployeePayrollData> employeePayrollList= new ArrayList<>();
		try(Connection connection =this.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			while(result.next()) {
				int id=result.getInt("employee_id");
				String name = result.getString("employee_name");
				Double salary=result.getDouble("basic_pay");
				employeePayrollList.add(new EmployeePayrollData(id, name, salary));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return employeePayrollList;
	}

	public int updateEmployeeData(String name, double salary) {
		return this.updateEmployeeDataUsingStatement(name,salary);
	}

	private int updateEmployeeDataUsingStatement(String name, double salary) {
		String sql=String.format("update payroll set salary= %.2f where employee_id = ( select employee_id from employee where employee_name ='%s';);", salary,name);
		try(Connection connection =this.getConnection()) {
			Statement statement = connection.createStatement();
			return statement.executeUpdate(sql);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}


}
