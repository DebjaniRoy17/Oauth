package com.project.oauth.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.project.oauth.model.Employee;

@RestController
public class EmployeeController {

	@RequestMapping(value="/getEmployeesList", produces="application/json")
	@ResponseBody
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
