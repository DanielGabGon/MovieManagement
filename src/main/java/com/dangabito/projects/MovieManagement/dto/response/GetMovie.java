package com.dangabito.projects.MovieManagement.dto.response;

import java.io.Serializable;
import java.util.List;

import com.dangabito.projects.MovieManagement.util.MovieGenre;
import com.fasterxml.jackson.annotation.JsonProperty;

public record GetMovie(Long id, String title, String director, MovieGenre genre,
		@JsonProperty(value = "release_year") int releaseYear, List<GetRating> ratings) implements Serializable {

	public static record GetRating(long id, int rating, String username) implements Serializable {
	}
}
