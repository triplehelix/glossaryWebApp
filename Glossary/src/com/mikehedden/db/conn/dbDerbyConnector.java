package com.mikehedden.db.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class dbDerbyConnector {
	static final String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	static final String DB_URL = "jdbc:derby:glossaryDB";

	public dbDerbyConnector() throws ClassNotFoundException {
		Class.forName(JDBC_DRIVER);
	}
	
	public Connection getConnection() throws SQLException {
		Connection conn;
		try {
			conn = DriverManager.getConnection(DB_URL);
		} catch( SQLException e ){
			if(e.getMessage().contains("not found")){
				System.out.println("Creating Database Tables.");
				conn = createDB();
			}else{
				throw e;
			}
		}
		return conn;
	}

	private Connection createDB() throws SQLException {
		Connection conn = DriverManager.getConnection(DB_URL + ";create=true");
		dbConnector.createTables(conn);

		return conn;
	}
	

}
