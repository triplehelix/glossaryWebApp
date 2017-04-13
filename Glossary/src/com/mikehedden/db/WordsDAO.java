/*
 * Words Data Access Object
 * by: Mike Hedden
 * Date: 2014-07-11
 */

package com.mikehedden.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mikehedden.objects.Word;
import com.mikehedden.db.conn.dbConnector;

public class WordsDAO {
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	
	public ArrayList<Word> getWordList(int projectId){
		ArrayList<Word> wordList = new ArrayList<>();
		dbConnector dbConn = new dbConnector();
		conn = dbConn.getConnection();
		if(null == conn){
			// Failed to get connection
			System.out.println("Failed to get connection to db.");
			return wordList;
		}
		if(projectId >= 0){
			//use inputed projectID
			String sql = "SELECT * FROM words WHERE project_id=? ORDER BY word ASC";
			try {
				ps = conn.prepareStatement(sql);
				ps.setInt(1, projectId);
				rs = ps.executeQuery();
				if(rs != null){
					while(rs.next()){
						Word currentWord = new Word(rs.getInt("words_id"), rs.getInt("project_id"),
								rs.getString("word"), rs.getString("definition"), rs.getString("notes"));
						wordList.add(currentWord);
					}
				}
			} catch (SQLException e) {
                handleSQLException(e, conn);
			}finally{
				destroy();
			}
			
		}else{
			//no projectID specified, display all
			String sql = "SELECT * FROM words ORDER BY word ASC";
			try {
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				if(rs != null){
					while(rs.next()){
						Word currentWord = new Word(rs.getInt("words_id"), rs.getInt("project_id"),
								rs.getString("word"), rs.getString("definition"), rs.getString("notes"));
						wordList.add(currentWord);
					}
				}
			} catch (SQLException e) {
                handleSQLException(e, conn);
			}finally{
				destroy();
			}
		}
		return wordList;
	}
	
	public boolean insertWord(Word insertWord){
		boolean success = false;
		String sql = "INSERT INTO words " +
						"(word, definition, notes, project_id) " +
						"VALUES (?,?,?,?)";
		try {
			dbConnector dbConn = new dbConnector();
			conn = dbConn.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, insertWord.getWord());
			ps.setString(2, insertWord.getDefinition());
			ps.setString(3, insertWord.getNotes());
			ps.setInt(4, insertWord.getProject_id());
			ps.execute();
			success = true;
		} catch (SQLException e) {
            handleSQLException(e, conn);
		} finally{
			destroy();
		}
		return success;
	}
	
	public boolean updateWord(Word updateWord){
		boolean success = false;
		String sql = "UPDATE words " +
				"SET word=?, " +
				"definition=?, " +
				"notes=?, " + 
				"project_id=? " +
				"WHERE words_id=?";
		try {
			dbConnector dbConn = new dbConnector();
			conn = dbConn.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, updateWord.getWord());
			ps.setString(2, updateWord.getDefinition());
			ps.setString(3, updateWord.getNotes());
			ps.setInt(4, updateWord.getProject_id());
			ps.setInt(5, updateWord.getWord_id());
			ps.execute();
			success = true;
		} catch (SQLException e) {
            handleSQLException(e, conn);
		} finally{
			destroy();
		}
		
		return success;
	}
	
	public boolean deleteWord(Word deleteWord){
		//TODO test
		boolean success = false;
		String sql = "DELETE FROM words " +
				"WHERE words_id=?";
		try {
			dbConnector dbConn = new dbConnector();
			conn = dbConn.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, deleteWord.getWord_id());
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
                dbConnector.createTables(conn);
            } catch (SQLException e2){
                e2.printStackTrace();
            }
        }else {
            e.printStackTrace();
        }
    }
}
