package com.dangabito.projects.MovieManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	public List<Movie> findAll(@RequestParam(required = false) String title,
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
		return peliculas;
	}

//	@RequestMapping(method = RequestMethod.GET, params = { "title", "genre" })
//	public List<Movie> findAllByGenreAndTitle(@RequestParam(required = false) String title,
//			@RequestParam(required = false) MovieGenre genre) {
//		System.out.println("Método:  findAllByGenreAndTitle");
//		return movieService.findAllByGenreAndTitle(genre, title);
//	}
//
//	@RequestMapping(method = RequestMethod.GET, params = { "title" })
//	public List<Movie> findAllByTitle(@RequestParam(required = false) String title) {
//		System.out.println("Método:  findAllByTitle");
//		return movieService.findAllByTitle(title);
//	}
//
//	@RequestMapping(method = RequestMethod.GET, params = { "genre" })
//	public List<Movie> findAllByGenre(@RequestParam(required = false) MovieGenre genre) {
//		System.out.println("Método:  findAllByGenre");
//		return movieService.findAllByGenre(genre);
//	}
//
//	@RequestMapping(method = RequestMethod.GET, params = { "!title", "!genre" })
//	public List<Movie> findAll() {
//		System.out.println("Método:  findAll");
//		return movieService.findAll();
//	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public Movie findOneById(@PathVariable Long id) {
		System.out.println("BUSCANDO EL id:" + id);
		return movieService.findOneById(id);
	}

}