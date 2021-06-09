package com.mbaker.abumazrouqdashboard.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mbaker.abumazrouqdashboard.beans.model.User;
import com.mbaker.abumazrouqdashboard.beans.request.UserCredential;
import com.mbaker.abumazrouqdashboard.exception.AbuMazrouqDashboardException;
import com.mbaker.abumazrouqdashboard.services.UserService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	private UserService employeeService;

	@PostMapping("/login")
	public User login(@RequestBody UserCredential user) {
		return employeeService.login(user.getUsername(), user.getPassword()).get();
	}

	@GetMapping
	public List<User> findAll() {
		return employeeService.getAll();
	}

	@GetMapping("/{id}")
	public User findById(@PathVariable("id") long id) {
		Optional<User> employee = employeeService.getById(id);
		return employee.isPresent()?employee.get():null;
	}

	@PostMapping
	public void save(User employee) throws AbuMazrouqDashboardException {
		employeeService.add(employee);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") long id) throws AbuMazrouqDashboardException {
		employeeService.delete(id);
	}

	public User findByName(String name) {
		return employeeService.getByName(name).get();
	}
}
