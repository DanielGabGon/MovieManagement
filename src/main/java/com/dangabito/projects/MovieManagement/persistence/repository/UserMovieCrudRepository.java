package com.dangabito.projects.MovieManagement.persistence.repository;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.dangabito.projects.MovieManagement.persistence.entity.UserMovie;




public interface UserMovieCrudRepository extends JpaRepository<UserMovie, Long>, JpaSpecificationExecutor<UserMovie> {

	static Logger logger = LoggerFactory.getLogger(UserMovieCrudRepository.class);

	default Page<UserMovie> findByNameContaining(String name, Pageable pageable) {
		Specification<UserMovie> userMovieSpecification = (root, query, criteriaBuilder) -> {
			if (StringUtils.hasText(name)) {
//				Predicate nameLikePredicate = criteriaBuilder.like(root.get("name"), "%" + name + "%");
				return criteriaBuilder.like(root.get("name"), "%" + name + "%");
			}
			return null;
		};
		return this.findAll(userMovieSpecification, pageable);
	}
	
	Optional<UserMovie> findByUsername(String username);

	// Esta anotación se pone para indicar que no es consulta.
	@Modifying
	@Transactional
	int deleteByUsername(String username);


}
