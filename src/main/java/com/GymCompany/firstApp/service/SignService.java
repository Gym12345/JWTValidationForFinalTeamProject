package com.GymCompany.firstApp.service;

import com.GymCompany.firstApp.model.SignInResultDTO;
import com.GymCompany.firstApp.model.SignUpResultDTO;


public interface SignService {
	SignUpResultDTO signUp(String userId, String userPw, String userName, String role, String email, String phone, int age, String gender,
				String profileUrl,
			 	String profileName,
		        String userSize,
		        String userBio,
		        String receiveEmail,
		        String receiveMessage,
		        String blockedProfiles,
		        String favoriteProducts);
	
	
	SignInResultDTO signIn(String userId,String userPw);
	
	int redundancyCheck(String userId);
}
