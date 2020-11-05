package com.teama.yahoomail.response;

public class UserSucessFullyAdmited {

	public String name;
	public boolean saved;
	public int statusCode;
	public String email;
	
	public UserSucessFullyAdmited() {
		
	}
	
	
	
	public UserSucessFullyAdmited(String name, boolean saved, int statusCode, String email) {
		super();
		this.name = name;
		this.saved = saved;
		this.statusCode = statusCode;
		this.email = email;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
