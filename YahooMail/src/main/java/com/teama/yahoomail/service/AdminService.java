package com.teama.yahoomail.service;

import com.teama.yahoomail.entity.Admin;


public interface AdminService {
	

	boolean isAdmin(String id);

	String saveAdmin(Admin admin);
	
	boolean isAdminMail(String mail);

}	
