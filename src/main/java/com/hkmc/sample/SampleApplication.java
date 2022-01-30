package com.hkmc.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
//properties 값 설정
@PropertySource(value = "classpath:properties-${spring.profiles.active}/application.properties", ignoreResourceNotFound = true)
public class SampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleApplication.class, args);
	}

}
