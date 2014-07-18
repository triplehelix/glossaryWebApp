package com.mikehedden.objects;

import org.json.simple.JSONObject;

public class Word {

	int word_id, project_id;
	String word, definition, notes;
	
	
	public Word(int word_id, int project_id, String word, String definition,
			String notes) {
		super();
		this.word_id = word_id;
		this.project_id = project_id;
		this.word = word;
		this.definition = definition;
		this.notes = notes;
	}
	
	public Word(int project_id, String word, String definition,
			String notes) {
		super();
		this.project_id = project_id;
		this.word = word;
		this.definition = definition;
		this.notes = notes;
	}
	
	public String toJSONString(){
		JSONObject obj = new JSONObject();
		obj.put("word_id", getWord_id());
		obj.put("project_id", getProject_id());
		obj.put("word", getWord());
		obj.put("definition", getDefinition());
		obj.put("notes", getNotes());
		return obj.toJSONString();		
	}
	public int getWord_id() {
		return word_id;
	}
	public void setWord_id(int word_id) {
		this.word_id = word_id;
	}
	public int getProject_id() {
		return project_id;
	}
	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getDefinition() {
		return definition;
	}
	public void setDefinition(String definition) {
		this.definition = definition;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
}
