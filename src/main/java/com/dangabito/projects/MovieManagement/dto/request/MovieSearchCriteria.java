package com.dangabito.projects.MovieManagement.dto.request;

import com.dangabito.projects.MovieManagement.util.MovieGenre;

public record MovieSearchCriteria(String title, MovieGenre genre, Integer minReleaseYear, Integer maxReleaseYear,
		Integer minAverageRating) {

}
