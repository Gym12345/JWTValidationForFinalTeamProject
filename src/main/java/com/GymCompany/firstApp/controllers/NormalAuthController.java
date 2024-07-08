package com.GymCompany.firstApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.GymCompany.firstApp.jwt.JwtTokenProvider;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/normalUser")
public class NormalAuthController {
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	
	//@PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
	@GetMapping(value = "/afterLogin")
	public String afterLogin(HttpServletRequest request) {
		System.out.println("afterLogin");
		
		return "afterLoginPage";
	}
	
	@GetMapping(value = "/invalidateSession")  // temporarily using this , delete this later
	public String invalidateSession(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		session.invalidate();
		return "redirect:/";
	}
	
	@GetMapping(value = "/basicTemplate") // this is basic code for loading other page or function
	public String test(HttpServletRequest request) {

		String jwtToken=(String) request.getAttribute("token");   //must need 
		
		Authentication authentication = jwtTokenProvider.getAuthentication(jwtToken);    //optional but could be essential afterhand
        SecurityContextHolder.getContext().setAuthentication(authentication);            //
  
		return "basicTemplate";
        
       
	}
	
	

}
