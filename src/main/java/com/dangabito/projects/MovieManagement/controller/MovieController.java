package com.dangabito.projects.MovieManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dangabito.projects.MovieManagement.exception.ObjectNotfoundException;
import com.dangabito.projects.MovieManagement.persistence.entity.Movie;
import com.dangabito.projects.MovieManagement.service.MovieService;
import com.dangabito.projects.MovieManagement.util.MovieGenre;



@RestController
@RequestMapping("/movies")
public class MovieController {

	@Autowired
	private MovieService movieService;

	/**
	 * Parametros
	 * 
	 * @param title @RequestParam(required = false, name = "titulo") String title
	 * @param genre @RequestParam(required = false, name = "genero") MovieGenre
	 *              genre
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Movie>> findAll(@RequestParam(required = false) String title,
			@RequestParam(required = false) MovieGenre genre) {
		System.out.println("ENTRE AL MÉTODO FINDALL CON PARÁMETROS  ");
		List<Movie> peliculas = null;

		// title!=null && title.isBlank() asi o como esta en el if
		if (StringUtils.hasText(title) && genre != null) {
			peliculas = movieService.findAllByGenreAndTitle(genre, title);
		} else if (StringUtils.hasText(title)) {
			peliculas = movieService.findAllByTitle(title);
		} else if (genre != null) {
			peliculas = movieService.findAllByGenre(genre);
		} else {
			peliculas = movieService.findAll();
		}

//		HttpHeaders headers = new HttpHeaders();

//		return new ResponseEntity<List<Movie>>(peliculas, headers, HttpStatus.OK);// opción 1
//		return ResponseEntity.status(HttpStatus.OK).body(peliculas); // opciòn 2
		return ResponseEntity.ok(peliculas);
	}


	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<Movie> findOneById(@PathVariable Long id) {
		System.out.println("BUSCANDO EL id:" + id);
		try {
			return ResponseEntity.ok(movieService.findOneById(id));

		} catch (ObjectNotfoundException e) {
			// return ResponseEntity.status(404).build();
			return ResponseEntity.notFound().build();
		}
	}
}