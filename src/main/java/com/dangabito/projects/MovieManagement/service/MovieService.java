package com.dangabito.projects.MovieManagement.service;

import java.util.List;

import com.dangabito.projects.MovieManagement.dto.request.SaveMovie;
import com.dangabito.projects.MovieManagement.dto.response.GetMovie;
import com.dangabito.projects.MovieManagement.util.MovieGenre;

public interface MovieService {
	
	// Los metodos de servicio
	// se tienen que llamar igual que los metodos de repositorio.
	List<GetMovie> findAll();

	List<GetMovie> findAllByTitle(String title);

	List<GetMovie> findAllByGenre(MovieGenre genre);

	List<GetMovie> findAllByGenreAndTitle(MovieGenre genre, String title);

	GetMovie findOneById(Long id);

	GetMovie updateOneById(Long id, SaveMovie movie);

	GetMovie createOne(SaveMovie movie);

	void deleteOneById(Long id);

}
