package com.mikehedden.ajax;

import com.mikehedden.db.ProjectsDAO;
import com.mikehedden.objects.Project;
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
 * Servlet implementation class AddProject
 */
@WebServlet("/EditProject")
public class EditProject extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditProject() {
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
		String project = null;
		String description = null;
		if(requestString != null){
			try {
				JSONObject param = (JSONObject) new JSONParser().parse(requestString);
				project_id = (param.get("project_id") != null) ? Integer.parseInt(param.get("project_id").toString()) : null;
				project = (param.get("project") != null) ? param.get("project").toString() : null;
				description = (param.get("description") != null) ?  param.get("description").toString() : null;
			} catch (ParseException e) {
				error = true;
				errorMsg = "Error setting projectId parameter.";
				e.printStackTrace();
			} catch (NullPointerException e){
				error = true;
				errorMsg = "Error while parsing parameters.";
				e.printStackTrace();
			}
		}
		
		//Setp 2: build into Word object
		Project newProject = new Project(project_id, project, description);
		
		//Step 3: pass to ProjectsDAO for insert or update
		ProjectsDAO projectsDAO = new ProjectsDAO();
		boolean success = projectsDAO.updateProject(newProject);
		if(!success){
			error = true;
			errorMsg = "Failed to update project In Database";
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
