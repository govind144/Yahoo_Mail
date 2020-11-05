package com.teama.yahoomail.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.teama.yahoomail.entity.Email;
import com.teama.yahoomail.entity.UserDetail;

@Service
public interface UserDetailService {
	
	public List<UserDetail> getAllUsers();
	public UserDetail getUser(String userId);
	public String addUser(UserDetail userDetail);
	public void sendToAllUser(Email email);
	public boolean findByEmail(String mail);
	public List<UserDetail> findAllUserWithBirthDay();
}
