package com.GymCompany.firstApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.GymCompany.firstApp.jwt.JwtTokenProvider;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class AdminAuthController {
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@GetMapping(value = "/aiConnectionTest") // this is basic code for loading other page or function
	public String aiConnectionTest(HttpServletRequest request) {

		String jwtToken=(String) request.getAttribute("token");   //must need 
		
		Authentication authentication = jwtTokenProvider.getAuthentication(jwtToken);    //optional but could be essential afterhand
        SecurityContextHolder.getContext().setAuthentication(authentication);            //
        
		return "aiConnectionTest";
        
       
	}
}
