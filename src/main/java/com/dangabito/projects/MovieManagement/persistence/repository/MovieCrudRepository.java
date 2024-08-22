package com.dangabito.projects.MovieManagement.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.dangabito.projects.MovieManagement.persistence.entity.Movie;

public interface MovieCrudRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {

//	List<Movie> findByTitleContaining(String title);

//	List<Movie> findByGenre(MovieGenre genre);

//	List<Movie> findByGenreAndTitleContaining(MovieGenre genre, String title);

}
