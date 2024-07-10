package com.GymCompany.firstApp.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.GymCompany.firstApp.Response.CommonResponse;
import com.GymCompany.firstApp.jwt.JwtTokenProvider;
import com.GymCompany.firstApp.model.SignInResultDTO;
import com.GymCompany.firstApp.model.SignUpResultDTO;
import com.GymCompany.firstApp.model.UserListDTO;
import com.GymCompany.firstApp.repository.UserListRepository;




@Service
public class SignServiceImpl implements SignService {

	  private final Logger LOGGER = LoggerFactory.getLogger(SignServiceImpl.class);

	    public UserListRepository userListRepository;
	    public JwtTokenProvider jwtTokenProvider;
	    public PasswordEncoder passwordEncoder;

	  
	public SignServiceImpl(UserListRepository userListRepository, JwtTokenProvider jwtTokenProvider,
				PasswordEncoder passwordEncoder) {
			super();
			this.userListRepository = userListRepository;
			this.jwtTokenProvider = jwtTokenProvider;
			this.passwordEncoder = passwordEncoder;
		}
	
	
	
	
	@Transactional
	@Override
	public SignUpResultDTO signUp(String userId, String userPw, String userName, String role, String email, String phone, int age, String gender,
				String profileUrl,
			 	String profileName,
		        String userSize,
		        String userBio,
		        String receiveEmail,
		        String receiveMessage,
		        String blockedProfiles,
		        String favoriteProducts) {
		
	    LOGGER.info("signing up");
	    UserListDTO userDTO;

	    if (role.equalsIgnoreCase("admin")) { // 대소문자 구분 X
	        userDTO = new UserListDTO.Builder()
	                .userId(userId)
	                .userPw(passwordEncoder.encode(userPw))
	                .userName(userName)
	                .email(email)
	                .phone(phone)
	                .age(age)
	                .gender(gender)
	                .roles(Collections.singletonList("ROLE_ADMIN")) // 어드민 로그인
	                .joinDate(LocalDate.now())
	                .lastLoginTime(LocalDateTime.now())
	                .profileUrl(profileUrl)
	                .profileName(profileName)
	                .userSize(userSize)
	                .userBio(userBio)
	                .receiveEmail(receiveEmail)
	                .receiveMessage(receiveMessage)
	                .blockedProfiles(blockedProfiles)
	                .favoriteProducts(favoriteProducts)
	                .build();
	        
	        
	    } else {
	    	
	    	userDTO = new UserListDTO.Builder()
	                .userId(userId)
	                .userPw(passwordEncoder.encode(userPw))
	                .userName(userName)
	                .email(email)
	                .phone(phone)
	                .age(age)
	                .gender(gender)
	                .roles(Collections.singletonList("ROLE_USER")) // 노멀유저
	                .joinDate(LocalDate.now())
	                .lastLoginTime(LocalDateTime.now())
	                .profileUrl(profileUrl)
	                .profileName(profileName)
	                .userSize(userSize)
	                .userBio(userBio)
	                .receiveEmail(receiveEmail)
	                .receiveMessage(receiveMessage)
	                .blockedProfiles(blockedProfiles)
	                .favoriteProducts(favoriteProducts)
	                .build();
	        
	    }

	    LOGGER.info("userDTO: {}", userDTO);
	    UserListDTO savedUserDTO = userListRepository.save(userDTO); // savedUserDTO 찍어보기

	    LOGGER.info("savedUserDTO: {}", savedUserDTO); // 잘찍힘
	    SignUpResultDTO signUpResultDTO = new SignUpResultDTO(); // response 에 쓰일 객체임 성공유무, 성공 또는 실패시 메시지 포함함 성공유무, 메시지의 값세팅은 setFailResult 나 setSuccessResult 함수에서 진행

	    if (savedUserDTO.getUsername().isEmpty()) {
	        LOGGER.info("회원가입 데이터 에러발생:", savedUserDTO);
	        setFailResult(signUpResultDTO);
	    } else {
	        LOGGER.info("회원가입 정상작동:", savedUserDTO);
	        setSuccessResult(signUpResultDTO);
	    }

	    return signUpResultDTO;
	}

	
	@Transactional
	@Override
	public SignInResultDTO signIn(String userId, String userPw) {
	    LOGGER.info("로그인 진행중");
	    UserListDTO userDTO = userListRepository.getByUserId(userId);
	    
	    SignInResultDTO signInResultDTO = new SignInResultDTO(); // Initialize the object here
	    
	    if (userDTO != null && passwordEncoder.matches(userPw, userDTO.getUserPw())) {
	        LOGGER.info("로그인성공");
	        userListRepository.updateLastLoginTime(userId);
	        String token = jwtTokenProvider.createToken(String.valueOf(userDTO.getUserId()), userDTO.getRoles());
	        signInResultDTO = new SignInResultDTO.Builder()
	                .token(token)
	                .build();
	        setSuccessResult(signInResultDTO);
	        LOGGER.info("Generated token: {}", token);
	        System.out.println("token:" + token);
	        return signInResultDTO;
	    } else {
	        LOGGER.info("입력 내용 불일치");
	        setFailResult(signInResultDTO);
	        
	        return signInResultDTO;
	    }
	    
	   
	}
	

	    
    
    @Override
	@Transactional(readOnly = true)
    public int redundancyCheck(String userId) {
        List<UserListDTO> userList = userListRepository.findAll();
        userId = userId.replaceAll("^\"|\"$", "");  // Remove quotes from userId if present
        
        for (UserListDTO user : userList) {
            if (user.getUserId().equals(userId)) {
                return 1;
            }
        }
        return 0;
    }
	
	

	// 결과 모델에 api 요청 성공 데이터를 세팅해주는 메소드
    private void setSuccessResult(SignUpResultDTO result) {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }

    // 결과 모델에 api 요청 실패 데이터를 세팅해주는 메소드
    private void setFailResult(SignUpResultDTO result) {
        result.setSuccess(false);
        result.setCode(CommonResponse.FAIL.getCode());
        result.setMsg(CommonResponse.FAIL.getMsg());
    }

	

}
