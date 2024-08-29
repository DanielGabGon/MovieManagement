package com.dangabito.projects.MovieManagement.persistence.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.dangabito.projects.MovieManagement.persistence.entity.Movie;
import com.dangabito.projects.MovieManagement.util.MovieGenre;

public interface MovieCrudRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {
/////////// Opción 1 /////////////////////////

	/**
	 * Opción general con Sort para ordenar: Utilización de objetos de configuración
	 * Sort y sin paginación.
	 * 
	 * @param genre
	 * @param sort
	 * @return
	 */
	List<Movie> findAllByGenre(MovieGenre genre, Sort sort);

	/**
	 * Utilización de objetos de configuración Sort y Pageable paginación.
	 * 
	 * @param genre
	 * @param pageable
	 * @return
	 */
	Page<Movie> findAllByGenre(MovieGenre genre, Pageable pageable);

	/////////// Opción 2/////////////////////////
	/**
	 * Utilización de palabras reservadas order by sin paginación.
	 * 
	 * @param genre
	 * @return
	 */
	List<Movie> findAllByGenreOrderByTitle(MovieGenre genre);

	/**
	 * Utilización de palabras reservadas order by con paginación.
	 * 
	 * @param genre
	 * @param pageable
	 * @return
	 */
	Page<Movie> findAllByGenreOrderByTitle(MovieGenre genre, Pageable pageable);


}
