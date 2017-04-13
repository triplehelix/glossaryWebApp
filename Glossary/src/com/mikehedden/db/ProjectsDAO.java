package com.mikehedden.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mikehedden.db.conn.dbConnector;
import com.mikehedden.objects.Project;

public class ProjectsDAO {
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	
	public ArrayList<Project> getProjectList(){
		ArrayList<Project> projectList = new ArrayList<>();
		dbConnector dbConn = new dbConnector();
		conn = dbConn.getConnection();
		if(null == conn){
			// Failed to get connection
			System.out.println("Failed to get connection to db.");
			return projectList;
		}
		String sql = "SELECT * FROM projects";
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs != null){
				while(rs.next()){
					Project currentProject = new Project(rs.getInt("project_id"),
							rs.getString("project_name"), rs.getString("project_description"));
					projectList.add(currentProject);
				}
			}
		} catch (SQLException e) {
		    handleSQLException(e, conn);
		}finally{
			destroy();
		}
		return projectList;
	}
	
	public boolean insertProject(Project p){
	    // Validate Input
		if (null == p){
	        return false;
        }

        String projectName = p.getProject_name();
	    String projectDescription = p.getProject_description();

		if(null == projectName){
			System.out.println("Project Name is missing from inserted Project Object. Insert of Project Failed!");
			return false;
		}

		// Write SQL
		boolean success = false;
		String sql = "INSERT INTO projects " +
						"(project_name, project_description) " +
						"VALUES (?,?)";

		// Populate Prepared Statement and execute
		try {
			dbConnector dbConn = new dbConnector();
			conn = dbConn.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, projectName);
			ps.setString(2, projectDescription);
			ps.execute();
			success = true;
		} catch (SQLException e) {
            handleSQLException(e, conn);
		} finally{
			destroy();
		}
		return success;
	}
	
	public boolean updateProject(Project p){
	    if (null == p){
	        return false;
        }
		//TODO test
		boolean success = false;
		String sql = "UPDATE projects " +
				"SET project_name=?, " +
				"project_description=? " +
				"WHERE project_id=?";
		try {
			dbConnector dbConn = new dbConnector();
			conn = dbConn.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, p.getProject_name());
			ps.setString(2, p.getProject_description());
			ps.setInt(3, p.getProject_id());
			ps.execute();
			success = true;
		} catch (SQLException e) {
            handleSQLException(e, conn);
		} finally{
			destroy();
		}
		return success;
	}
	
	public boolean deleteProject(Project p){

	    if(null == p){
	        return false;
        }
		//TODO test
		boolean success = false;
		String sql = "DELETE FROM projects " +
				"WHERE project_id=?";
		
		try {
			dbConnector dbConn = new dbConnector();
			conn = dbConn.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, p.getProject_id());
			ps.execute();
			success = true;
		} catch (SQLException e) {
            handleSQLException(e, conn);
		} finally{
			destroy();
		}
		
		return success;
	}
	
	public void destroy(){
		try {
			if(rs != null) rs.close();
			if(ps != null) ps.close();
			if(conn != null) conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void handleSQLException(SQLException e, Connection conn){
        if(e.getMessage().contains("does not exist")){
            try {
                System.out.println("Error Message" + e.getMessage());
                dbConnector.createTables(conn);
            } catch (SQLException e2){
                e2.printStackTrace();
            }
        }else {
            e.printStackTrace();
        }
    }
}
