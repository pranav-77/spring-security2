package com.spring.securityDemo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;


@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        System.out.println("Inside Jwt filter");
        String authHeader = request.getHeader("Authorization");

        String token = null;
        String userName = null;
        boolean isExpired = false;

        System.out.println(authHeader);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            System.out.println("inside 1nd if");
            token = authHeader.substring(7);
            userName = jwtService.extractName(token);
            isExpired = jwtService.extractExpiry(token);

            System.out.println(userName + " "+ isExpired);
        }

        if (userName != null & !isExpired & SecurityContextHolder.getContext().getAuthentication() == null) {
            System.out.println("inside 2nd if");
            UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(userName, "", List.of(new SimpleGrantedAuthority("USER")));
            upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(upat);
            System.out.println("End");
//            return;
        }
        filterChain.doFilter(request, response);
    }

}
