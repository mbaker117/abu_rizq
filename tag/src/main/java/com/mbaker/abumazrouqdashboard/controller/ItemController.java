package com.mbaker.abumazrouqdashboard.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mbaker.abumazrouqdashboard.beans.model.Item;
import com.mbaker.abumazrouqdashboard.exception.AbuMazrouqDashboardException;
import com.mbaker.abumazrouqdashboard.services.ItemService;

@RestController
@RequestMapping("/items")
public class ItemController {

	@Autowired
	private ItemService itemService;

	@GetMapping
	public List<Item> getAll() {
		return itemService.getAll();
	}

	@GetMapping("/{id}")
	public Item getById(@PathVariable("id") long id) {
		Optional<Item> item = itemService.getById(id);
		return item.isPresent()?item.get():null;
	}

	@PostMapping
	public void save(Item item) {
		itemService.save(item);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") long id) throws AbuMazrouqDashboardException {
		itemService.delete(id);
	}
	
	@GetMapping("/{name}")
	public Item getByName(@PathVariable("name") String name) {
		return itemService.getByName(name).get();
	}

	@GetMapping("/{categoryId}")
	public List<Item> getByCategory(@PathVariable("name") long categoryId) throws AbuMazrouqDashboardException {
		return itemService.getByCategory(categoryId);
	}

}
