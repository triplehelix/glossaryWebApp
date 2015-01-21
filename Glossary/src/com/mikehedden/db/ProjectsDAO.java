package com.mikehedden.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mikehedden.db.conn.dbConnector;
import com.mikehedden.objects.Project;

public class ProjectsDAO {
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public ArrayList<Project> getProjectList(){
		ArrayList<Project> projectList = new ArrayList<>();
		dbConnector dbConn = new dbConnector();
		conn = dbConn.getConnection();
		String sql = "SELECT * FROM `projects`;";
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
			e.printStackTrace();
		}finally{
			destroy();
		}
		return projectList;
	}
	
	public boolean insertProject(Project p){
		boolean success = false;
		String sql = "INSERT INTO `projects` " + 
						"(project_name, project_description) " +
						"VALUES (?,?);";
		try {
			dbConnector dbConn = new dbConnector();
			conn = dbConn.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, p.getProject_name());
			ps.setString(2, p.getProject_description());
			ps.execute();
			success = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			destroy();
		}
		return success;
	}
	
	public boolean updateProject(Project p){
		//TODO test
		boolean success = false;
		String sql = "UPDATE `projects` " + 
				"SET project_name=?, " +
				"project_description=? "
				"WHERE project_id=?;";
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
			e.printStackTrace();
		} finally{
			destroy();
		}
		return success;
	}
	
	public boolean deleteProject(Project p){
		//TODO test
		boolean success = false;
		String sql = "DELETE FROM `projects` " + 
				"WHERE project_id=?;";
		
		try {
			dbConnector dbConn = new dbConnector();
			conn = dbConn.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, p.getProject_id());
			ps.execute();
			success = true;
		} catch (SQLException e) {
			e.printStackTrace();
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
}
