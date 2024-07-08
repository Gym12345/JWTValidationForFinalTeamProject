package com.GymCompany.firstApp.service;

import java.time.Duration;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class TokenBlacklistServiceImpl implements TokenBlacklistService{
	 private static final String BLACKLIST_PREFIX = "blacklist:";

	    @Autowired
	    private RedisTemplate<String, Object> redisTemplate;

	    
		public void blacklistToken(String token, Date expiration) {
		    long expiryDuration = expiration.getTime() - System.currentTimeMillis();
	        redisTemplate.opsForValue().set(BLACKLIST_PREFIX + token, "true", Duration.ofMillis(expiryDuration));
			
		}

		
		public boolean isTokenBlacklisted(String token) {
			return redisTemplate.hasKey(BLACKLIST_PREFIX + token);
		}
}
