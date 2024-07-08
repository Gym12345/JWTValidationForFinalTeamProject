package com.GymCompany.firstApp.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.GymCompany.firstApp.model.SignInResultDTO;
import com.GymCompany.firstApp.model.SignUpResultDTO;
import com.GymCompany.firstApp.service.SignService;

import io.swagger.annotations.ApiParam;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/sign-api")

public class SwaggerController { //swagger
	 private final Logger LOGGER = LoggerFactory.getLogger(SwaggerController.class);
	    private final SignService signService;

	    @Autowired
	    public SwaggerController(SignService signService) {
	        this.signService = signService;
	    }
	    
	    
		
	    @PostMapping(value = "/sign-in")
	    public SignInResultDTO signIn(
	        @ApiParam(value = "userId", required = true) @RequestParam(value="userId") String userId,
	        @ApiParam(value = "userPw", required = true) @RequestParam(value="userPw") String userPw)
	        throws RuntimeException {
	        LOGGER.info("[signIn] 로그인을 시도하고 있습니다. id : {}, pw : ****", userId);
	        SignInResultDTO signInResultDTO = signService.signIn(userId, userPw);

	        if (signInResultDTO.getCode() == 0) {
	            LOGGER.info("[signIn] 정상적으로 로그인되었습니다. id : {}, token : {}", userId,
	            		signInResultDTO.getToken());
	        }
	        return signInResultDTO;
	    }
	   
	    @PostMapping(value = "/sign-up")
	    public SignUpResultDTO signUp(
	        @ApiParam(value = "userId", required = true) @RequestParam(value="userId") String userId,
	        @ApiParam(value = "userPw", required = true) @RequestParam(value="userPw") String userPw,
	        @ApiParam(value = "userName", required = true) @RequestParam(value="userName") String userName,
	        @ApiParam(value = "role", required = true) @RequestParam(value="role") String role,
	        @ApiParam(value = "email", required = true) @RequestParam(value="email") String email,
	        @ApiParam(value = "phone", required = true) @RequestParam(value="phone") String phone,
	        @ApiParam(value = "age", required = true) @RequestParam(value="age") int age,
	        @ApiParam(value = "gender", required = true) @RequestParam(value="gender") String gender,
	        @ApiParam(value = "profileUrl") @RequestParam(value="profileUrl", required = false) String profileUrl,
	        @ApiParam(value = "profileName") @RequestParam(value="profileName", required = false) String profileName,
	        @ApiParam(value = "userSize") @RequestParam(value="userSize", required = false) String userSize,
	        @ApiParam(value = "userBio") @RequestParam(value="userBio", required = false) String userBio,
	        @ApiParam(value = "receiveEmail") @RequestParam(value="receiveEmail", defaultValue = "0") String receiveEmail,
	        @ApiParam(value = "receiveMessage") @RequestParam(value="receiveMessage", defaultValue = "0") String receiveMessage,
	        @ApiParam(value = "blockedProfiles") @RequestParam(value="blockedProfiles", required = false) String blockedProfiles,
	        @ApiParam(value = "favoriteProducts") @RequestParam(value="favoriteProducts", required = false) String favoriteProducts
	    		
	    		
	    		) {

	        LOGGER.info("[signUp] 회원가입을 수행합니다.");
	        LOGGER.info("role : {}", role);

	        // Call the signUp method with all the necessary fields
	        SignUpResultDTO signUpResultDTO =  signService.signUp(
	                userId, userPw, userName, role, email, phone, age, gender,
	                profileUrl, profileName, userSize, userBio, receiveEmail, receiveMessage,
	                blockedProfiles, favoriteProducts);

	        LOGGER.info("[signUp] 회원가입을 완료했습니다. id : {}", userId);
	        return signUpResultDTO;
	    }

	    
	    
	  
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    @GetMapping(value = "/exception")
	    public void exceptionTest() throws RuntimeException {
	        throw new RuntimeException("접근이 금지되었습니다.");
	    }
	    
	    @ExceptionHandler(value = RuntimeException.class)
	    public ResponseEntity<Map<String, String>> ExceptionHandler(RuntimeException e) {
	        HttpHeaders responseHeaders = new HttpHeaders();
	        //responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
	        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

	        LOGGER.error("ExceptionHandler 호출, {}, {}", e.getCause(), e.getMessage());

	        Map<String, String> map = new HashMap<>();
	        map.put("error type", httpStatus.getReasonPhrase());
	        map.put("code", "400");
	        map.put("message", "에러 발생");

	        return new ResponseEntity<>(map, responseHeaders, httpStatus);
	    }
}
