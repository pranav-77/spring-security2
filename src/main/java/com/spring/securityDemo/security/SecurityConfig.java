package com.spring.securityDemo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        System.out.println("Inside Security Filter");
        return security
                .csrf(csrf -> csrf.disable())
//                .securityMatchers(matchers -> matchers.requestMatchers("/add","/login/**"))
//                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll().anyRequest().authenticated())
                .authorizeHttpRequests(auth -> auth.requestMatchers("/add","/login/**").permitAll().anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(jwtFilter , UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider())
                .build();
    }

    @Bean
    AuthenticationManager authenticationManager (AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    AuthenticationProvider authenticationProvider () {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

}
