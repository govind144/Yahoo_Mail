package com.teama.yahoomail.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Set;

import javax.mail.MessagingException;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.teama.yahoomail.entity.AdminEmailRecord;
import com.teama.yahoomail.entity.Email;
import com.teama.yahoomail.exception.EmailFormattingException;
import com.teama.yahoomail.exception.KeyNotValidException;
import com.teama.yahoomail.response.EmailedAllUserResponse;
import com.teama.yahoomail.response.EmailedSucessFullResponse;
import com.teama.yahoomail.service.AdminService;
import com.teama.yahoomail.utilities.MailUtility;
import com.teama.yahoomail.utilities.UserUtility;


@RestController
public class MailController {
	
	@Autowired
	private MailUtility mailUtility;
	
	@Autowired
	private UserUtility userUtility;
	
	@Autowired
	private AdminService adminService;
	
	ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	Validator validator = factory.getValidator();
	
	
	
	@PostMapping("/api/{apiKey}/send")
	public void sendMail(  @RequestBody Email theMail,			
			@PathVariable("apiKey") String apiKey
			) throws MessagingException, IOException 
	{		
		if(userUtility.isValidUser(apiKey)) 
		{			
			System.out.println(theMail);
			mailUtility.sendMail(theMail);
		}	
	}
	
			
	@PostMapping("/api/v2/{id}/send")
	public void sendAttachedMail
	(
			@PathVariable("id") String id,
			@RequestPart("file") MultipartFile file,
			@RequestParam("subject") String theSubject,
			@RequestParam("text") String theText ,
			@RequestParam("sendTo") String sendTo,
			AdminEmailRecord email) 
			throws MessagingException, IOException 
	{
		if(adminService.isAdmin(id)) 
		{
			Email theMail = new Email();
			theMail.setSendTo(sendTo);
			theMail.setSubject(theSubject);
			theMail.setText(theText);	
			findViolation(theMail);
			mailUtility.sendEmailWithAttachment(theMail, file);
		}
		
	}
	
	@PostMapping("/api/v2/{id}/send/all")
	public void sendAttachedMailToAll(
			@PathVariable("id") String id,@RequestPart("file") MultipartFile file,
			@RequestParam("subject") String theSubject,
			@RequestParam("text") String theText
			) throws MessagingException, IOException {
		if(adminService.isAdmin(id)) {
			Email theMail = new Email();			
			theMail.setSubject(theSubject);
			theMail.setText(theText);
			findViolation(theMail);
			mailUtility.sendMailToAllUserAttachement(theMail, file);
			
		}
	}
	
	@PostMapping("/api/v3/send")
	public ResponseEntity<EmailedSucessFullResponse> sendUserMail(@Valid  @RequestBody Email theMail,			
			@RequestHeader("apiKey") String apiKey
			) throws MessagingException, IOException 
	{		
		findViolation(theMail);
		if(userUtility.isValidUser(apiKey)) 
		{			
			System.out.println(theMail);
			System.out.println(apiKey);
			//mailUtility.sendMail(theMail);
		} else {
			throw new KeyNotValidException("Not a valid key -"+apiKey);
		}
		
		EmailedSucessFullResponse msg = new EmailedSucessFullResponse(theMail.getSendTo(), HttpStatus.OK.value(), LocalDateTime.now());
		
		
		return new ResponseEntity<>(msg,HttpStatus.OK);
	}
	
	@PostMapping("/api/admin/v3/send")
	public ResponseEntity<EmailedSucessFullResponse> sendAttachedMailV3
	(
			@RequestPart("file") MultipartFile file,
			@RequestParam("subject") String theSubject,
			@RequestParam("text") String theText ,
			@RequestParam("sendTo") String sendTo,
			@RequestHeader("apiKey") String apiKey) 
			throws MessagingException, IOException 
	{
		Email theMail = new Email();
		if(adminService.isAdmin(apiKey)) 
		{
			
			theMail.setSendTo(sendTo);
			theMail.setSubject(theSubject);
			theMail.setText(theText);
			findViolation(theMail);
			System.out.println(theMail);
			mailUtility.sendEmailWithAttachment(theMail, file);
			//adminemailrepository.save(email);
		} else {
			throw new KeyNotValidException("Not a valid key -"+apiKey);
		}
		EmailedSucessFullResponse msg = new EmailedSucessFullResponse(theMail.getSendTo(), HttpStatus.OK.value(), LocalDateTime.now());
		return new ResponseEntity<>(msg,HttpStatus.OK);
		
	}
	
