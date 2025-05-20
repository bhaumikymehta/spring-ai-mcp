package com.example.springaimcp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Meta-annotation that combines @Configuration, @EnableAutoConfiguration, and
// @ComponentScan
@SpringBootApplication
public class SpringAiMcpApplication {

	public static void main(String[] args) {
		// This line starts the Spring Boot application
		SpringApplication.run(SpringAiMcpApplication.class, args);
	}

}
