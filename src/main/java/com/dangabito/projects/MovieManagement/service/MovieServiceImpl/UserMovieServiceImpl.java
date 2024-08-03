package com.dangabito.projects.MovieManagement.service.MovieServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.dangabito.projects.MovieManagement.exception.ObjectNotfoundException;
import com.dangabito.projects.MovieManagement.persistence.entity.UserMovie;
import com.dangabito.projects.MovieManagement.persistence.repository.UserMovieCrudRepository;
import com.dangabito.projects.MovieManagement.service.UserMovieService;

public class UserMovieServiceImpl implements UserMovieService {

	@Autowired
	private UserMovieCrudRepository userMovieCrudRepository;

	@Override
	public List<UserMovie> findAll() {
		return userMovieCrudRepository.findAll();
	}

	@Override
	public List<UserMovie> findAllByName(String name) {
		return userMovieCrudRepository.findByNameContaining(name);
	}

	@Override
	public UserMovie findOneByUsernameMovie(String username) {
		return userMovieCrudRepository.findByUsername(username).orElseThrow(()->new ObjectNotfoundException("[userMovie:"+username+"]");
	}

	@Override
	public UserMovie updateOneByUserName(String username, UserMovie userMovie) {
		UserMovie oldUserMovie = this.findOneByUsernameMovie(username);
		oldUserMovie.setName(userMovie.getName());
		oldUserMovie.setPassword(userMovie.getPassword());

		return userMovieCrudRepository.save(oldUserMovie);
	}

	@Override
	public UserMovie creteOne(UserMovie userMovie) {
		return userMovieCrudRepository.save(userMovie);
	}

	@Override
	public void deleteOneByUsername(String username) {
//		UserMovie userMovie=this.findOneByUsernameMovie(username);
//		userMovieCrudRepository.delete(userMovie);

		int deleteRecords = userMovieCrudRepository.deleteByUsername(username);
		if (deleteRecords != 1) {
			throw new ObjectNotfoundException("[userMovie:" + username + "]");
		}

	}

}
