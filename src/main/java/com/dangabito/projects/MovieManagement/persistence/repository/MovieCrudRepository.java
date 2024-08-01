package com.dangabito.projects.MovieManagement.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dangabito.projects.MovieManagement.persistence.entity.Movie;
import com.dangabito.projects.MovieManagement.util.MovieGenre;

public interface MovieCrudRepository extends JpaRepository<Movie, Long> {

	List<Movie> findByTitleContaining(String title);

	List<Movie> findByGenre(MovieGenre genre);

	List<Movie> findByGenreAndTitleContaining(MovieGenre genre, String title);

}
