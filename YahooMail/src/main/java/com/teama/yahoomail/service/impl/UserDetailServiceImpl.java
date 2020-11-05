package com.teama.yahoomail.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teama.yahoomail.entity.Email;
import com.teama.yahoomail.entity.UserDetail;
import com.teama.yahoomail.repository.UserDetailRepository;
import com.teama.yahoomail.service.UserDetailService;
import com.teama.yahoomail.utilities.MailUtility;


@Service
public class UserDetailServiceImpl implements UserDetailService {
	
	@Autowired
	private UserDetailRepository userDetailRepository;
	
	@Autowired
	private MailUtility mailUtility;
	
	@Transactional
	@Override
	public List<UserDetail> getAllUsers() {
		List<UserDetail> userDetails = new ArrayList<>();
		  userDetailRepository.findAll().forEach(userDetails::add);
		  
		  return userDetails;
	}
	
	@Transactional
	@Override
	public UserDetail getUser(String userId) {
		return userDetailRepository.findById(userId).get();
	}

	@Transactional
	@Override
	public String addUser(UserDetail entity) {
		userDetailRepository.save(entity);
		System.out.println("saving this-"+entity);
		return entity.getUserId();
		
	}
	
	@Transactional
	public void sendToAllUser(Email email) {
		List<UserDetail> userDetails = new ArrayList<>();
		userDetailRepository.findAll().forEach(userDetails::add);
		
		for (UserDetail userDetail : userDetails) {
			mailUtility.sendUserMail(userDetail, email);
		}
	}
	
	@Transactional
	@Override
	public boolean findByEmail(String email) {
		UserDetail userDetail = userDetailRepository.findByEmail(email);
		return userDetail != null;
	}
	
	@Transactional
	@Override
	public List<UserDetail> findAllUserWithBirthDay(){
		return userDetailRepository.usersWithBirthDayToday();
	}

}