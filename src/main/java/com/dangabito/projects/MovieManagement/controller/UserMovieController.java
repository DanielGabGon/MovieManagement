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

import com.dangabito.projects.MovieManagement.dto.request.SaveUser;
import com.dangabito.projects.MovieManagement.dto.response.GetUser;
import com.dangabito.projects.MovieManagement.exception.ObjectNotfoundException;
import com.dangabito.projects.MovieManagement.service.UserMovieService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users")
public class UserMovieController {

	@Autowired
	private UserMovieService userMovieService;

	@GetMapping
	public ResponseEntity<List<GetUser>> findAll(@RequestParam(required = false) String name) {

		System.out.println("Entre metodo findAll UserMovie");

		List<GetUser> usuarios = null;

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
	public ResponseEntity<GetUser> findOneByUsername(@PathVariable("username") String username) {
		try {
			return ResponseEntity.ok(userMovieService.findOneByUsernameMovie(username));
		} catch (ObjectNotfoundException e) {
			// return ResponseEntity.status(404).build();
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	public ResponseEntity<GetUser> createOne(@RequestBody SaveUser saveDto, HttpServletRequest request) {
		GetUser userMovieCreate = userMovieService.creteOne(saveDto);
		String baseUrl = request.getRequestURL().toString();
		URI newLocationUri = URI.create(baseUrl + "/" + saveDto.username());
		return ResponseEntity.created(newLocationUri).body(userMovieCreate);
	}

	@PutMapping(value = "/{username}")
	public ResponseEntity<GetUser> updateOneByUsername(@PathVariable String username, @RequestBody SaveUser saveDto) {
		try {
			GetUser updateMovie = userMovieService.updateOneByUserName(username, saveDto);
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