package com.mikehedden.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.mikehedden.db.WordsDAO;
import com.mikehedden.objects.Word;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//using post for this use-case TODO remove if POST works
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
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
				project_id = Integer.parseInt(param.get("project_id").toString());	
				word = param.get("word").toString();
				definition = (param.get("definition") != null) ?  param.get("definition").toString() : null;
				notes = param.get("notes").toString();
			} catch (ParseException e) {
				error = true;
				errorMsg = "Error setting projectId parameter.";
				e.printStackTrace();
			}
		}
		
		//Setp 2: build into Word object
		Word newWord = new Word(project_id, word, definition, notes);
		
		//Step 3: pass to WordsDAO for insert or update
		WordsDAO wordsDAO = new WordsDAO();
		boolean success = wordsDAO.insertWord(newWord);
		if(!success){
			error = true;
			errorMsg = "Failed to insert Into Database";
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
