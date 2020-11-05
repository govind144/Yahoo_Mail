package com.teama.yahoomail.controller;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.teama.yahoomail.entity.UserDetail;
import com.teama.yahoomail.exception.EmailAlreadyUsedException;
import com.teama.yahoomail.exception.EmailFormattingException;
import com.teama.yahoomail.response.UserSucessFullyAdmited;
import com.teama.yahoomail.service.UserDetailService;
import com.teama.yahoomail.utilities.MailUtility;


@RestController
public class UserDetailController {
	
	@Autowired
	private UserDetailService userDetailService;

	@Autowired
	private MailUtility mailUtility;
	
	ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	Validator validator = factory.getValidator();

	
	@GetMapping("/users")
	public List<UserDetail> getAllUsers(){
		return userDetailService.getAllUsers();
	}
	
	@PostMapping("/signup")
	public ResponseEntity<UserSucessFullyAdmited> addUser(@RequestBody UserDetail userDetail) {
		String userId;
		findViolation(userDetail);
		if(userDetailService.findByEmail(userDetail.getEmail())) {
			throw new EmailAlreadyUsedException("The email--"+userDetail.getEmail()+"--already exists.use another mail");
		}
		userId = userDetailService.addUser(userDetail);
		mailUtility.authenticationMail(userId, userDetail.getEmail());
		UserSucessFullyAdmited msg = new UserSucessFullyAdmited(userDetail.getName(),true,HttpStatus.OK.value(),userDetail.getEmail());
		
		return new ResponseEntity<>(msg,HttpStatus.OK);
	}
	
	private void findViolation(UserDetail userDetail) {
		Set<ConstraintViolation<UserDetail>> violations = validator.validate(userDetail);
		
		if(violations.size() !=0) {
			throw new EmailFormattingException("Not a valid email ---"+userDetail.getEmail());
		}
	}
	
	
}
