package com.teama.yahoomail.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "USER_SIGNUP_DETAILS")
public class UserDetail {
	
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "USER_ID")
	private String userId;
	
	@Column(name = "NAME")
	private String name;
	
	@Email(message = "Please provide an email")
	@Column(name = "EMAIL_ID")
	private String email;

	@Column(name = "BIRTHDAY")
	private LocalDate birthDay;
	
	@Column(name = "STATUS")
	private boolean status;


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public LocalDate getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(LocalDate birthDay) {
		this.birthDay = birthDay;
	}

	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "UserDetail [userId=" + userId + ", name=" + name + ", email=" + email + ", birthDay=" + birthDay
				+ ", status=" + status + "]";
	}
	
	

		
}
