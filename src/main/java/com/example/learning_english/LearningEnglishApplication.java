package com.example.learning_english;

import com.example.learning_english.security.jwt.AuthEntryPointJwt;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

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
    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:3000");
            }
        };
    }

    @Bean
    public AuthEntryPointJwt authEntryPointJwt(){
        return new AuthEntryPointJwt();
    };
}
