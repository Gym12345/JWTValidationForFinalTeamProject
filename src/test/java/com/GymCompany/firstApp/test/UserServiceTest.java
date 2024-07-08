package com.GymCompany.firstApp.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.GymCompany.firstApp.jwt.JwtTokenProvider;
import com.GymCompany.firstApp.model.SignInResultDTO;
import com.GymCompany.firstApp.model.UserListDTO;
import com.GymCompany.firstApp.repository.UserListRepository;
import com.GymCompany.firstApp.service.SignServiceImpl;

import uk.org.lidalia.slf4jext.Level;
import uk.org.lidalia.slf4jtest.LoggingEvent;
import uk.org.lidalia.slf4jtest.TestLogger;
import uk.org.lidalia.slf4jtest.TestLoggerFactory;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest { //테스트 클래스는 db와 연동하지않음

    private static final TestLogger LOGGER = TestLoggerFactory.getTestLogger(SignServiceImpl.class);

    @Mock
    private UserListRepository userListRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private SignServiceImpl signService;

    private UserListDTO userDTO;

    @BeforeEach
    public void setUp() {
        userDTO = new UserListDTO.Builder()
                .userId("testUser")
                .userPw("encodedPassword")
                .userName("Test User")
                .roles(List.of("ROLE_USER"))
                .build();
    }

    @Test
    public void testSignIn_Success() {//does not interact with actual db
        when(userListRepository.getByUserId("testUser")).thenReturn(userDTO);
        when(passwordEncoder.matches("testPassword", "encodedPassword")).thenReturn(true);

        // Mocking a more realistic token value
        String realisticToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.testPayloadSignature";
        when(jwtTokenProvider.createToken("testUser", userDTO.getRoles())).thenReturn(realisticToken);

        SignInResultDTO result = signService.signIn("testUser", "testPassword");

        assertNotNull(result);
        assertEquals(realisticToken, result.getToken());

        List<LoggingEvent> loggingEvents = LOGGER.getLoggingEvents();
        assertTrue(loggingEvents.stream()
            .anyMatch(event -> event.getLevel() == Level.INFO && event.getMessage().contains("Generated token: " + realisticToken)));
    }

    @Test
    public void testSignIn_Failure() {
        when(userListRepository.getByUserId("testUser")).thenReturn(userDTO);
        when(passwordEncoder.matches("wrongPassword", "encodedPassword")).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            signService.signIn("testUser", "wrongPassword");
        });

        assertNull(exception.getMessage());  // Or any other assertion you want to make about the exception

        List<LoggingEvent> loggingEvents = LOGGER.getLoggingEvents();
        assertTrue(loggingEvents.stream()
            .anyMatch(event -> event.getLevel() == Level.INFO && event.getMessage().contains("입력 내용 불일치")));
    }
}
