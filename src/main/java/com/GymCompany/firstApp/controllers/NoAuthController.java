package com.GymCompany.firstApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class NoAuthController {
	@GetMapping(value = "/")
	public String mainPage(HttpServletRequest request) {
		System.out.println("MainPage");
		
		return "MainPage";
	}
	@GetMapping(value = "/gamePage")
	public String gamePage(HttpServletRequest request) {
		System.out.println("gamePage");
		
		return "gamePage";
	}
	
	
}
