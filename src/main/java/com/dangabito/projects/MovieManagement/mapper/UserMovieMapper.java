package com.dangabito.projects.MovieManagement.mapper;

import java.util.List;

import com.dangabito.projects.MovieManagement.dto.request.SaveUser;
import com.dangabito.projects.MovieManagement.dto.response.GetUser;
import com.dangabito.projects.MovieManagement.persistence.entity.UserMovie;

public class UserMovieMapper {

	public static GetUser toGetDto(UserMovie entity) {
		if (entity == null)
			return null;

		return new GetUser(entity.getUsername(), entity.getName(),
				RatingMapper.toGetUserRatingDtoList(entity.getRatings()));
	}

	public static List<GetUser> toGetDtoList(List<UserMovie> entities) {
		if (entities == null)
			return null;

		List<GetUser> listaGetUser = entities.stream().map(each -> UserMovieMapper.toGetDto(each)).toList();// entities.stream().map(UserMovieMapper::toGetDto).toList()

		return listaGetUser;
	}

	public static UserMovie toEntity(SaveUser saveDto) {
		if (saveDto == null)
			return null;
		UserMovie userMovie = new UserMovie();
		userMovie.setUsername(saveDto.username());
		userMovie.setName(saveDto.name());
		userMovie.setPassword(saveDto.password());
		return userMovie;
	}

	public static void updateEntity(UserMovie oldUserMovie, SaveUser saveDto) {
		if (oldUserMovie == null || saveDto == null)
			return;
		oldUserMovie.setName(saveDto.name());
		oldUserMovie.setPassword(saveDto.password());
	}

}
