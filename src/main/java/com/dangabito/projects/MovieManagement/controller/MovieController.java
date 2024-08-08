package com.dangabito.projects.MovieManagement.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dangabito.projects.MovieManagement.exception.ObjectNotfoundException;
import com.dangabito.projects.MovieManagement.persistence.entity.Movie;
import com.dangabito.projects.MovieManagement.service.MovieService;
import com.dangabito.projects.MovieManagement.util.MovieGenre;

import jakarta.servlet.http.HttpServletRequest;



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
	@GetMapping
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

		// HttpHeaders headers = new HttpHeaders();

		// return new ResponseEntity<List<Movie>>(peliculas, headers, HttpStatus.OK);//
		// opción 1
		// return ResponseEntity.status(HttpStatus.OK).body(peliculas); // opciòn 2
		return ResponseEntity.ok(peliculas);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Movie> findOneById(@PathVariable Long id) {
		System.out.println("BUSCANDO EL id:" + id);
		try {
			return ResponseEntity.ok(movieService.findOneById(id));

		} catch (ObjectNotfoundException e) {
			// return ResponseEntity.status(404).build();
			return ResponseEntity.notFound().build();
		}
	}


//	@PostMapping
	public ResponseEntity<Movie> createOneV1(@RequestParam String title, @RequestParam String director,
			@RequestParam MovieGenre genre, @RequestParam int releaseYear, HttpServletRequest request) {
		System.out.println("ENTRO AL METODO GUARDAR:" + releaseYear);
		Movie newMovie = new Movie();
		newMovie.setTitle(title);
		newMovie.setDirector(director);
		newMovie.setGenre(genre);
		newMovie.setReleaseYear(releaseYear);
		Movie movieCreatedMovie = movieService.createOne(newMovie);
		String baseUrl = request.getRequestURL().toString();
		URI newLocation = URI.create(baseUrl + "/" + movieCreatedMovie.getId());

		return ResponseEntity.created(newLocation).body(movieCreatedMovie);
	}

	@PostMapping
	public ResponseEntity<Movie> createOne(@RequestBody Movie newMovie, HttpServletRequest request) {
		System.out.println("ENTRO AL METODO GUARDAR:");

		Movie movieCreatedMovie = movieService.createOne(newMovie);
		String baseUrl = request.getRequestURL().toString();
		URI newLocation = URI.create(baseUrl + "/" + movieCreatedMovie.getId());
		
		return ResponseEntity.created(newLocation).body(movieCreatedMovie);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Movie> updateOneById(@PathVariable Long id, @RequestBody Movie movie) {
		try {
			Movie updateMovie = movieService.updateOneById(id, movie);
			return ResponseEntity.ok(updateMovie);
		} catch (ObjectNotfoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteOneById(@PathVariable Long id) {
		try {
			movieService.deleteOneById(id);
			return ResponseEntity.noContent().build();
		} catch (ObjectNotfoundException e) {
			return ResponseEntity.notFound().build();
		}
	}


}
