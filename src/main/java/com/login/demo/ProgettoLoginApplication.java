package com.login.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.login")
@MapperScan("com.login.mapper")
public class ProgettoLoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProgettoLoginApplication.class, args);
	}

}
