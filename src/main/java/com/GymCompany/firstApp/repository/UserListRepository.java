package com.GymCompany.firstApp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.GymCompany.firstApp.model.UserListDTO;


@Repository
public interface UserListRepository extends JpaRepository<UserListDTO, Integer> { 
	
	UserListDTO getByUserId(String id);  //UserListDTO is implementing UserDetails // jpa 가 자동적으로 함수명 분석해서 정의함

	@Modifying
    @Transactional
    @Query("UPDATE UserListDTO u SET u.lastLoginTime = CURRENT_TIMESTAMP WHERE u.userId = :userId")
    void updateLastLoginTime(@Param("userId") 	String userId);
	
	
}