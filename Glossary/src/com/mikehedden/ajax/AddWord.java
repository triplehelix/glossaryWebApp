package com.mikehedden.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mikehedden.db.WordsDAO;
import com.mikehedden.objects.Word;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Servlet implementation class AddWord
 */
@WebServlet("/AddWord")
public class AddWord extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddWord() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean error = false;
		String errorMsg = "no error";
		
		//Step 1: Handle request parameters
		String requestString = request.getParameter("params");
		int project_id = -1;
		String word = null;
		String definition = null;
		String notes = null;
		if(requestString != null){
			try {
				//TODO create null checks like on definition
				JSONObject param =  (JSONObject) new JSONParser().parse(requestString);
				project_id = (param.get("project_id") != null) ? Integer.parseInt(param.get("project_id").toString()) : -2;	
				word = (param.get("word") != null) ? param.get("word").toString() : null;
				definition = (param.get("definition") != null) ?  param.get("definition").toString() : null;
				notes = (param.get("notes") != null) ? param.get("notes").toString() : null;
			} catch (ParseException e) {
				error = true;
				errorMsg = "Error setting projectId parameter.";
				e.printStackTrace();
			}
		}
		
		if(project_id == -2 || word == null || definition == null || notes == null){
			error = true;
			errorMsg = "There was a problem with the form inputs.";
		}else{
			
		//Step 2: build into Word object
			Word newWord = new Word(project_id, word, definition, notes);
			
		//Step 3: pass to WordsDAO for insert or update
			WordsDAO wordsDAO = new WordsDAO();
			boolean success = wordsDAO.insertWord(newWord);
			if(!success){
				error = true;
				errorMsg = "Failed to insert Into Database";
			}
		}
		
		//Step 4: return success or error via JSON.
		JSONObject obj = new JSONObject();
		obj.put("error", error);
		obj.put("errorMsg", errorMsg);
		
		//Step 5: output
		PrintWriter out = response.getWriter();
		out.println(obj);
	}

}
