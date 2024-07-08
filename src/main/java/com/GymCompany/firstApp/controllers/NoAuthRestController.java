package com.GymCompany.firstApp.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;


@CrossOrigin
@RestController
public class NoAuthRestController {
	
	@GetMapping(value = "/favicon.ico")
	public void favicon(HttpServletRequest request) {
	}
		

}
