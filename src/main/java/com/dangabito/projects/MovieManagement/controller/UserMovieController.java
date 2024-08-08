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
import com.dangabito.projects.MovieManagement.persistence.entity.UserMovie;
import com.dangabito.projects.MovieManagement.service.UserMovieService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users")
public class UserMovieController {

	@Autowired
	private UserMovieService userMovieService;

	@GetMapping
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

	@GetMapping(value = "/{username}")
	public ResponseEntity<UserMovie> findOneByUsername(@PathVariable("username") String username) {
		try {
			return ResponseEntity.ok(userMovieService.findOneByUsernameMovie(username));
		} catch (ObjectNotfoundException e) {
			// return ResponseEntity.status(404).build();
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	public ResponseEntity<UserMovie> createOne(@RequestBody UserMovie newUserMovie, HttpServletRequest request) {
		UserMovie userMovieCreate = userMovieService.creteOne(newUserMovie);
		String baseUrl = request.getRequestURL().toString();
		URI newLocationUri = URI.create(baseUrl + "/" + userMovieCreate.getUsername());
		return ResponseEntity.created(newLocationUri).body(userMovieCreate);
	}

	@PutMapping(value = "/{username}")
	public ResponseEntity<UserMovie> updateOneByUsername(@PathVariable String username,
			@RequestBody UserMovie userMovie) {
		try {
			UserMovie updateMovie = userMovieService.updateOneByUserName(username, userMovie);
			return ResponseEntity.ok(updateMovie);
		} catch (ObjectNotfoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping(value = "/{username}")
	public ResponseEntity<Void> deleteOneByUsername(@PathVariable String username) {
		try {
			userMovieService.deleteOneByUsername(username);
			return ResponseEntity.noContent().build();
		} catch (ObjectNotfoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

}