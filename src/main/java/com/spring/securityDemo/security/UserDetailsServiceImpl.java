package com.spring.securityDemo.security;

import com.spring.securityDemo.model.Employee;
import com.spring.securityDemo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("load by user name");
        System.out.println("----------------------");
        System.out.println(username);
        Employee employee = employeeRepository.findByEmail(username).get();
        System.out.println("success");
        return new UserDetailsImpl(employee);
//        return employeeRepository.findByEmail(username).map(e -> new  UserDetailsImpl(e)).orElseThrow();
    }
}
