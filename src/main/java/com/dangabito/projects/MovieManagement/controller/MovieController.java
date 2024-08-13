package com.dangabito.projects.MovieManagement.controller;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.dangabito.projects.MovieManagement.dto.request.SaveMovie;
import com.dangabito.projects.MovieManagement.dto.response.GetMovie;
import com.dangabito.projects.MovieManagement.exception.ObjectNotfoundException;
import com.dangabito.projects.MovieManagement.service.MovieService;
import com.dangabito.projects.MovieManagement.util.MovieGenre;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;



@RestController
@RequestMapping("/movies")
public class MovieController {

	private static Logger logger = LoggerFactory.getLogger(MovieController.class);

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
	public ResponseEntity<List<GetMovie>> findAll(@RequestParam(required = false) String title,
			@RequestParam(required = false) MovieGenre genre) {
		logger.info("ENTRE AL MÉTODO FINDALL CON PARÁMETROS ");
		List<GetMovie> movies = null;

		// title!=null && title.isBlank() asi o como esta en el if
		if (StringUtils.hasText(title) && genre != null) {
			movies = movieService.findAllByGenreAndTitle(genre, title);
		} else if (StringUtils.hasText(title)) {
			movies = movieService.findAllByTitle(title);
		} else if (genre != null) {
			movies = movieService.findAllByGenre(genre);
		} else {
			movies = movieService.findAll();
		}
		return ResponseEntity.ok(movies);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<GetMovie> findOneById(@PathVariable Long id) {
			return ResponseEntity.ok(movieService.findOneById(id));
	}

	@PostMapping
	public ResponseEntity<GetMovie> createOne(@Valid @RequestBody SaveMovie saveDto, HttpServletRequest request) {
//		logger.info("ENTRO AL METODO GUARDAR:{}", saveDto.availabilityEndTime());
		GetMovie movieCreatedMovie = movieService.createOne(saveDto);
		String baseUrl = request.getRequestURL().toString();
		URI newLocation = URI.create(baseUrl + "/" + movieCreatedMovie.id());
		
		return ResponseEntity.created(newLocation).body(movieCreatedMovie);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<GetMovie> updateOneById(@PathVariable Long id, @Valid @RequestBody SaveMovie saveDto) {
		try {
			GetMovie updateMovie = movieService.updateOneById(id, saveDto);
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


	@Autowired
	public void setMovieService(MovieService movieService) {
		this.movieService = movieService;
	}

}
