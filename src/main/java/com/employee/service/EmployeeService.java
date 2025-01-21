package com.employee.service;

import com.employee.model.EmployeeModel;
import com.employee.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

//    @Autowired
    private  EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

//    public EmployeeService(EmployeeRepository repository) {
//        this.repository = repository;
//    }

    public  EmployeeModel postEmployee(EmployeeModel employee){

        return repository.save(employee);
    }
    public List<EmployeeModel> getAllEmployee(){

        return repository.findAll();
    }

    public EmployeeModel getEmployeeById(Long id)
    {
        return repository.findById(id).orElse(null);
    }
    public void deleteEmployeeById(Long id){
        if (!repository.existsById(id)){
            throw new EntityNotFoundException("Employee with id "+ id+" not found");
        }
        repository.deleteById(id);
    }

    public EmployeeModel updateEmployee(Long id,EmployeeModel employee){
        Optional<EmployeeModel> optionalEmployeeModel= repository.findById(id);
        if (optionalEmployeeModel.isPresent()){
         EmployeeModel existingemployee = optionalEmployeeModel.get();
         existingemployee.setName(employee.getName());
         existingemployee.setContact(employee.getContact());
         existingemployee.setEmail(employee.getEmail());
         existingemployee.setDepartment(employee.getDepartment());
         return repository.save(existingemployee);
        }
        return null;
    }

}
