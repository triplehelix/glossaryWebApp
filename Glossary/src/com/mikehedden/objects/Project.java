package com.mikehedden.objects;

import org.json.simple.JSONObject;

public class Project {
	int project_id;
	String project_name, project_description;
	
	public Project(int project_id, String project_name,
			String project_description) {
		super();
		this.project_id = project_id;
		this.project_name = project_name;
		this.project_description = project_description;
	}
	
	public String toJSONString(){
		JSONObject obj = new JSONObject();
		obj.put("project_id", getProject_id());
		obj.put("project_name", getProject_name());
		obj.put("project_description", getProject_description());
		return obj.toJSONString();		
	}
	
	public int getProject_id() {
		return project_id;
	}
	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}
	public String getProject_name() {
		return project_name;
	}
	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	public String getProject_description() {
		return project_description;
	}
	public void setProject_description(String project_description) {
		this.project_description = project_description;
	}

}
