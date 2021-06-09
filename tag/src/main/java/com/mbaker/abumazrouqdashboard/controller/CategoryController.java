package com.mbaker.abumazrouqdashboard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mbaker.abumazrouqdashboard.beans.model.Category;
import com.mbaker.abumazrouqdashboard.exception.AbuMazrouqDashboardException;
import com.mbaker.abumazrouqdashboard.services.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping
	public List<Category> findAll(){
		return categoryService.getAll();
	}
	
	@GetMapping("/{id}")
	public Category findById(@PathVariable("id") long id){
		return categoryService.getById(id).get();
	}
	
	@PostMapping
	public void save(Category category) {
		categoryService.save(category);
	}
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id")long id) throws AbuMazrouqDashboardException{
		categoryService.delete(id);
	}

}
