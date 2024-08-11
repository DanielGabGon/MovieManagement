package com.dangabito.projects.MovieManagement.dto.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GetUser(String username, String name, List<GetRating> ratings) implements Serializable {
	public static record GetRating(long id, 
			@JsonProperty(value = "movie_title") String movieTitle, @JsonProperty("movie_id") long movieId, int rating)
			implements Serializable {
	}
}
