package com.dangabito.projects.MovieManagement.mapper;

import java.util.Collections;
import java.util.List;

import com.dangabito.projects.MovieManagement.dto.request.SaveMovie;
import com.dangabito.projects.MovieManagement.dto.response.GetMovie;
import com.dangabito.projects.MovieManagement.persistence.entity.Movie;

public class MovieMapper {

	public MovieMapper() {
	}

	public static GetMovie toGetDto(Movie entity) {
		if (entity == null)
			return null;

		return new GetMovie(entity.getId(), entity.getTitle(), entity.getDirector(), entity.getGenre(),
				entity.getReleaseYear(), RatingMapper.toGetMovieRatingDtoList(entity.getRatings()));
	}

	public static List<GetMovie> toGetDtoList(List<Movie> entities) {
		if (entities == null)
			return Collections.emptyList();
		return entities.stream().map(each -> MovieMapper.toGetDto(each)).toList(); // entities.stream().map(MovieMapper::toGetDto).toList()
	}

	public static Movie toEntity(SaveMovie saveDto) {
		if (saveDto == null)
			return null;
		Movie newMovie = new Movie();
		newMovie.setTitle(saveDto.title());
		newMovie.setDirector(saveDto.director());
		newMovie.setReleaseYear(saveDto.releaseYear());
		newMovie.setGenre(saveDto.genre());
		return newMovie;
	}

	public static void updateEntity(Movie oldMovie, SaveMovie saveDto) {
		if (oldMovie == null || saveDto == null)
			return;
		oldMovie.setGenre(saveDto.genre());
		oldMovie.setReleaseYear(saveDto.releaseYear());
		oldMovie.setTitle(saveDto.title());
		oldMovie.setDirector(saveDto.director());
	}

}
