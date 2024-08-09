package com.dangabito.projects.MovieManagement.service.MovieServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dangabito.projects.MovieManagement.exception.ObjectNotfoundException;
import com.dangabito.projects.MovieManagement.persistence.entity.Rating;
import com.dangabito.projects.MovieManagement.persistence.repository.RatingCrudRepository;
import com.dangabito.projects.MovieManagement.service.RatingService;

@Service
@Transactional
public class RatingServiceimpl implements RatingService {

	@Autowired
	private RatingCrudRepository ratingCrudRepository;

	/**
	 * Esta viene de la Interface JpaRepository.
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Rating> findAll() {
		return ratingCrudRepository.findAll();
	}

	/**
	 * Esta viene de la Interface RatingCrudRepository.
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Rating> findAllByMovieId(Long movieId) {
		return ratingCrudRepository.findByMovieId(movieId);
	}

	/**
	 * Esta viene de la Interface RatingCrudRepository.
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Rating> findAllByUsername(String username) {
		return ratingCrudRepository.findByUserMovieUsername(username);
	}

	@Override
	@Transactional(readOnly = true)
	public Rating findOneById(Long id) {
		return ratingCrudRepository.findById(id)
				.orElseThrow(() -> new ObjectNotfoundException("[rating:" + Long.toString(id) + "]"));
	}

	@Override
	public Rating updateOneByRatingId(Long id, Rating rating) {
		Rating oldRating = this.findOneById(id);
		oldRating.setUserId(rating.getUserId());
		oldRating.setMovieId(rating.getMovieId());

		return ratingCrudRepository.save(oldRating);
	}

	@Override
	public Rating createOne(Rating rating) {
		return ratingCrudRepository.save(rating);
	}

	@Override
	public void deleteOneById(Long id) {
		// Rating rating = this.findOneById(id);
		boolean exist= ratingCrudRepository.existsById(id);
		if(exist) {
			ratingCrudRepository.deleteById(id);
			return;
		}
		throw new ObjectNotfoundException("rating:" + Long.toString(id) + "]");
	}

}