	@PostMapping("/api/admin/v3/send/all")
	public ResponseEntity<EmailedAllUserResponse> sendAttachedMailToAllV3(
			@RequestHeader("file") MultipartFile file,
			@RequestParam("subject") String theSubject,
			@RequestParam("text") String theText,
			@RequestHeader("apiKey") String apiKey
			) throws MessagingException, IOException {
		if(adminService.isAdmin(apiKey)) {
			Email theMail = new Email();			
			theMail.setSubject(theSubject);
			theMail.setText(theText);
			mailUtility.sendMailToAllUserAttachement(theMail, file);
			
		}  else {
			throw new KeyNotValidException("Not a valid key -"+apiKey);
		}
		
		EmailedAllUserResponse msg = new EmailedAllUserResponse(HttpStatus.OK.value(), "done");
		
		return  new ResponseEntity<>(msg,HttpStatus.OK);
	}
	
	
	
	@PostMapping("/api/admin/v3/send/multi")
	public ResponseEntity<EmailedSucessFullResponse> sendAttachedMailV4WithMultiFile
	(
			@RequestPart("files") MultipartFile[] file,
			@RequestParam("subject") String theSubject,
			@RequestParam("text") String theText ,
			@RequestParam("sendTo") String sendTo,
			@RequestHeader("apiKey") String apiKey) 
			throws MessagingException, IOException 
	{
		Email theMail = new Email();
		if(adminService.isAdmin(apiKey)) 
		{
			System.out.println(file.length);
			theMail.setSendTo(sendTo);
			theMail.setSubject(theSubject);
			theMail.setText(theText);
			findViolation(theMail);
			System.out.println(theMail);
			//mailUtility.sendEmailWithAttachment(theMail, file);
			mailUtility.sendMailWithAttachMentWithMultiFileSupport(theMail, file);
			//adminemailrepository.save(email);
		} else {
			throw new KeyNotValidException("Not a valid key -"+apiKey);
		}
		EmailedSucessFullResponse msg = new EmailedSucessFullResponse(theMail.getSendTo(), HttpStatus.OK.value(), LocalDateTime.now());
		return new ResponseEntity<>(msg,HttpStatus.OK);
		
	}
	
	@PostMapping("/api/admin/v3/send/multi/all")
	public ResponseEntity<EmailedAllUserResponse> sendAttachedMailToAllV4WithMultiFile
	(   
			@RequestPart("files") MultipartFile[] file,
			@RequestParam("subject") String theSubject,
			@RequestParam("text") String theText,
			@RequestHeader("apiKey") String apiKey
			) throws MessagingException, IOException {
		if(adminService.isAdmin(apiKey)) {
			Email theMail = new Email();	
			System.out.println(file.length);
			theMail.setSubject(theSubject);
			theMail.setText(theText);
			mailUtility.sendMailToAllWithMultiFileSupport(theMail, file);
			
		}  else {
			throw new KeyNotValidException("Not a valid key -"+apiKey);
		}
		
		EmailedAllUserResponse msg = new EmailedAllUserResponse(HttpStatus.OK.value(), "done");
		
		return  new ResponseEntity<>(msg,HttpStatus.OK);
	}
	
	private void findViolation(Email email) {
		Set<ConstraintViolation<Email>> violations = validator.validate(email);
		
		if(violations.size() !=0) {
			throw new EmailFormattingException("Not a valid email ---"+email.getSendTo());
		}
	}
}
