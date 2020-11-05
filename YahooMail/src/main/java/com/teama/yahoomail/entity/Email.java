package com.teama.yahoomail.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER_EMAIL_RECORDS")
public class Email 
{
	
	@Id
	@Column(name = "SEND_TO")
	@javax.validation.constraints.Email(message = "please provide a proper email")
	private String sendTo;
	
	@Column(name = "SUBJECT")	
	private String subject;
	
	@Column(name = "MESSAGE")
	private String text;

	
	public Email() 
	{
		
	}
	public Email(String sendTo, String subject, String text) 
	
	{
		super();
		this.sendTo = sendTo;
		this.subject = subject;
		this.text = text;
	
	}
	public String getSendTo() {
		return sendTo;
	}

	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "Email [sendTo=" + sendTo + ", subject=" + subject + ", text=" + text + "]";
	}
		
	}
	
	
