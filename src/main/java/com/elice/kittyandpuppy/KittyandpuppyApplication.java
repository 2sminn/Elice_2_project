package com.elice.kittyandpuppy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class KittyandpuppyApplication {

	public static void main(String[] args) {
		SpringApplication.run(KittyandpuppyApplication.class, args);
	}

}
