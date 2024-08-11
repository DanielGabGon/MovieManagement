package com.dangabito.projects.MovieManagement.dto.request;

import java.io.Serializable;

import com.dangabito.projects.MovieManagement.util.MovieGenre;
import com.fasterxml.jackson.annotation.JsonProperty;

public record SaveMovie(String title, String director, MovieGenre genre,
		@JsonProperty(value = "release_year") int releaseYear) implements Serializable {

}
