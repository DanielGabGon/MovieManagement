package com.dangabito.projects.MovieManagement.service.MovieServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dangabito.projects.MovieManagement.exception.ObjectNotfoundException;
import com.dangabito.projects.MovieManagement.persistence.entity.Movie;
import com.dangabito.projects.MovieManagement.persistence.repository.MovieCrudRepository;
import com.dangabito.projects.MovieManagement.service.MovieService;
import com.dangabito.projects.MovieManagement.util.MovieGenre;

@Service
public class MovieServiceImpl implements MovieService {

	@Autowired
	private MovieCrudRepository movieCrudRepository;

	/**
	 * Este viene de la Interface JpaRepository
	 */
	@Override
	public List<Movie> findAll() {
		return movieCrudRepository.findAll();
	}

	/**
	 * Este viene de la Interface MovieCrudRepository
	 */
	@Override
	public List<Movie> findAllByTitle(String title) {
		return movieCrudRepository.findByTitleContaining(title);
	}

	/**
	 * /**
	 * Este viene de la Interface MovieCrudRepository
	 */
	@Override
	public List<Movie> findAllByGenre(MovieGenre genre) {
		return movieCrudRepository.findByGenre(genre);
	}

	/**
	 * /**
	 * Este viene de la Interface MovieCrudRepository
	 */

	@Override
	public List<Movie> findAllByGenreAndTitle(MovieGenre genre, String title) {
		return movieCrudRepository.findByGenreAndTitleContaining(genre, title);
	}
	 
	 @Override
	 public Movie findOneById(Long id) {
			System.out.println("ENTRAMOS A BUSCAR:" + id);
			Movie movie = movieCrudRepository.findById(id).orElseThrow(
					() ->
					new ObjectNotfoundException("[movie" + (id) + "]")
			);
		 return movie;
	 }

	/**
	 * 
	 */
	@Override
	public Movie updateOneById(Long id, Movie newMovie) {
		
		Movie oldMovie=this.findOneById(id);
		
		oldMovie.setGenre(newMovie.getGenre());
		oldMovie.setReleaseYear(newMovie.getReleaseYear());
		oldMovie.setTitle(newMovie.getTitle());
		oldMovie.setDirector(newMovie.getDirector());
		
		return movieCrudRepository.save(oldMovie);
	}
	
	/**
	 * Este viene de la Interface JpaRepository
	 */
	@Override
	public Movie createOne(Movie movie) {
		return movieCrudRepository.save(movie);
	}

	/**
	 * 
	 */
	@Override
	public void deleteOneById(Long id) {
		Movie movie= this.findOneById(id);
		movieCrudRepository.delete(movie);
	}
}
