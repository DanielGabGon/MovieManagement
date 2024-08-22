package com.dangabito.projects.MovieManagement.controller;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dangabito.projects.MovieManagement.dto.request.MovieSearchCriteria;
import com.dangabito.projects.MovieManagement.dto.request.SaveMovie;
import com.dangabito.projects.MovieManagement.dto.response.GetMovie;
import com.dangabito.projects.MovieManagement.service.MovieService;
import com.dangabito.projects.MovieManagement.util.MovieGenre;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;



@RestController
@RequestMapping("/movies")
public class MovieController {

	private static Logger logger = LoggerFactory.getLogger(MovieController.class);

	private MovieService movieService;

	@Autowired
	public void setMovieService(MovieService movieService) {
		this.movieService = movieService;
	}

	@GetMapping
	public ResponseEntity<Page<GetMovie>> findAll(@RequestParam(required = false) String title,
			@RequestParam(required = false) MovieGenre genre,
			@RequestParam(required = false, value = "min_release_year") Integer minReleaseYear,
			@RequestParam(required = false, value = "max_release_year") Integer maxReleaseYear,
			@RequestParam(required = false, value = "min_average_rating") Integer minAverageRating,
			@RequestParam(required = false, defaultValue = "0") Integer pageNumber,
			@RequestParam(required = false, defaultValue = "10") Integer pageSize,
			@RequestParam(required = false, defaultValue = "id") String sortBy) {
		logger.info("ENTRE AL MÉTODO FINDALL CON PARÁMETROS ");

		Sort movieSort = Sort.by(sortBy.trim());
		Pageable moviePagable = PageRequest.of(pageNumber, pageSize, movieSort);

		MovieSearchCriteria serachCriteria = new MovieSearchCriteria(title, genre, minReleaseYear, maxReleaseYear,
				minAverageRating);
		Page<GetMovie> movies = null;
		movies = movieService.findAll(serachCriteria, moviePagable);
		return ResponseEntity.ok(movies);
	}

	/**
	 * Parametros
	 * 
	 * @param title @RequestParam(required = false, name = "titulo") String title
	 * @param genre @RequestParam(required = false, name = "genero") MovieGenre
	 *              genre
	 * @return
	 */
//	@GetMapping
//	public ResponseEntity<List<GetMovie>> findAll(@RequestParam(required = false) String title,
//			@RequestParam(required = false) MovieGenre genre) {
//		logger.info("ENTRE AL MÉTODO FINDALL CON PARÁMETROS ");
//		List<GetMovie> movies = null;
//
//		// title!=null && title.isBlank() asi o como esta en el if
//		if (StringUtils.hasText(title) && genre != null) {
//			movies = movieService.findAllByGenreAndTitle(genre, title);
//		} else if (StringUtils.hasText(title)) {
//			movies = movieService.findAllByTitle(title);
//		} else if (genre != null) {
//			movies = movieService.findAllByGenre(genre);
//		} else {
//			movies = movieService.findAll();
//		}
//		return ResponseEntity.ok(movies);
//	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<GetMovie> findOneById(@PathVariable Long id) {
		logger.info("LANZANDO GET EN BUSQUEDA :{}", id);
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
			GetMovie updateMovie = movieService.updateOneById(id, saveDto);
			return ResponseEntity.ok(updateMovie);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteOneById(@PathVariable Long id) {
			movieService.deleteOneById(id);
			return ResponseEntity.noContent().build();
	}
}
