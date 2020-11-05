package com.teama.yahoomail.config;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.sun.mail.util.MailConnectException;
import com.teama.yahoomail.exception.AuthFaliedException;
import com.teama.yahoomail.exception.EmailAlreadyUsedException;
import com.teama.yahoomail.exception.EmailFormattingException;
import com.teama.yahoomail.exception.GmailServerErrorException;
import com.teama.yahoomail.exception.KeyNotValidException;
import com.teama.yahoomail.exception.MailException;
import com.teama.yahoomail.exception.ResourceNotFoundException;

@ControllerAdvice
public class MailHandler {

	@ExceptionHandler
	public ResponseEntity<MailException> handleEmailAlreadyUsed(EmailAlreadyUsedException exc) {

		MailException error = new MailException();
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage("This Mail Already Exists");
		error.setTimeStamp(System.currentTimeMillis());

		return new ResponseEntity<MailException>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public ResponseEntity<MailException> internetNotConnected(MailConnectException exc) {

		MailException error = new MailException();
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage("Please connect to internet to get email services");
		error.setTimeStamp(System.currentTimeMillis());

		return new ResponseEntity<MailException>(error, HttpStatus.BAD_REQUEST);
	}

	

	@ExceptionHandler
	public ResponseEntity<MailException> emailFormattingIsWrong(EmailFormattingException exc) {
		MailException error = new MailException();
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage(exc.getMessage());
		error.setTimeStamp(System.currentTimeMillis());

		return new ResponseEntity<MailException>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public ResponseEntity<MailException> IORelated(IOException exc) {

		MailException error = new MailException();
		error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.setMessage("Looks like something is missing please use our service a bit later");
		error.setTimeStamp(System.currentTimeMillis());

		return new ResponseEntity<MailException>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler
	public ResponseEntity<MailException> resourceNotFound(ResourceNotFoundException exc) {

		MailException error = new MailException();
		error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.setMessage("Looks like something is missing please use our service a bit later");
		error.setTimeStamp(System.currentTimeMillis());

		return new ResponseEntity<MailException>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler
	public ResponseEntity<MailException> AuthExc(KeyNotValidException exc) {

		MailException error = new MailException();
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage(exc.getMessage());
		error.setTimeStamp(System.currentTimeMillis());

		return new ResponseEntity<MailException>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler
	public ResponseEntity<MailException> GmailException(GmailServerErrorException exc) {

		MailException error = new MailException();
		error.setStatus(HttpStatus.CONFLICT.value());
		error.setMessage(exc.getMessage());
		error.setTimeStamp(System.currentTimeMillis());

		return new ResponseEntity<MailException>(error, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler
	public ResponseEntity<MailException> AuthenticationFalied(AuthFaliedException exc) {

		MailException error = new MailException();
		error.setStatus(HttpStatus.CONFLICT.value());
		error.setMessage(exc.getMessage());
		error.setTimeStamp(System.currentTimeMillis());

		return new ResponseEntity<MailException>(error, HttpStatus.CONFLICT);
	}

}
