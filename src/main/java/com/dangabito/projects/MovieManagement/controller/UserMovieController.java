package com.dangabito.projects.MovieManagement.controller;

import java.io.UnsupportedEncodingException;
import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import com.dangabito.projects.MovieManagement.dto.request.SaveUser;
import com.dangabito.projects.MovieManagement.dto.response.GetUser;
import com.dangabito.projects.MovieManagement.service.UserMovieService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserMovieController {

	private static Logger logger = LoggerFactory.getLogger(UserMovieController.class);

	private UserMovieService userMovieService;

	@GetMapping
	public ResponseEntity<Page<GetUser>> findAll(@RequestParam(required = false) String name,
			Pageable pageable) {
		logger.info("Entre metodo findAll UserMovie");

		Page<GetUser> usuarios = userMovieService.findAll(name, pageable);

		return ResponseEntity.ok(usuarios);
	}

	@GetMapping(value = "/{username}")
	public ResponseEntity<GetUser> findOneByUsername(@PathVariable("username") String username) {
		return ResponseEntity.ok(userMovieService.findOneByUsernameMovie(username));
	}

	@PostMapping
	public ResponseEntity<GetUser> createOne(@Valid @RequestBody SaveUser saveDto, HttpServletRequest request)
			throws UnsupportedEncodingException {
		logger.info("INICIANDO..!");
		GetUser userMovieCreate = userMovieService.creteOne(saveDto);
		String baseUrl = request.getRequestURL().toString();
		URI newLocationUri = URI.create(baseUrl + "/" + saveDto.username());
		return ResponseEntity.created(newLocationUri).body(userMovieCreate);
	}

	@PutMapping(value = "/{username}")
	public ResponseEntity<GetUser> updateOneByUsername(@PathVariable String username,
			@Valid @RequestBody SaveUser saveDto) {
		GetUser updateMovie = userMovieService.updateOneByUserName(username, saveDto);
		return ResponseEntity.ok(updateMovie);
	}

	@DeleteMapping(value = "/{username}")
	public ResponseEntity<Void> deleteOneByUsername(@PathVariable String username) {
		userMovieService.deleteOneByUsername(username);
		return ResponseEntity.noContent().build();
	}

	@Autowired
	public void setUserMovieService(UserMovieService userMovieService) {
		this.userMovieService = userMovieService;
	}
}