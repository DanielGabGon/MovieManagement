package com.dangabito.projects.MovieManagement.service.MovieServiceImpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.dangabito.projects.MovieManagement.dto.request.SaveMovie;
import com.dangabito.projects.MovieManagement.dto.response.GetMovie;
import com.dangabito.projects.MovieManagement.mapper.MovieMapper;
import com.dangabito.projects.MovieManagement.persistence.entity.Movie;
import com.dangabito.projects.MovieManagement.persistence.repository.MovieCrudRepository;
import com.dangabito.projects.MovieManagement.service.MovieService;
import com.dangabito.projects.MovieManagement.util.MovieGenre;



@Service
@Transactional
public class MovieServiceImpl implements MovieService {

	private static Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);
	public MovieServiceImpl() {
	}


	private MovieCrudRepository movieCrudRepository;

	@Autowired
	public void setMovieCrudRepository(MovieCrudRepository movieCrudRepository) {
		this.movieCrudRepository = movieCrudRepository;
	}

	/**
	 * Este viene de la Interface JpaRepository
	 */
	@Override
	@Transactional(readOnly = true)
	public List<GetMovie> findAll() {
		List<Movie> entities = movieCrudRepository.findAll();
		return MovieMapper.toGetDtoList(entities);
	}

	/**
	 * Este viene de la Interface MovieCrudRepository
	 */
	@Override
	@Transactional(readOnly = true)
	public List<GetMovie> findAllByTitle(String title) {
		List<Movie> entities = movieCrudRepository.findByTitleContaining(title);
		return MovieMapper.toGetDtoList(entities);
	}

	/**
	 * /**
	 * Este viene de la Interface MovieCrudRepository
	 */
	@Override
	@Transactional(readOnly = true)
	public List<GetMovie> findAllByGenre(MovieGenre genre) {
		List<Movie> entities = movieCrudRepository.findByGenre(genre);
		return MovieMapper.toGetDtoList(entities);
	}

	/**
	 * /**
	 * Este viene de la Interface MovieCrudRepository
	 */

	@Override
	@Transactional(readOnly = true)
	public List<GetMovie> findAllByGenreAndTitle(MovieGenre genre, String title) {
		List<Movie> entities = movieCrudRepository.findByGenreAndTitleContaining(genre, title);
		return MovieMapper.toGetDtoList(entities);
	}
	 
	@Transactional(readOnly = true)
	private Movie findOneEntityById(Long id) {
		return movieCrudRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Movie not found:" + id));

//				.findById(id).orElseThrow(() -> new ObjectNotfoundException("[movie" + (id) + "]"));
	}

	@Override
	@Transactional(readOnly = true)
	public GetMovie findOneById(Long id) {
		logger.info("ENTRAMOS A BUSCAR:{}", id);
		return MovieMapper.toGetDto(findOneEntityById(id));
	}


	/**
	 * 
	 */
	@Override
	public GetMovie updateOneById(Long id, SaveMovie saveDto) {
		Movie oldMovie = findOneEntityById(id);
		MovieMapper.updateEntity(oldMovie, saveDto);
		return MovieMapper.toGetDto(movieCrudRepository.save(oldMovie));
	}
	
	/**
	 * Este viene de la Interface JpaRepository
	 */
	@Override
	public GetMovie createOne(SaveMovie saveDto) {
		logger.info("OBJETO:{}", saveDto);
		Movie newMovie = MovieMapper.toEntity(saveDto);
		return MovieMapper.toGetDto(movieCrudRepository.save(newMovie));
	}

	/**
	 * 
	 */
	@Override
	public void deleteOneById(Long id) {
		Movie movie = findOneEntityById(id);
		movieCrudRepository.delete(movie);
	}

}
