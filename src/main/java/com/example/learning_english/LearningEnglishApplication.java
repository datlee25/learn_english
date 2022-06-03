package com.example.learning_english;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class LearningEnglishApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearningEnglishApplication.class, args);
    }
    //this is bean of password encoder put it any where but dont put same class that use it's dependencies injection
    // if not you will get error message like this:
    //┌─────┐
    //|  apiSecurityConfig defined in file ApiSecurityConfig.class]
    //↑     ↓
    //|  authenticationService defined in file AuthenticationService.class]
    //└─────┘
    @Bean(name = "bCryptPasswordEncoder")
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
