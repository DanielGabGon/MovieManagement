package com.dangabito.projects.MovieManagement.mapper;

import java.util.List;

import com.dangabito.projects.MovieManagement.dto.response.GetMovie;
import com.dangabito.projects.MovieManagement.dto.response.GetUser;
import com.dangabito.projects.MovieManagement.persistence.entity.Rating;

public class RatingMapper {

	public static GetMovie.GetRating toGetMovieRatingDto(Rating entity) {
		if (entity == null)
			return null;

		String username = entity.getUser() != null ? entity.getUser().getUsername() : null;

		return new GetMovie.GetRating(entity.getId(), entity.getRating(), username);
	}

	public static List<GetMovie.GetRating> toGetMovieRatingDtoList(List<Rating> entities) {
		if (entities == null)
			return null;
		return entities.stream().map(each -> RatingMapper.toGetMovieRatingDto(each)).toList();
	}

	public static GetUser.GetRating toGetUserRatingDto(Rating entity) {
		if (entity == null)
			return null;

		String movieTitle = entity.getMovie() != null ? entity.getUser().getUsername() : null;
		
		return new GetUser.GetRating(entity.getId(), movieTitle, entity.getMovieId(), entity.getRating());
	}

	public static List<GetUser.GetRating> toGetUserRatingDtoList(List<Rating> entities) {
		if (entities == null)
			return null;
		return entities.stream().map(each -> RatingMapper.toGetUserRatingDto(each)).toList();
	}

}
