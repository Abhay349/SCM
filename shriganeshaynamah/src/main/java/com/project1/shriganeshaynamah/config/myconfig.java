package com.project1.shriganeshaynamah.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity


public  class myconfig{
    @Autowired
     private UserDetailsService userDEtSEr;
   @Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(auth -> auth
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .requestMatchers("/user/**").permitAll()
            .anyRequest().permitAll()
        )
        .formLogin(form -> form
        .loginPage("/signin")
        .loginProcessingUrl("/logged")
        .defaultSuccessUrl("/user/index")
        .failureUrl("/failed")
        .permitAll()
        )
        
        .csrf(csrf -> csrf.disable());

    return http.build();
}

   @Bean
public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDEtSEr);
    provider.setPasswordEncoder(passwordEncoder()); // this is NOT deprecated
    return provider;
}



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}













   



