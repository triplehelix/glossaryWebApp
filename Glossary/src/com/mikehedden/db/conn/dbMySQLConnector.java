package com.mikehedden.db.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbMySQLConnector {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/Glossary";
	static final String DB_USER = "glossary";
	static final String DB_PASS = "gandolf";

	public dbMySQLConnector(){
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection(){
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	

}
