package com.aungkhant.model;

import java.lang.annotation.Annotation;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class TestJDBC {
	public static void main(String[] args) {
		 String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
		 String DB_URL = "jdbc:mysql://localhost";
		 String USER = "root";
		 String PASS = "";
		 String dataBase = "test";
		 
		 try {
//			 Connection conn;
//			 Class.forName("com.mysql.jdbc.Driver");
//		     conn = DriverManager.getConnection(DB_URL, USER, PASS);
//		     Statement stmt = conn.createStatement();
//		     Class<?> clas= Test.class;
//		     Annotation annotation = clas.getAnnotation(Table.class);
//		     System.out.println(((Table)annotation).value());
//		      
//		     String sql = "CREATE DATABASE IF NOT EXISTS "+dataBase+";";
//		     stmt.executeUpdate(sql);
		      
			 ConnectionParameter connectionParameter = new ConnectionParameter();
			 connectionParameter.setDataBase(dataBase);
			 connectionParameter.setDriver(JDBC_DRIVER);
			 connectionParameter.setPassword(PASS);
			 connectionParameter.setUrl(DB_URL);
			 connectionParameter.setUserName(USER);
			 EntityManager<Test> entityManager = new EntityManager<Test>();
			 entityManager.setConnectionParameter(connectionParameter);
			 Test test = new Test(); 
			 entityManager.createTable(test);
			 
			 
			 
		 }catch (Exception e) {
			e.printStackTrace();
		}
		   
	}
}
