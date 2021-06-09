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

import com.mbaker.abumazrouqdashboard.beans.model.Admin;
import com.mbaker.abumazrouqdashboard.beans.request.UserCredential;
import com.mbaker.abumazrouqdashboard.exception.AbuMazrouqDashboardException;
import com.mbaker.abumazrouqdashboard.services.AdminService;

@RestController
@RequestMapping("/admins")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@PostMapping("/login")
	public Admin login(@RequestBody UserCredential user){
		return adminService.login(user.getUsername(), user.getPassword()).get();	
	}
	
	@GetMapping
	public List<Admin> findAll(){
		return adminService.findAll();
	}
	
	@GetMapping("/{id}")
	public Optional<Admin> findById(@PathVariable("id") long id){
		return adminService.findById(id);
	}
	
	@PostMapping
	public void save(Admin admin) {
		adminService.save(admin);
	}
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id")long id) throws AbuMazrouqDashboardException{
		adminService.delete(id);
	}

}
