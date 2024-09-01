package com.dangabito.projects.MovieManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MovieManagementApplication {



	public static void main(String[] args) {
		SpringApplication.run(MovieManagementApplication.class, args);
	}

//	@Autowired
//	MovieCrudRepository movieCrudRepository;
//	@Bean
//	public CommandLineRunner tetsFindAllMoviesByGenreOrderBy() {
//		return args -> {
//			System.out.println("PELICULAS DE DRAMA ORDENADAS POR TÍTULO");
//			Pageable pageable = PageRequest.of(0, 10);
//
//			movieCrudRepository.findAllByGenreOrderByTitle(MovieGenre.DRAMA, pageable).forEach(System.out::println);
//		};
//	}

//	@Bean
//	public CommandLineRunner tetsFindAllMoviesByGenreSort() {
//		return args -> {
//			System.out.println("PELICULAS DE DRAMA ORDENADAS POR TÍTULO SORT");
//
//
//			Sort.Direction direction = Sort.Direction.fromString("DESC");
//			Sort sort = Sort.by(direction, "releaseYear").and(Sort.by(Sort.Direction.ASC, "id"));
//			Pageable pageable = PageRequest.of(0, 10, sort);
//
//			movieCrudRepository.findAllByGenre(MovieGenre.DRAMA, pageable).forEach(System.out::println);
//		};
//	}

}
