package com.GymCompany.firstApp.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.GymCompany.firstApp.repository.UserListRepository;
import com.GymCompany.firstApp.service.TokenBlacklistService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtTokenProvider { //loaduserbyUsername  쓰임 여기서
	private final Logger LOGGER=LoggerFactory.getLogger(JwtTokenProvider.class);
    private final UserDetailsService userDetailsService; // Spring Security 에서 제공하는 서비스 레이어
    private final TokenBlacklistService tokenBlacklistService;
	
	
    @Value("${springboot.jwt.secret}")
    private String secretKey; // Loaded from application.properties // this will replace the secret key that is defined in application.properties if program failed to scan it in application.propertie
	
    private final long tokenValidMillisecond=1000L * 60 * 60;
	public JwtTokenProvider(UserDetailsService userDetailsService, TokenBlacklistService tokenBlacklistService) {
		
		this.userDetailsService = userDetailsService;
		this.tokenBlacklistService = tokenBlacklistService;
	
	}
	
	
	
	 private SecretKey getSigningKey() {
	        byte[] keyBytes = Decoders.BASE64.decode(this.secretKey);
	        return Keys.hmacShaKeyFor(keyBytes);
	    }

	
	 @PostConstruct
	    protected void init() {
	        LOGGER.info("[init] JwtTokenProvider 내 secretKey 초기화 시작");
	       
	        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
	        
	        LOGGER.info("[init] JwtTokenProvider 내 secretKey 초기화 완료");
	    }
	 	
	   
	    // JWT 토큰 생성
	    public String createToken(String userUid, List<String> roles) {
	        LOGGER.info("[createToken] 토큰 생성 시작");
	        Claims claims = Jwts.claims().setSubject(userUid); //current logined user
	        claims.put("roles", roles);
	        //claims.put("currentLoginedUser", userUid); 
	        
	        Date now = new Date();
	        String token = Jwts.builder()
	            .setHeaderParam("typ", "JWT") // Set the type to JWT
	            .setClaims(claims)
	            .setIssuedAt(now)
	            .setExpiration(new Date(now.getTime() + tokenValidMillisecond))
	            .signWith(this.getSigningKey())
	          // .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret 값 세팅
	            .compact();

	        LOGGER.info("[createToken] 토큰 생성 완료");
	        return token;
	    }
	 
	  
	  
	    public Authentication getAuthentication(String token) {//Authentication 인터페이스는 인증 정보를 담는 하나의 토큰입니다. 필터에서 인증이성공한경우에만 작동
 	        LOGGER.info("[getAuthentication] 토큰 인증 정보 조회 시작");
	        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUsername(token));
	        LOGGER.info("[getAuthentication] 토큰 인증 정보 조회 완료, UserDetails UserName : {}",
	            userDetails.getUsername());
	        return new UsernamePasswordAuthenticationToken(userDetails, "",
	            userDetails.getAuthorities());
	    }
	    
	   
	    // JWT 토큰에서 회원 구별 정보 추출
	    public String getUsername(String token) {
	        LOGGER.info("[getUsername] 토큰 기반 회원 구별 정보 추출");
	        String info = Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody().getSubject();

	        LOGGER.info("[getUsername] 토큰 기반 회원 구별 정보 추출 완료, info : {}", info);
	        return info;
	    }
	    
	   
	    
	    public String resolveToken(HttpServletRequest request) { // 현재 사용중이지않음 
 
	        LOGGER.info("[resolveToken] HTTP 헤더에서 Token 값 추출");    
	        LOGGER.info("[resolveToken]:", request.getHeader("Authorization"));

	        return request.getHeader("Authorization");
	    }

	    
	    
	    public boolean validateToken(String token) {
	        LOGGER.info("[validateToken] 토큰 유효 체크 시작");
	        
	        try {
	        	
	        	if (tokenBlacklistService.isTokenBlacklisted(token)) {// checks if the token is blacklisted or not on redis 
	                LOGGER.info("[validateToken] 토큰이 블랙리스트에 있습니다.");
	                return false;
	            }
	        	
	            Jws<Claims> claims = Jwts.parserBuilder()
	            		.setSigningKey(getSigningKey())
	            		.build()
	            		.parseClaimsJws(token); // verifying signature  using secretkey
	            
	            LOGGER.info("[validateToken] 토큰 유효 체크 완료");
	            
	            return !claims.getBody().getExpiration().before(new Date()); //return false or true here
	        } catch (Exception e) { //체크과정에서 문제 생긴다면
	        	
	            LOGGER.info("[validateToken] 토큰 유효 체크 예외 발생");
	            
	            return false;
	        }
	    }
	    
	    
	    public Date getExpirationDate(String token) {  //토큰 만료 시간 리턴
	        LOGGER.info("[getExpirationDate] 토큰 만료 날짜 조회 시작");
	        Jws<Claims> claims = Jwts.parserBuilder()
	                .setSigningKey(getSigningKey())
	                .build()
	                .parseClaimsJws(token);
	        Date expiration = claims.getBody().getExpiration();
	        LOGGER.info("[getExpirationDate] 토큰 만료 날짜 조회 완료, expiration : {}", expiration);
	        return expiration;
	    }
}
