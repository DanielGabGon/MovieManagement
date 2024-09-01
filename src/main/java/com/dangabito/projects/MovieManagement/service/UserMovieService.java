package com.dangabito.projects.MovieManagement.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dangabito.projects.MovieManagement.dto.request.SaveUser;
import com.dangabito.projects.MovieManagement.dto.response.GetUser;

public interface UserMovieService {

	Page<GetUser> findAll(String name, Pageable pageable);

	Page<GetUser> findAllByName(String name, Pageable pageable);

	GetUser findOneByUsernameMovie(String username);

	GetUser updateOneByUserName(String username, SaveUser saveDto);

	GetUser creteOne(SaveUser saveDto);

	void deleteOneByUsername(String username);


}
