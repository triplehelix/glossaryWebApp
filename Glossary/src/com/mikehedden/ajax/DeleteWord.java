package com.mikehedden.ajax;

import com.mikehedden.db.WordsDAO;
import com.mikehedden.objects.Word;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class AddWord
 */
@WebServlet("/DeleteWord")
public class DeleteWord extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteWord() {
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
		int word_id = -1;
		if(requestString != null){
			try {
				//TODO create null checks like on definition
				JSONObject param =  (JSONObject) new JSONParser().parse(requestString);
				word_id = (param.get("word_id") != null) ? Integer.parseInt(param.get("word_id").toString()) : -2;
			} catch (ParseException e) {
				error = true;
				errorMsg = "Error setting wordId parameter.";
				e.printStackTrace();
			}
		}
		
		if(word_id == -2){
			error = true;
			errorMsg = "There was a problem with word_id input.";
		}else{
			
		//Step 2: build into Word object
			Word newWord = new Word(word_id, -1, null, null, null);
			
		//Step 3: pass to WordsDAO for insert or update
			WordsDAO wordsDAO = new WordsDAO();
			boolean success = wordsDAO.deleteWord(newWord);
			if(!success){
				error = true;
				errorMsg = "Failed to delete from Database.";
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
