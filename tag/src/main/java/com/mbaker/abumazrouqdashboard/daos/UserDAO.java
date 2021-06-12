package com.mbaker.abumazrouqdashboard.daos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.mbaker.abumazrouqdashboard.beans.model.User;
import com.mbaker.abumazrouqdashboard.enums.UserType;

@Repository
@EnableJpaRepositories
public interface UserDAO  extends JpaRepository<User, Long>{
	
	public Optional<User> findByUsernameAndPassword(String username, String password);
	
	public Optional<User> findByUsername(String username);
	
	public Optional<User> findByName(String name);
	
	public List<User> findByUserType(UserType userType);


}
