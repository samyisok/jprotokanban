package com.axix.jprotokanban;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class JProtoKanbanApplication {

	public static void main(String[] args) {
		SpringApplication.run(JProtoKanbanApplication.class, args);
	}

}
