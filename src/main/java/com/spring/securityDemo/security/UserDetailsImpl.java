package com.spring.securityDemo.security;

import com.spring.securityDemo.model.Employee;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private Employee employee;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        System.out.println(employee);
        return employee.getPassword();
    }

    @Override
    public String getUsername() {
        return employee.getEmail();
    }
}
