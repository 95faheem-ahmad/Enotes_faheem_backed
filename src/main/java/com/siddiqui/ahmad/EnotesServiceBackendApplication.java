package com.siddiqui.ahmad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication

@EnableJpaAuditing(auditorAwareRef="auditAware")
public class EnotesServiceBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnotesServiceBackendApplication.class, args);
	}

}
