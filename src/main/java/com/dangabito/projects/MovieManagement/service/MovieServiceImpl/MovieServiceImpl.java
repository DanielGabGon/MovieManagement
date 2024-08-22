package com.dangabito.projects.MovieManagement.service.MovieServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dangabito.projects.MovieManagement.dto.request.MovieSearchCriteria;
import com.dangabito.projects.MovieManagement.dto.request.SaveMovie;
import com.dangabito.projects.MovieManagement.dto.response.GetMovie;
import com.dangabito.projects.MovieManagement.exception.ObjectNotfoundException;
import com.dangabito.projects.MovieManagement.mapper.MovieMapper;
import com.dangabito.projects.MovieManagement.persistence.entity.Movie;
import com.dangabito.projects.MovieManagement.persistence.repository.MovieCrudRepository;
import com.dangabito.projects.MovieManagement.persistence.specification.FindAllMoviesSpecification;
import com.dangabito.projects.MovieManagement.service.MovieService;



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


	@Override
	public Page<GetMovie> findAll(MovieSearchCriteria movieSearchCriteria, Pageable pageable) {
		FindAllMoviesSpecification moviesSpecification = new FindAllMoviesSpecification(movieSearchCriteria);
		Page<Movie> entities = movieCrudRepository.findAll(moviesSpecification, pageable);
//	 	return MovieMapper.toGetDtoList(entities);
		return entities.map(entity -> MovieMapper.toGetDto(entity)); // return entities.map(MovieMapper::toGetDto);
	}
	 
	@Transactional(readOnly = true)
	private Movie findOneEntityById(Long id) {
		return movieCrudRepository.findById(id).orElseThrow(() -> new ObjectNotfoundException("[movie" + (id) + "]"));
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
