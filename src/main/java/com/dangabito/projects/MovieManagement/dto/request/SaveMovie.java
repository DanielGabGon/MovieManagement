package com.dangabito.projects.MovieManagement.dto.request;

import java.io.Serializable;

import com.dangabito.projects.MovieManagement.util.MovieGenre;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;



public record SaveMovie(
		@Size(min = 4, max = 255, message = "{generic.size}") @NotBlank(message = "{generic.notblank}") String title,
		@Size(min = 4, max = 255, message = "{generic.size}") @NotBlank(message = "{generic.notblank}") String director,
		MovieGenre genre,
		@Min(value = 1900, message = "{generic.min}") @JsonProperty(value = "release_year") int releaseYear
//		,
//		@JsonProperty("availability_end_time") @JsonFormat(pattern = "yyyy-MM-dd") @PastOrPresent LocalDate availabilityEndTime
)
		implements Serializable {
}
