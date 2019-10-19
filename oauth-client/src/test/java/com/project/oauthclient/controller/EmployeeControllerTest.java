package com.project.oauthclient.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import com.project.oauthclient.model.Employee;


@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Mock
	RestTemplate restTemplate ;
	
	@Test
	public void testGetEmployeeInfo() throws Exception {
		
		mockMvc.perform(get("/getEmployees"))
        .andExpect(status().isOk())
        .andExpect(view().name("employee"))
        .andExpect(forwardedUrl("/WEB-INF/jsp/employee.jsp"));
	}

	@Test
	public void testShowEmployees() throws Exception {
							 
		 ResponseEntity<Employee[]> myEntity = new ResponseEntity<Employee[]>(HttpStatus.ACCEPTED);
		 
		 Mockito.when(restTemplate.exchange(ArgumentMatchers.eq("http://localhost:8080/getEmployeesList"),
				  ArgumentMatchers.eq(HttpMethod.POST),
				  ArgumentMatchers.<HttpEntity<String>>any(),
				  ArgumentMatchers.<ParameterizedTypeReference<Employee[]>>any()))
		  .thenReturn(myEntity);
		 
		
		mockMvc.perform(get("/showEmployees").param("code","8d38hu"))
        .andExpect(status().isOk())
        .andExpect(view().name("showEmployees"))
        .andExpect(forwardedUrl("/WEB-INF/jsp/showEmployees.jsp"))
        .andExpect(model().attribute("employees", hasSize(2)));
	}
	
	
	public List<Employee> getEmployees(){
		List<Employee> employees = new ArrayList<>();
        Employee emp = new Employee();
        emp.setEmpId("EMP1234");
        emp.setEmpName("JOHN DOE");
        employees.add(emp);
        
        Employee emp2 = new Employee();
        emp2.setEmpId("EMP3456");
        emp2.setEmpName("JANE DOE");
        employees.add(emp2);
        return employees;
		
	}
}
