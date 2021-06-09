package com.mbaker.abumazrouqdashboard.daos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.mbaker.abumazrouqdashboard.beans.model.Category;

@Repository
@EnableJpaRepositories
public interface CategoryDAO extends JpaRepository<Category, Long> {
	
	public Optional<Category> findByName(String name);

}
