package com.dangabito.projects.MovieManagement.persistence.entity;

import java.util.List;

import com.dangabito.projects.MovieManagement.util.MovieGenre;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String director;

	@Enumerated(EnumType.STRING)
	private MovieGenre genre;

	private int releaseYear;

	@OneToMany(targetEntity = Rating.class, fetch = FetchType.EAGER, mappedBy = "movie")
	private List<Rating> ratings;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public MovieGenre getGenre() {
		return genre;
	}

	public void setGenre(MovieGenre genre) {
		this.genre = genre;
	}



	public List<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}

	public int getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}

}
