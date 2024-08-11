package com.dangabito.projects.MovieManagement.service;

import java.util.List;

import com.dangabito.projects.MovieManagement.dto.request.SaveUser;
import com.dangabito.projects.MovieManagement.dto.response.GetUser;

public interface UserMovieService {

	List<GetUser> findAll();

	List<GetUser> findAllByName(String name);

	GetUser findOneByUsernameMovie(String username);

	GetUser updateOneByUserName(String username, SaveUser saveDto);

	GetUser creteOne(SaveUser saveDto);

	void deleteOneByUsername(String username);


}
