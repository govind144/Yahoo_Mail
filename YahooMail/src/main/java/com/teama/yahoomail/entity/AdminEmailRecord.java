package com.teama.yahoomail.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "ADMIN_EMAIL_RECORDS")
public class AdminEmailRecord 
{

	@Id
	@Column(name = "SEND_TO")
	private String sendTo;
	
	@Column(name = "SUBJECT")
	private String subject;
	
	@Column(name = "MESSAGE")
	private String text;
	
	
	public AdminEmailRecord() 
	{
		
	}
	public String getSendTo() 
	{
		return sendTo;
	}

	public AdminEmailRecord(String sendTo, String subject, String text) {
		super();
		this.sendTo = sendTo;
		this.subject = subject;
		this.text = text;
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
		return "AdminEmailRecord [sendTo=" + sendTo + ", subject=" + subject + ", text=" + text + "]";
	}

}
