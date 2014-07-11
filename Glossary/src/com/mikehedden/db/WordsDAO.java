package com.mikehedden.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mikehedden.db.conn.dbConnector;
import com.mikehedden.objects.Word;

public class WordsDAO {
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public ArrayList<Word> getWordList(int projectId){
		ArrayList<Word> wordList = new ArrayList<>();
		dbConnector dbConn = new dbConnector();
		conn = dbConn.getConnection();
		if(projectId >= 0){
			//use inputed projectID
			String sql = "SELECT * FROM `words` WHERE project_id=?;";
			try {
				ps = conn.prepareStatement(sql);
				ps.setInt(1, projectId);
				rs = ps.executeQuery();
				if(rs != null){
					while(rs.next()){
						Word currentWord = new Word(rs.getInt("word_id"), rs.getInt("project_id"),
								rs.getString("word"), rs.getString("definition"), rs.getString("notes"));
						wordList.add(currentWord);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				destroy();
			}
			
		}else{
			//no projectID specified, display all
			String sql = "SELECT * FROM `words`;";
			try {
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				if(rs != null){
					while(rs.next()){
						Word currentWord = new Word(rs.getInt("word_id"), rs.getInt("project_id"),
								rs.getString("word"), rs.getString("definition"), rs.getString("notes"));
						wordList.add(currentWord);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				destroy();
			}
		}
		return wordList;
	}
	
	public boolean insertWord(){
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
