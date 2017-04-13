package com.mikehedden.db.conn;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class dbConnector {
	private Connection conn = null;
	private dbDerbyConnector derbyConnector = null;
	public dbConnector(){
		try {
			derbyConnector = new dbDerbyConnector();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection(){
		if (null == conn) {
			try {
				conn = derbyConnector.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return conn;
	}

	public static void createTables(Connection conn) throws SQLException{
	    System.out.println("Creating Tables.");
		// Create the database from scratch
		String createProjectsTable = "CREATE TABLE projects (" +
				"  project_id int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)," +
				"  project_name varchar(128) NOT NULL," +
				"  project_description varchar(1024) DEFAULT NULL," +
				"  PRIMARY KEY (project_id)" +
				")";

		String createWordsTable = "CREATE TABLE words (" +
				"  words_id int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)," +
				"  word varchar(128) NOT NULL," +
				"  definition varchar(4096) DEFAULT NULL," +
				"  notes varchar(4096) DEFAULT NULL," +
				"  project_id int DEFAULT NULL," +
				"  PRIMARY KEY (words_id)" +
				")";

		Statement statement = conn.createStatement();
        statement.execute(createProjectsTable);
		statement.execute(createWordsTable);
		statement.close();
	}
	

}
