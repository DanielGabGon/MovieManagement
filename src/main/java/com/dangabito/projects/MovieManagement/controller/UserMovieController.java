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
import com.dangabito.projects.MovieManagement.persistence.entity.UserMovie;
import com.dangabito.projects.MovieManagement.service.UserMovieService;

@RestController
@RequestMapping("/users")
public class UserMovieController {

	@Autowired
	private UserMovieService userMovieService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<UserMovie>> findAll(@RequestParam(required = false) String name) {

		System.out.println("Entre metodo findAll UserMovie");

		List<UserMovie> usuarios = null;

		if (StringUtils.hasText(name)) {
			System.out.println("Entro NAME:" + name);
			usuarios = userMovieService.findAllByName(name);
		} else {
			System.out.println("Entro TODOS");
			usuarios = userMovieService.findAll();
		}
		return ResponseEntity.ok(usuarios);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{username}")
	public ResponseEntity<UserMovie> findOneByUsername(@PathVariable("username") String username) {
		try {
			return ResponseEntity.ok(userMovieService.findOneByUsernameMovie(username));
		} catch (ObjectNotfoundException e) {
			// return ResponseEntity.status(404).build();
			return ResponseEntity.notFound().build();
		}
	}

}