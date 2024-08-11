package com.dangabito.projects.MovieManagement.service.MovieServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dangabito.projects.MovieManagement.dto.request.SaveMovie;
import com.dangabito.projects.MovieManagement.dto.response.GetMovie;
import com.dangabito.projects.MovieManagement.exception.ObjectNotfoundException;
import com.dangabito.projects.MovieManagement.mapper.MovieMapper;
import com.dangabito.projects.MovieManagement.persistence.entity.Movie;
import com.dangabito.projects.MovieManagement.persistence.repository.MovieCrudRepository;
import com.dangabito.projects.MovieManagement.service.MovieService;
import com.dangabito.projects.MovieManagement.util.MovieGenre;



@Service
@Transactional
public class MovieServiceImpl implements MovieService {

	@Autowired
	private MovieCrudRepository movieCrudRepository;

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
		return movieCrudRepository.findById(id).orElseThrow(() -> new ObjectNotfoundException("[movie" + (id) + "]"));
	}

	@Override
	@Transactional(readOnly = true)
	public GetMovie findOneById(Long id) {
		System.out.println("ENTRAMOS A BUSCAR:" + id);
		return MovieMapper.toGetDto(this.findOneEntityById(id));
	}


	/**
	 * 
	 */
	@Override
	public GetMovie updateOneById(Long id, SaveMovie saveDto) {
		Movie oldMovie = this.findOneEntityById(id);
		MovieMapper.updateEntity(oldMovie, saveDto);
		return MovieMapper.toGetDto(movieCrudRepository.save(oldMovie));
	}
	
	/**
	 * Este viene de la Interface JpaRepository
	 */
	@Override
	public GetMovie createOne(SaveMovie saveDto) {
		System.out.println("OBJETO:" + saveDto.toString());
		Movie newMovie = MovieMapper.toEntity(saveDto);
		return MovieMapper.toGetDto(movieCrudRepository.save(newMovie));
	}

	/**
	 * 
	 */
	@Override
	public void deleteOneById(Long id) {
		Movie movie = this.findOneEntityById(id);
		movieCrudRepository.delete(movie);
	}
}
