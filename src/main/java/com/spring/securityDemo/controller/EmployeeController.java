package com.spring.securityDemo.controller;

import com.spring.securityDemo.model.Employee;
import com.spring.securityDemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {
    @Autowired
    private EmployeeService service;

    @PostMapping("add")
    public Employee addData(@RequestBody Employee employee) {
        return service.addData(employee);
    }

    @GetMapping("/get")
    public List<Employee> getData() {
        return service.getData();
    }

    @GetMapping("/search/{id}")
    public Employee getById(@PathVariable int id) {
        return service.getById(id);
    }

    @PutMapping("/update/{id}")
    public Employee update(@PathVariable int id, @RequestBody Employee employee) {
        System.out.println("update controller");
        return service.update(id, employee);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }

    @GetMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password) {
        return service.login(email, password);
    }
}
