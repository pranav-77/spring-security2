package com.spring.securityDemo.service;

import com.spring.securityDemo.model.Employee;
import com.spring.securityDemo.repository.EmployeeRepository;
import com.spring.securityDemo.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository repository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public Employee addData(Employee employee) {
        return repository.save(employee);
    }

    public List<Employee> getData() {
        return repository.findAll();
    }

    public Employee getById(int id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("No Employee Found In Given ID"));
    }

    public Employee update(int id, Employee employee) {
        getById(id);
        employee.setId(id);
        return repository.save(employee);
    }

    public void delete(int id) {
        getById(id);
        repository.deleteById(id);
    }

    public String login(String email, String password) {
    	System.out.println("from employee service impl 1");
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    	System.out.println("from employee service impl 2");

        if (authentication.isAuthenticated()) {
            Employee employee = repository.findByEmail(email).get();
            return jwtService.createToken(employee);
        }
        throw new RuntimeException("User not Authorized");
    }
}
