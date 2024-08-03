package com.dangabito.projects.MovieManagement.service;

import java.util.List;

import com.dangabito.projects.MovieManagement.persistence.entity.Movie;
import com.dangabito.projects.MovieManagement.util.MovieGenre;

public interface MovieService {
	
	// Los metodos de servicio
	// se tienen que llamar igual que los metodos de repositorio.
	List<Movie> findAll();

	List<Movie> findAllByTitle(String title);

	List<Movie> findAllByGenre(MovieGenre genre);

	List<Movie> findAllByGenreAndTitle(MovieGenre genre, String title);

	Movie findOneById(Long id);

	Movie updateOneById(Long id, Movie movie);

	Movie createOne(Movie movie);

	void deleteOneById(Long id);

}
