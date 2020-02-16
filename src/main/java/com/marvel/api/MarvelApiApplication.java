package com.marvel.api;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.marvel.api.model.User;
import com.marvel.api.repository.UserRepository;

@SpringBootApplication
public class MarvelApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarvelApiApplication.class, args);
	}
	
	/**
     * This method creates the admin user
     * */
    @Bean
    protected CommandLineRunner init(final UserRepository userRepository) {

        return args -> {
            User user = new User();
            user.setUsername("test");
            user.setPassword("marvel");
            user.setName("Test");
            userRepository.save(user);

        };
    }

}
