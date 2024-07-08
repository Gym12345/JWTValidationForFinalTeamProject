//package com.GymCompany.firstApp.test;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.Collections;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import com.GymCompany.firstApp.model.SignUpResultDTO;
//import com.GymCompany.firstApp.model.UserListDTO;
//import com.GymCompany.firstApp.repository.UserListRepository;
//import com.GymCompany.firstApp.service.SignServiceImpl;
//
//import uk.org.lidalia.slf4jtest.TestLogger;
//import uk.org.lidalia.slf4jtest.TestLoggerFactory;
//
//
//@ExtendWith(MockitoExtension.class)
//public class SignUpTest {
//
//    private static final TestLogger LOGGER = TestLoggerFactory.getTestLogger(SignServiceImpl.class);
//
//    @Mock
//    private UserListRepository userListRepository;
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    @InjectMocks
//    private SignServiceImpl signUpService;
//
//    private UserListDTO userDTO;
//
//    @BeforeEach
//    public void setUp() {
//        userDTO = new UserListDTO.Builder()
//                .userId("testUser")
//                .userPw("encodedPassword")
//                .userName("Test User")
//                .roles(Collections.singletonList("ROLE_USER"))
//                .joinDate(LocalDate.now())
//                .lastLoginTime(LocalDateTime.now())
//                .build();
//    }
//
//    @Test
//    public void testSignUp_Success() {
//        when(passwordEncoder.encode("testPassword")).thenReturn("encodedPassword");
//        when(userListRepository.save(any(UserListDTO.class))).thenReturn(userDTO);
//
//        SignUpResultDTO result = signUpService.signUp("testUser", "testPassword", "Test User", "user");
//
//        assertNotNull(result);
//        assertEquals("SUCCESS", result.getStatus());  // Assuming setSuccessResult sets the status to "SUCCESS"
//        
//        assertTrue(LOGGER.getLoggingEvents().stream()
//            .anyMatch(event -> event.getMessage().contains("회원가입 정상작동:")));
//    }
//
//    @Test
//    public void testSignUp_Failure() {
//        UserListDTO emptyNameUserDTO = new UserListDTO.Builder()
//                .userId("testUser")
//                .userPw("encodedPassword")
//                .userName("")
//                .roles(Collections.singletonList("ROLE_USER"))
//                .joinDate(LocalDate.now())
//                .lastLoginTime(LocalDateTime.now())
//                .build();
//
//        when(passwordEncoder.encode("testPassword")).thenReturn("encodedPassword");
//        when(userListRepository.save(any(UserListDTO.class))).thenReturn(emptyNameUserDTO);
//
//        SignUpResultDTO result = signUpService.signUp("testUser", "testPassword", "Test User", "user");
//
//        assertNotNull(result);
//        assertEquals("FAILURE", result.getStatus());  // Assuming setFailResult sets the status to "FAILURE"
//        
//        assertTrue(LOGGER.getLoggingEvents().stream()
//            .anyMatch(event -> event.getMessage().contains("회원가입 데이터 에러발생:")));
//    }
//}
