package com.employee;

import com.employee.model.EmployeeModel;
import com.employee.repository.EmployeeRepository;
import com.employee.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class EmployeeApplicationTests {

	@Test
	void contextLoads() {
	}

	@Mock
	private EmployeeRepository repository;

	@InjectMocks
	private EmployeeService service;

	@Test
     void testPostEmployee(){

		EmployeeModel employee = new EmployeeModel();
		employee.setId(4L);
		employee.setName("rajaravi");
		employee.setEmail("rajaravi@gmail.com");
		employee.setDepartment("IT");
		employee.setContact("123454321");

		when(repository.save(employee)).thenReturn(employee);

		EmployeeModel result = service.postEmployee(employee);

		assertNotNull(result);
		assertEquals("rajaravi",result.getName());
		assertEquals("rajaravi@gmail.com",result.getEmail());
		assertEquals("IT",result.getDepartment());
		assertEquals("123454321",result.getContact());
		verify(repository,times(1)).save(employee);

	 }

	 @Test
	 void testGetAllEmployees(){

		when(repository.findAll()).thenReturn(Arrays.asList(new EmployeeModel(),new EmployeeModel()));
		 List<EmployeeModel> employee=service.getAllEmployee();
		 assertEquals(2,employee.size());
		 verify(repository,times(1)).findAll();
	 }

	 @Test
	 void testDeleteEmployeeById(){
		when(repository.existsById(4L)).thenReturn(true);
		service.deleteEmployeeById(4L);
		verify(repository,times(1)).deleteById(4L);
	 }

	 @Test
	 void testDeleteEmployeeByIdThrowsException(){
		when(repository.existsById(4L)).thenReturn(false);

		 EntityNotFoundException exception=assertThrows(EntityNotFoundException.class,()->{
			 service.deleteEmployeeById(4l);
		 });
		 assertEquals("Employee with id 4 not found",exception.getMessage());
	 }

	 @Test
	 void testGetEmployeeById(){
		EmployeeModel employee = new EmployeeModel();
		employee.setId(4L);
		employee.setName("rajaravi");

		when(repository.findById(4L)).thenReturn(Optional.of(employee));

		EmployeeModel result=service.getEmployeeById(4L);
		assertNotNull(result);
		verify(repository,times(1)).findById(4l);
	 }

	 @Test
	 void testUpdateEmployee(){
		EmployeeModel existingEmployee= new EmployeeModel();
		existingEmployee.setId(4L);
		existingEmployee.setName("rajaravi");
		existingEmployee.setEmail("rajaravi@gmail.com");
		existingEmployee.setContact("123454321");
		existingEmployee.setDepartment("IT");

		EmployeeModel updatedEmployee= new EmployeeModel();
		updatedEmployee.setName("raman");
		 updatedEmployee.setEmail("raman@gmail.com");
		 updatedEmployee.setContact("1122334455");
		 updatedEmployee.setDepartment("HR");

		when(repository.findById(4L)).thenReturn(Optional.of(existingEmployee));
		when(repository.save(existingEmployee)).thenReturn(existingEmployee);

		EmployeeModel result = service.updateEmployee(4l,updatedEmployee);

		assertNotNull(result);
		assertEquals("raman",result.getName());
		assertEquals("raman@gmail.com",result.getEmail());
		assertEquals("HR",result.getDepartment());
		assertEquals("1122334455",result.getContact());
		verify(repository,times(1)).findById(4L);
		verify(repository,times(1)).save(existingEmployee);

	 }
}
