package com.mbaker.abumazrouqdashboard.daos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.mbaker.abumazrouqdashboard.beans.model.Category;
import com.mbaker.abumazrouqdashboard.beans.model.Item;

@Repository
@EnableJpaRepositories
public interface ItemDAO extends JpaRepository<Item, Long> {
	
	public Optional<Item> findByName(String name);
	
	public List<Item> findByCategory(Category category);
}
