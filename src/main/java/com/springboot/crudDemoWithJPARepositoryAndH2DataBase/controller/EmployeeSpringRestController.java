package com.springboot.crudDemoWithJPARepositoryAndH2DataBase.controller;

import com.springboot.crudDemoWithJPARepositoryAndH2DataBase.dao.EmployeeRepository;
import com.springboot.crudDemoWithJPARepositoryAndH2DataBase.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeSpringRestController {

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeSpringRestController(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
        this.employeeRepository.createDataForH2();
    }

    @GetMapping("/employees")
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    @GetMapping("/employees/{employeeName}")
    public List<Employee> getEmployeeByName(@PathVariable String employeeName){
        return employeeRepository.findByFirstName(employeeName);
    }

    @GetMapping("/employees/{employeeId}")
    public Employee getEmployeeById(@PathVariable int employeeId){
        return employeeRepository.findById(employeeId).orElse(null);
    }

    @PostMapping("/employees")
    @Transactional
    public Employee createNewEmployee(@RequestBody Employee newEmployee){
        return employeeRepository.save(newEmployee);
    }

    @PatchMapping("employees/{employeeId}")
    @Transactional
    public Employee patchEmployee(@PathVariable int employeeId, @RequestBody Employee patchEmployee){
        try{
            if(employeeRepository.existsById(employeeId)){
                Employee existingEmployee = employeeRepository.findById(employeeId).orElse(null);
                existingEmployee.setEmail(patchEmployee.getEmail());
                existingEmployee.setFirstName(patchEmployee.getFirstName());
                existingEmployee.setLastName(patchEmployee.getLastName());
                return employeeRepository.save(existingEmployee);
            }else return employeeRepository.save(patchEmployee);
        } catch (IndexOutOfBoundsException e){
            throw new RuntimeException("Employee id not found "+ employeeId);
        }

    }

    @DeleteMapping("/employees/{employeeId}")
    @Transactional
    public String deleteEmployee(@PathVariable int EmployeeId){
        try{
            employeeRepository.deleteById(EmployeeId);
        } catch (IndexOutOfBoundsException e){
            throw new RuntimeException("Employee id not found "+ EmployeeId);
        }

        return "The Employee data have been deleted "+ EmployeeId;
    }

    @GetMapping("/ping")
    public String ping(){
        return "hello world";
    }
}
