package com.example.ingle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class IngleApplication {

	public static void main(String[] args) {
		SpringApplication.run(IngleApplication.class, args);
	}

}
