package com.example.learning_english.security;

import com.example.learning_english.security.jwt.AuthEntryPointJwt;
import com.example.learning_english.security.jwt.AuthTokenFilter;
import com.example.learning_english.exception.RestAuthenticationFailureHandler;
import com.example.learning_english.security.oauth2.CustomAuthenticationFailureHandler;
import com.example.learning_english.security.oauth2.CustomAuthenticationSuccessHandler;
import com.example.learning_english.security.oauth2.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.example.learning_english.entity.enums.ERole.ROLE_ADMIN;
import static com.example.learning_english.entity.enums.ERole.ROLE_USER;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter(){
        return new AuthTokenFilter();
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Autowired
    private CustomOAuth2UserService oauthUserService;

    @Autowired
    private CustomAuthenticationFailureHandler failureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests().antMatchers("/api/v1/register**",
                "/api/v1/login**",
                "/api/v1/token/refresh_**",
                "/api/v1/confirm/email/**","/",
                "/oauth2/**","/test/**",
                "/api/v1/chat/**",
                "/ws/**",
                "/app/**")
        .permitAll();

        http.authorizeRequests().antMatchers("/api/v1/users/**").hasAnyAuthority(String.valueOf(ROLE_USER));
        //add requests path for more role here
        http.authorizeRequests().antMatchers("/api/v1/admin/**").hasAnyAuthority(String.valueOf(ROLE_ADMIN));
//        http.addFilter(apiAuthenticationFilter);
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                .userService(oauthUserService);
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    RestAuthenticationFailureHandler authenticationFailureHandler(){
        return new RestAuthenticationFailureHandler();
    }

}
