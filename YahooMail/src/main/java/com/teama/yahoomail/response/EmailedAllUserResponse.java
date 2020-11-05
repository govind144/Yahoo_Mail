package com.teama.yahoomail.response;

public class EmailedAllUserResponse {
	private int status;
	private String sent;

	public EmailedAllUserResponse() {

	}

	public EmailedAllUserResponse(int status, String sent) {
		super();
		this.status = status;
		this.sent = sent;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getSent() {
		return sent;
	}

	public void setSent(String sent) {
		this.sent = sent;
	}

}
