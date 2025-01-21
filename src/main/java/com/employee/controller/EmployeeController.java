package com.employee.controller;


import com.employee.model.EmployeeModel;
import com.employee.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmployeeController {


    @Autowired
    private  EmployeeService service;

    @PostMapping("/employee")
    public EmployeeModel postEmployee(@RequestBody EmployeeModel employee){

        return service.postEmployee(employee);
    }

    @GetMapping("/employees")
    public List<EmployeeModel> getAllEmployee(){
        return service.getAllEmployee();
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id){
        try {
            service.deleteEmployeeById(id);
            return  new ResponseEntity<>("Employee with id "+id+"deleted succesfully",HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return  new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<?> getEmployeebyId(@PathVariable Long id){
      EmployeeModel employee= service.getEmployeeById(id);
      if (employee==null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(employee);
    }
    @PatchMapping("/employee/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id,@RequestBody EmployeeModel employee){
        EmployeeModel updateEmployee= service.updateEmployee(id, employee);
        if (employee==null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return  ResponseEntity.ok(updateEmployee);
    }

}
