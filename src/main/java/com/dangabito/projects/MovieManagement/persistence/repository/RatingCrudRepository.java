package com.dangabito.projects.MovieManagement.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dangabito.projects.MovieManagement.persistence.entity.Rating;

public interface RatingCrudRepository extends JpaRepository<Rating, Long> {

	List<Rating> findByMovieId(Long id);

	List<Rating> findByUserMovieUsername(String username);

	// Este es equivalente al de arriba
	@Query("SELECT r From Rating r JOIN r.user u WHERE u.username=:username")
	List<Rating> findByUsername(String username);

}
