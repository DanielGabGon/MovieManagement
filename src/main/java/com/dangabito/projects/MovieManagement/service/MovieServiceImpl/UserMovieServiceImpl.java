package com.dangabito.projects.MovieManagement.service.MovieServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dangabito.projects.MovieManagement.exception.ObjectNotfoundException;
import com.dangabito.projects.MovieManagement.persistence.entity.Movie;
import com.dangabito.projects.MovieManagement.persistence.entity.UserMovie;
import com.dangabito.projects.MovieManagement.persistence.repository.MovieCrudRepository;
import com.dangabito.projects.MovieManagement.persistence.repository.UserMovieCrudRepository;
import com.dangabito.projects.MovieManagement.service.UserMovieService;
import com.dangabito.projects.MovieManagement.util.MovieGenre;



@Service
@Transactional
public class UserMovieServiceImpl implements UserMovieService {

	@Autowired
	private UserMovieCrudRepository userMovieCrudRepository;

	@Autowired
	private MovieCrudRepository movieCrudRepository;

	@Override
	@Transactional(readOnly = true)
	public List<UserMovie> findAll() {
		return userMovieCrudRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserMovie> findAllByName(String name) {
		return userMovieCrudRepository.findByNameContaining(name);
	}

	@Override
	@Transactional(readOnly = true)
	public UserMovie findOneByUsernameMovie(String username) {
		return userMovieCrudRepository.findByUsername(username)
				.orElseThrow(() -> new ObjectNotfoundException("[userMovie:" + username + "]"));
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
	@Transactional
	public void deleteOneByUsername(String username) {
//		UserMovie userMovie=this.findOneByUsernameMovie(username);
//		userMovieCrudRepository.delete(userMovie);

		int deleteRecords = userMovieCrudRepository.deleteByUsername(username);
		if (deleteRecords != 1) {
			throw new ObjectNotfoundException("[userMovie:" + username + "]");
		}
	}

	@Override
	public void deleteAll() {
		Movie newMovie = new Movie();
		newMovie.setTitle(null);
		newMovie.setGenre(MovieGenre.ACTION);
		newMovie.setReleaseYear(2004);
		newMovie.setDirector("Sam Reimi");
		userMovieCrudRepository.deleteAll();
		movieCrudRepository.save(newMovie);
	}

}
