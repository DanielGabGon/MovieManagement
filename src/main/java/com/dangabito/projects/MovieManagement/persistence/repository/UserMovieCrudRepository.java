package com.dangabito.projects.MovieManagement.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.dangabito.projects.MovieManagement.persistence.entity.UserMovie;

public interface UserMovieCrudRepository extends JpaRepository<UserMovie, Long> {

	List<UserMovie> findByNameContaining(String name);
	
	Optional<UserMovie> findByUsername(String username);

	// Esta anotación se pone para indicar que no es consulta.
	@Modifying
	int deleteByUsername(String username);


}
