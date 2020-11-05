package com.teama.yahoomail.response;

public class AdminSucessfullyAdmited {
	public String name;
	public boolean saved;
	public int statusCode;
	
	public AdminSucessfullyAdmited() {
		
	}
	
	
	
	public AdminSucessfullyAdmited(String name, boolean saved, int statusCode) {
		super();
		this.name = name;
		this.saved = saved;
		this.statusCode = statusCode;
	}



	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isSaved() {
		return saved;
	}
	public void setSaved(boolean saved) {
		this.saved = saved;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	
}
