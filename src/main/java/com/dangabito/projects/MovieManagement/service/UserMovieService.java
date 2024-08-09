package com.dangabito.projects.MovieManagement.service;

import java.util.List;

import com.dangabito.projects.MovieManagement.persistence.entity.UserMovie;

public interface UserMovieService {

	List<UserMovie> findAll();

	List<UserMovie> findAllByName(String name);

	UserMovie findOneByUsernameMovie(String username);

	UserMovie updateOneByUserName(String username, UserMovie userMovie);

	UserMovie creteOne(UserMovie userMovie);

	void deleteOneByUsername(String username);


}
