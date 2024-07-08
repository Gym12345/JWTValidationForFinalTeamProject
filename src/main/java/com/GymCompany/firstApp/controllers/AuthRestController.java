package com.GymCompany.firstApp.controllers;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.GymCompany.firstApp.jwt.JwtTokenProvider;
import com.GymCompany.firstApp.model.SignInResultDTO;
import com.GymCompany.firstApp.model.SignUpResultDTO;
import com.GymCompany.firstApp.model.TempUserDTO;
import com.GymCompany.firstApp.model.UserListDTO;
import com.GymCompany.firstApp.service.SignService;
import com.GymCompany.firstApp.service.TokenBlacklistService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthRestController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(SwaggerController.class);
	  
	@Autowired
    private   SignService signService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
	
    @Autowired
    private TokenBlacklistService tokenBlacklistService;
	

    
    @PostMapping(value = "/loginCheck")
    public String loginCheck(@RequestBody TempUserDTO dto, HttpServletResponse response) {

        LOGGER.info("[signIn] Attempting to log in. with id : {}", dto.getUserId());
        SignInResultDTO signInResultDTO = signService.signIn( dto.getUserId(),  dto.getUserPw());
      
        if (signInResultDTO.getCode() == 1) {
            LOGGER.info("[signIn] Successfully logged in. id : {}, token : {}", dto.getUserId(), signInResultDTO.getToken());
            
            // Get JWT token from signInResultDTO
            String jwtToken = signInResultDTO.getToken();
            
            Cookie loginCookie= new Cookie("loginCookie", dto.getUserId());
            loginCookie.setPath("/");
            loginCookie.setHttpOnly(true);
            loginCookie.setMaxAge(60 * 60); // 1 hour , cuz jwtToken is 1 hour exp
            
            response.addCookie(loginCookie);
            
            
            if (jwtTokenProvider.validateToken(jwtToken)) {
                
                Authentication authentication = jwtTokenProvider.getAuthentication(jwtToken);
               
                System.out.println("jwtToken:"+jwtToken);
                System.out.println("authentication:"+authentication);
                
                SecurityContextHolder.getContext().setAuthentication(authentication);
                
                //response.setHeader("X-AUTH-TOKEN",jwtToken );
                
            
    	        
    	       
                return jwtToken;
            } else {
                LOGGER.info("JWT token validation failed for user id: {}", dto.getUserId());
               ;
                return null;
            }
        } else {
            LOGGER.info("Login failed for user id: {}",dto.getUserId());
            
            return null;
        }
    }
	  
	  
//    @PostMapping(value = "/loginCheck")
// public String loginCheck(@RequestBody Map<String, String> loginData) { //김준형코드
//    String userId = loginData.get("email");
//    String userPw = loginData.get("password");
//
//      LOGGER.info("[signIn] Attempting to log in. id : {}, pw : ****", userId);
//      SignInResultDTO signInResultDTO = signService.signIn(userId, userPw);
//      
//      if (signInResultDTO.getCode() == 1) {
//          LOGGER.info("[signIn] Successfully logged in. id : {}, token : {}", userId, signInResultDTO.getToken());
//          
//          // Get JWT token from signInResultDTO
//          String jwtToken = signInResultDTO.getToken();
//          
//          if (jwtTokenProvider.validateToken(jwtToken)) {
//              
//              Authentication authentication = jwtTokenProvider.getAuthentication(jwtToken);
//             
//              System.out.println("jwtToken:"+jwtToken);
//              System.out.println("authentication:"+authentication);
//              
//              SecurityContextHolder.getContext().setAuthentication(authentication);
//              
//              //response.setHeader("X-AUTH-TOKEN",jwtToken );
//              
//          
//             
//            
//              return jwtToken;
//          } else {
//              LOGGER.info("JWT token validation failed for user id: {}", userId);
//             ;
//              return null;
//          }
//      } else {
//          LOGGER.info("Login failed for user id: {}", userId);
//          
//           return null;
//      }
//  }
    
    
	  @PostMapping(value = "/registerCheck")
	  public SignUpResultDTO registerCheck(@RequestBody UserListDTO userDTO) {
	      String role = "normal"; // admin 만 아니면 어떤 스트링이던지 normalUser로 저장되서 상관없음
	      System.out.println("userId:" + userDTO.getUserId());
	      
	      // Call the signUp method with all the necessary fields
	      SignUpResultDTO signUpResultDTO = signService.signUp(
	    		  userDTO.getUserId(),
	              userDTO.getUserPw(),
	              userDTO.getUsername(),
	              role,
	              userDTO.getEmail(),
	              userDTO.getPhone(),
	              userDTO.getAge(),
	              userDTO.getGender(),
	              userDTO.getProfileUrl(),
	              userDTO.getProfileName(),
	              userDTO.getUserSize(),
	              userDTO.getUserBio(),
	              userDTO.getReceiveEmail(),
	              userDTO.getReceiveMessage(),
	              userDTO.getBlockedProfiles(),
	              userDTO.getFavoriteProducts()
	      );

	      LOGGER.info("[signUp] 회원가입을 완료했습니다. id : {}", userDTO.getUserId());
	      return signUpResultDTO;
	  }

	  
	  
		@PostMapping("/rddCheck")
	    public int rddUserId(@RequestBody String userIdData) { 
			 System.out.println("rdddddddddddddddddddddd");
	        System.out.println("Data received from client: " + userIdData);
	       
	        int result = 0;
	        try {
	            result = signService.redundancyCheck(userIdData);
	            System.out.println("rddResult:" + result);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return result;
	    }
		
		 @PostMapping("/logout")
		    public int logout(HttpServletRequest request, @RequestHeader("token-for-blacklist") String token, HttpServletResponse response) {
			 	

			 	int result=0;
		        try {
		           

		            if (jwtTokenProvider.validateToken(token)) {// 아직 유효한 토큰이라면 ~~
		                // Blacklist the token
		            	Date tokenExpDate=jwtTokenProvider.getExpirationDate(token);
		            	tokenBlacklistService.blacklistToken(token,tokenExpDate);

		                // Clear the security context
		                SecurityContextHolder.clearContext();
		                
		                
		                Cookie loginCookie = new Cookie("loginCookie", null);
				        loginCookie.setPath("/");
				        loginCookie.setHttpOnly(true);
				        loginCookie.setMaxAge(0); // Setting max age to 0 to delete the cookie

				        response.addCookie(loginCookie);
		                
		                
		                result=1;
		                return result;
		            } else {
		                return result;
		            }
		        } catch (Exception e) {
		        	LOGGER.info("error while logging out:" + e.getMessage());
		            return result;
		        }
		    }
	
}
