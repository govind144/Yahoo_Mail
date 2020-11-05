package com.teama.yahoomail.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.teama.yahoomail.entity.Admin;
import com.teama.yahoomail.repository.AdminRepository;
import com.teama.yahoomail.service.AdminService;

@Service
public class AdminServiceimpl implements AdminService {
	
	@Autowired
	private AdminRepository adminRepository;
	
	
	@Transactional
	@Override
	public boolean isAdmin(String id) {
		return adminRepository.findById(id).isPresent();
	}
	
	@Transactional
	@Override
	public String saveAdmin(Admin admin) {
		adminRepository.save(admin);
		
		return admin.getUserId();
	}
	
	@Transactional
	@Override
	public boolean isAdminMail(String mail) {
		return adminRepository.findByEmail(mail) != null;
	}


}
