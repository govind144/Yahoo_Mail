package com.teama.yahoomail.utilities;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.teama.yahoomail.entity.Email;
import com.teama.yahoomail.entity.UserDetail;
import com.teama.yahoomail.exception.AuthFaliedException;
import com.teama.yahoomail.exception.EmailAlreadyUsedException;
import com.teama.yahoomail.exception.GmailServerErrorException;
import com.teama.yahoomail.service.AdminService;
import com.teama.yahoomail.service.UserDetailService;

@Component
public class MailUtility 

{
	@Autowired
	private JavaMailSender sender;
	
	@Autowired
	private UserDetailService userDetailService;
	
	@Autowired
	private AdminService adminService;
	
	public String sendMail(Email theMail) 
	{
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		try 
		{
			helper.setTo(theMail.getSendTo());
			helper.setText(theMail.getText());
			helper.setSubject(theMail.getSubject());
		} catch (MessagingException e) {
			System.out.println("cannot send mail to"+theMail.getSendTo());
			return "Error While Sending Mail ....";
		}
		sender.send(message);
		return "Mail Sent Success Congratulations!";

	}

	
	public String authenticationMail(String userId, String userMail) 
	
	{
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		String authMessage = "Hey! Thanks For Signing Up Please Use Userid As Your API KEY-" + userId;

		try 
		{
			helper.setTo(userMail);
			helper.setText(authMessage);
			helper.setSubject("Your Authentication Number From Team A Please Keep It Secret");
		} catch (MessagingException e) 
		
		{
			e.printStackTrace();
			return "Error While Sending Mail ....";
		}
		
		sender.send(message);
		System.out.println("You Have Recived Message Check Your Mail Inbox");
		return "Mail Sent Success!";
	}

	
	public String birthDayMail(UserDetail userDetail)  {
		MimeMessage message;
		try {
			message = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(userDetail.getEmail());
			helper.setText("Wishing You A Very Happy Birthday Stay Blessed.");
			helper.setSubject("Happy BirthDay !!! " + userDetail.getName());
			ClassPathResource file = new ClassPathResource("download.PNG");
			helper.addAttachment("download.PNG", file);
		} catch (MessagingException e) {
			System.out.println("cannot send birthday message"+userDetail.getEmail());
			return "Error While Sending Mail ..";
		}
		sender.send(message);
		return "Mail Sent Success!";
	}
	

	public String sendUserMail(UserDetail userDetail, Email theMail) {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		try {
			helper.setTo(userDetail.getEmail());
			helper.setText(theMail.getText());
			helper.setSubject(theMail.getSubject());
		} catch (MessagingException e) {
			e.printStackTrace();
			return "Error While Sending Mail ..";
		}
		sender.send(message);
		return "Mail Sent Success!";
	}

	public void sendEmailWithAttachment(Email theMail, MultipartFile multipartFile)
			throws  IOException {

		System.out.println("another one");
		try {
			MimeMessage msg = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true);
			
			
			
			helper.setTo(theMail.getSendTo());
			helper.setSubject(theMail.getSubject());
			helper.setText(theMail.getText());

			
			InputStreamSource attachment = new ByteArrayResource(multipartFile.getBytes());
			if (!multipartFile.isEmpty()) {
				helper.addAttachment(multipartFile.getOriginalFilename(), attachment);
			}
			 sender.send(msg);
		}  catch(MailAuthenticationException e) {
			throw new AuthFaliedException("Your gmail id and password is wrong");
		} catch(MessagingException e) {
			System.out.println("jamie");
			throw new GmailServerErrorException("Email cannot be sent check connection or gmail server error");
		}

	}
	public void sendMailToAllUserAttachement(Email theMail,MultipartFile multipartFile) 
			throws MessagingException, IOException {
		List<UserDetail> listOfAllUser = userDetailService.getAllUsers();
		
		
		
		for (UserDetail userDetail : listOfAllUser) {
			theMail.setSendTo(userDetail.getEmail());
			//System.out.println("sending mail with attachment-"+userDetail.getEmail());
			sendEmailWithAttachment(theMail, multipartFile);
		}
	}
	
      public void checkMail(String mail) {
		
		boolean exist = userDetailService.findByEmail(mail);
		
		System.out.println("=====================================");
		if (exist) {
			throw new EmailAlreadyUsedException("your email has been used");
		}
	}
      
      public void checkAdminMail(String mail) {
  		
  		boolean exist = adminService.isAdmin(mail);
  		
  		System.out.println("=====================================");
  		if (exist) {
  			throw new EmailAlreadyUsedException("your email has been used");
  		}
  	}
      
    public void sendMailWithAttachMentWithMultiFileSupport(Email theMail,MultipartFile[] files ) throws IOException {
    	try {
			MimeMessage msg = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true);
			
			
			
			helper.setTo(theMail.getSendTo());
			helper.setSubject(theMail.getSubject());
			helper.setText(theMail.getText());

			for (MultipartFile file : files) {
				if(!file.isEmpty()) {
					InputStreamSource attachment = new ByteArrayResource(file.getBytes());
					helper.addAttachment(file.getOriginalFilename(), attachment);
				}
			}
			 sender.send(msg);
		}  catch(MailAuthenticationException e) {
			throw new AuthFaliedException("Your gmail id and password is wrong");
		} catch(MessagingException e) {
			throw new GmailServerErrorException("Email cannot be sent check connection or gmail server error");
		}
    }


	public void sendMailToAllWithMultiFileSupport(Email theMail, MultipartFile[] file) 
			throws MessagingException, IOException{
		
List<UserDetail> listOfAllUser = userDetailService.getAllUsers();
		
		
		
		for (UserDetail userDetail : listOfAllUser) {
			theMail.setSendTo(userDetail.getEmail());
			System.out.println("sending mail with attachment-"+userDetail.getEmail());
			sendMailWithAttachMentWithMultiFileSupport(theMail, file);
		}
		for (MultipartFile file1 : file) {
			if(!file1.isEmpty()) {
				InputStreamSource attachment = new ByteArrayResource(file1.getBytes());
			
			}
		}
	}

}
