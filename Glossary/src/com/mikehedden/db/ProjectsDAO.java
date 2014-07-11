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
	
	public boolean insertProject(){
		//TODO
		
		return false;
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
