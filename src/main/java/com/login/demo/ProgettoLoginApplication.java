package com.login.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.login", "com.item"})
@MapperScan({"com.login.mapper", "com.item.mapper"})
public class ProgettoLoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProgettoLoginApplication.class, args);
	}

}
