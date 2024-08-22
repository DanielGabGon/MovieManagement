package com.dangabito.projects.MovieManagement.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dangabito.projects.MovieManagement.dto.request.MovieSearchCriteria;
import com.dangabito.projects.MovieManagement.dto.request.SaveMovie;
import com.dangabito.projects.MovieManagement.dto.response.GetMovie;

public interface MovieService {
	
	Page<GetMovie> findAll(MovieSearchCriteria movieSearchCriteria, Pageable pageable);

	GetMovie findOneById(Long id);

	GetMovie updateOneById(Long id, SaveMovie movie);

	GetMovie createOne(SaveMovie movie);

	void deleteOneById(Long id);

}
