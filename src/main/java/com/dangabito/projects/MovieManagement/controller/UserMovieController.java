package com.dangabito.projects.MovieManagement.controller;

import java.io.UnsupportedEncodingException;
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

import com.dangabito.projects.MovieManagement.dto.request.SaveUser;
import com.dangabito.projects.MovieManagement.dto.response.GetUser;
import com.dangabito.projects.MovieManagement.exception.ObjectNotfoundException;
import com.dangabito.projects.MovieManagement.service.UserMovieService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserMovieController {

	private static Logger logger = LoggerFactory.getLogger(UserMovieController.class);

	private UserMovieService userMovieService;

	@GetMapping
	public ResponseEntity<List<GetUser>> findAll(@RequestParam(required = false) String name) {

		logger.info("Entre metodo findAll UserMovie");
		List<GetUser> usuarios = null;
		logger.info("Entro NAME:{}", name);
		if (StringUtils.hasText(name)) {
			usuarios = userMovieService.findAllByName(name);
		} else {
			logger.info("Entro TODOS");
			usuarios = userMovieService.findAll();
		}
		return ResponseEntity.ok(usuarios);
	}

	@GetMapping(value = "/{username}")
	public ResponseEntity<GetUser> findOneByUsername(@PathVariable("username") String username) {
			return ResponseEntity.ok(userMovieService.findOneByUsernameMovie(username));
	}

	@PostMapping
	public ResponseEntity<GetUser> createOne(@Valid @RequestBody SaveUser saveDto, HttpServletRequest request)
			throws UnsupportedEncodingException {
		GetUser userMovieCreate = userMovieService.creteOne(saveDto);
		String baseUrl = request.getRequestURL().toString();
		URI newLocationUri = URI.create(baseUrl + "/" + saveDto.username());
		return ResponseEntity.created(newLocationUri).body(userMovieCreate);
	}

	@PutMapping(value = "/{username}")
	public ResponseEntity<GetUser> updateOneByUsername(@PathVariable String username,
			@Valid @RequestBody SaveUser saveDto) {
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

	@Autowired
	public void setUserMovieService(UserMovieService userMovieService) {
		this.userMovieService = userMovieService;
	}
}