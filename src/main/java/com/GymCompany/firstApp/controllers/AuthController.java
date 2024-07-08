package com.GymCompany.firstApp.controllers;




import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import jakarta.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/auth")
public class AuthController {
	
	
	
	@GetMapping(value = "/loginMenu")
	public String loginMenu(HttpServletRequest request) {
		System.out.println("loginMenu");
		
		return "loginMenu";
	}

	@GetMapping(value = "/registerMenu")
	public String registerMenu(HttpServletRequest request) {
		System.out.println("registerMenu");
		
		return "registerMenu";
	}

	



	
	    
	    
}

	

