package com.mbaker.abumazrouqdashboard.daos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.mbaker.abumazrouqdashboard.beans.model.Admin;

@Repository
@EnableJpaRepositories
public interface AdminDAO extends JpaRepository<Admin, Long>  {
	
	public Optional<Admin> findByUsernameAndPassword(String username, String password);

}
