package com.teama.yahoomail.utilities;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.teama.yahoomail.entity.UserDetail;
import com.teama.yahoomail.repository.UserDetailRepository;
import com.teama.yahoomail.service.UserDetailService;

@Component
public class UserUtility {
	
	@Autowired
	private UserDetailRepository userDetailRepository; 
	
	@Autowired
	private UserDetailService userDetailService;
	
	@Autowired
	private MailUtility mailUtility;
	
	public boolean isValidUser(String userId) {
		return userDetailRepository.findById(userId).isPresent();
		
	}
	
 //       @Scheduled(cron = "0 15 10 * * ?")
	//@Scheduled(cron = "0 0/1 * * * *")
      @Scheduled(fixedRate = 1500000000)
	public void sendBirthDayMail() throws MessagingException {
		List<UserDetail> userWithBirthDay = userDetailRepository.usersWithBirthDayToday();
		System.out.println("-------------------- BirthDay Mail Initializing----------------------------");
		for(UserDetail user : userWithBirthDay) {
			mailUtility.birthDayMail(user);
			System.out.println("sending mail to -"+ user.getEmail());
			user.setStatus(false);
			userDetailService.addUser(user);
		}
		
		System.out.println("---------------------- BirthDay Mail Completed-----------------------------");
		
	}
	
	
	@Scheduled(cron = "0 0 1 1 * *")
	public void changeStatusOfBirthDay() {
		List<UserDetail> listOfUser = userDetailService.getAllUsers();
		
		for (UserDetail userDetail : listOfUser) {
			userDetail.setStatus(true);
			userDetailService.addUser(userDetail);
		}
	}
}
