package com.GymCompany.firstApp;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;



@EnableTransactionManagement
@SpringBootApplication
public class BoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardApplication.class, args);
	}
	@Bean
	public RestTemplate restTemplate() { //rest 통신 , 현재는 flask 와 통신시 사용중
		
		
	    return new RestTemplate();
	  }
	
	
//	RESTful API란 무엇인가요?
//	RESTful API는 두 컴퓨터 시스템이 인터넷을 통해 정보를 안전하게 교환하기 위해 사용하는 인터페이스입니다.
//	대부분의 비즈니스 애플리케이션은 다양한 태스크를 수행하기 위해 다른 내부 애플리케이션 및 서드 파티 애플리케이션과 통신해야 합니다.
//예를 들어 월간 급여 명세서를 생성하려면 인보이스 발행을 자동화하고 내부의 근무 시간 기록 애플리케이션과 통신하기 위해 
//	내부 계정 시스템이 데이터를 고객의 뱅킹 시스템과 공유해야 합니다.
//	RESTful API는 안전하고 신뢰할 수 있으며 효율적인 소프트웨어 통신 표준을 따르므로 이러한 정보 교환을 지원합니다
	
//두개이상의 다른시스템끼리 데이터를 통신 하기 위해 사용하는 인터페이스
}
