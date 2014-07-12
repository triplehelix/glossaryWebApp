package com.mikehedden.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.mikehedden.db.WordsDAO;
import com.mikehedden.objects.Word;

/**
 * Servlet implementation class GetWords
 */
@WebServlet("/GetWords")
public class GetWords extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetWords() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* Step1: get wordList from database
		 * 	   2: make into JSON array
		 *     3: output JSON object with JSON array
		*/
		//parameters
		String requestString = request.getParameter("params");
		System.out.println("DEBUG: requestString = " + requestString);
		int projectId = -1;
		if(request != null){
			try {
				JSONObject param =  (JSONObject) new JSONParser().parse(requestString);
				projectId = Integer.parseInt(param.get("project_id").toString());				
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		boolean error = false;
		String errorMsg = "";
		
		//Step 1:
		WordsDAO word = new WordsDAO();
		ArrayList<Word> wordsList = word.getWordList(projectId);
		
		//Step 2:
		JSONArray wordListJSON = new JSONArray();
		for(Word w : wordsList){
			wordListJSON.add(w.toJSONString());
		}
		JSONObject obj = new JSONObject();
		obj.put("error", error);
		obj.put("errorMsg", errorMsg);
		obj.put("wordList", wordListJSON);
		
		//Step 3:
		PrintWriter out = response.getWriter();
		out.println(obj);
		
	}

}
