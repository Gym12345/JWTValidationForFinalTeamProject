package com.GymCompany.firstApp.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@CrossOrigin
@RequestMapping("/admin")
@RestController
public class AdminAuthRestController {
	
	 @Autowired
	 private RestTemplate restTemplate;


	  

	  @PostMapping("/run-notebook")
	    public String testAIConnection(@RequestBody String payload) {
	        
		  
	        String url = "http://localhost:5000/admin/run-notebook";
	        ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);
	        
	        return response.getBody();
	    }
}
