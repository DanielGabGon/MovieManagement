package com.dangabito.projects.MovieManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.dangabito.projects.MovieManagement.persistence.repository.MovieCrudRepository;
import com.dangabito.projects.MovieManagement.util.MovieGenre;

@SpringBootApplication
public class MovieManagementApplication {



	public static void main(String[] args) {
		SpringApplication.run(MovieManagementApplication.class, args);
	}

	@Autowired
	MovieCrudRepository movieCrudRepository;
	@Bean
	public CommandLineRunner tetsFindAllMoviesByGenreOrderBy() {
		return args -> {
			System.out.println("PELICULAS DE DRAMA ORDENADAS POR TÍTULO");
			Pageable pageable = PageRequest.of(0, 10);

			movieCrudRepository.findAllByGenreOrderByTitle(MovieGenre.DRAMA, pageable).forEach(System.out::println);
		};
	}

	@Bean
	public CommandLineRunner tetsFindAllMoviesByGenreSort() {
		return args -> {
			System.out.println("PELICULAS DE DRAMA ORDENADAS POR TÍTULO SORT");


			Sort.Direction direction = Sort.Direction.fromString("DESC");
			Sort sort = Sort.by(direction, "releaseYear").and(Sort.by(Sort.Direction.ASC, "id"));
			Pageable pageable = PageRequest.of(0, 10, sort);

			movieCrudRepository.findAllByGenre(MovieGenre.DRAMA, pageable).forEach(System.out::println);
		};
	}

}
