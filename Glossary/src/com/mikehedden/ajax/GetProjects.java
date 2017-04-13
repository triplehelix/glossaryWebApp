package com.mikehedden.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mikehedden.db.ProjectsDAO;
import com.mikehedden.objects.Project;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Servlet implementation class GetProjects
 */
@WebServlet("/GetProjects")
public class GetProjects extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetProjects() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/* Step1: get projectList from database
		 * 	   2: make into JSON array
		 *     3: output JSON object with JSON array
		*/
		boolean error = false;
		String errorMsg = "";
		
		//Step 1:
		ProjectsDAO projDAO = new ProjectsDAO();
		ArrayList<Project> projectList = projDAO.getProjectList();
		
		//Step 2:
		JSONArray projectListJSON = new JSONArray();
		for(Project p : projectList){
			projectListJSON.add(p.toJSONString());
		}
		JSONObject obj = new JSONObject();
		obj.put("error", error);
		obj.put("errorMsg", errorMsg);
		obj.put("projectList", projectListJSON);
		
		//Step 3:
		PrintWriter out = response.getWriter();
		out.println(obj);
	}

}
