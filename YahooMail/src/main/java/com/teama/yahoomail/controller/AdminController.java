package com.teama.yahoomail.controller;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.teama.yahoomail.entity.Admin;
import com.teama.yahoomail.exception.EmailFormattingException;
import com.teama.yahoomail.response.AdminSucessfullyAdmited;
import com.teama.yahoomail.service.AdminService;
import com.teama.yahoomail.utilities.MailUtility;

@RestController
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private MailUtility mailUtility;
	
	ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	Validator validator = factory.getValidator();
	
	
	
	@PostMapping("/admin/signup")
	public ResponseEntity<AdminSucessfullyAdmited> adminSignUp(@RequestBody Admin admin,Errors errors) {
		if(errors.hasFieldErrors("email")) {
			throw new EmailFormattingException("Not a valid mail ---"+admin.getEmail());
		}
		findViolation(admin);
		adminService.isAdminMail(admin.getEmail());
		String adminId = adminService.saveAdmin(admin);
		
		mailUtility.authenticationMail(adminId, admin.getEmail());
		AdminSucessfullyAdmited msg = new AdminSucessfullyAdmited(admin.getName(),true,HttpStatus.OK.value());
		
		return new ResponseEntity<>(msg,HttpStatus.OK);
	}
	
	private void findViolation(Admin admin) {
		Set<ConstraintViolation<Admin>> violations = validator.validate(admin);
		
		if(violations.size() !=0) {
			throw new EmailFormattingException("Not a valid email ---"+admin.getEmail());
		}
	}
}
