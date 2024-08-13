package com.dangabito.projects.MovieManagement.service;

import java.util.List;

import com.dangabito.projects.MovieManagement.persistence.entity.Rating;

public interface RatingService {

	List<Rating> findAll();

	List<Rating> findAllByMovieId(Long movieId);

	List<Rating> findAllByUsername(String username);

	Rating findOneById(Long id);

	Rating updateOneByRatingId(Long id, Rating rating);

	Rating createOne(Rating rating);

	void deleteOneById(Long id);

}
