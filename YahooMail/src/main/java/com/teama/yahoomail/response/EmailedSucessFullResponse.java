package com.teama.yahoomail.response;

import java.time.LocalDateTime;

public class EmailedSucessFullResponse {
	public String sendTo;
	public int statusCode;
	public LocalDateTime timeStamp;
	
	
	public EmailedSucessFullResponse() {
		
	}
	
	
	
	public EmailedSucessFullResponse(String sendTo, int statusCode, LocalDateTime timeStamp) {
		super();
		this.sendTo = sendTo;
		this.statusCode = statusCode;
		this.timeStamp = timeStamp;
	}



	public String getSendTo() {
		return sendTo;
	}
	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}
	
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	
}
