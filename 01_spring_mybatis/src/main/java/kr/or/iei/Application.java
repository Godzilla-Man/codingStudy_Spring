package kr.or.iei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/*
 * Spring Security 기본 설정값 제외(시큐리티 적용 시, 첫 화면이 로그인 화면으로 표기됨을 제거)
 */

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
