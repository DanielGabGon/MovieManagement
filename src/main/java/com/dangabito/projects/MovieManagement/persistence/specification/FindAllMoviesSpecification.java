package com.dangabito.projects.MovieManagement.persistence.specification;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.dangabito.projects.MovieManagement.dto.request.MovieSearchCriteria;
import com.dangabito.projects.MovieManagement.persistence.entity.Movie;
import com.dangabito.projects.MovieManagement.persistence.entity.Rating;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

public class FindAllMoviesSpecification implements Specification<Movie> {
	private static Logger logger = LoggerFactory.getLogger(FindAllMoviesSpecification.class);

	private MovieSearchCriteria searchCriteria;

	public FindAllMoviesSpecification(MovieSearchCriteria searchCriteria) {
		super();
		this.searchCriteria = searchCriteria;

	}

	@Override
	public Predicate toPredicate(Root<Movie> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

		// root = from Movie
		// query = criterios de la consulta en si misma
		// criteriaBuilder = complemento de las consultas >= like

		List<Predicate> predicates = new ArrayList<>();

		if (StringUtils.hasText(this.searchCriteria.title())) {
			Predicate titleLike = criteriaBuilder.like(root.get("title"), "%" + this.searchCriteria.title() + "%");
			predicates.add(titleLike);
		}

		if (searchCriteria.genre() != null) {
			Predicate genreEqual = criteriaBuilder.equal(root.get("genre"), this.searchCriteria.genre());
			predicates.add(genreEqual);
		}

		if (searchCriteria.minReleaseYear() != null && searchCriteria.minReleaseYear().intValue() > 0) {
			logger.info("ENTRO MIN LEASE YEAR");
			Predicate minReleaseYearGreaterThanEqual = criteriaBuilder.greaterThanOrEqualTo(root.get("releaseYear"),
					this.searchCriteria.minReleaseYear());
			predicates.add(minReleaseYearGreaterThanEqual);
		}

		if (searchCriteria.maxReleaseYear() != null && searchCriteria.maxReleaseYear().intValue() > 0) {
			logger.info("ENTRO MAX LEASE YEAR");
			Predicate maxReleaseYearLessThanEqual = criteriaBuilder.lessThanOrEqualTo(root.get("releaseYear"),
					this.searchCriteria.maxReleaseYear());
			predicates.add(maxReleaseYearLessThanEqual);
		}
		
		if (searchCriteria.minAverageRating() != null && searchCriteria.minAverageRating() > 0) {
			logger.info("ENTRO SUBQUERY");

			Subquery<Double> averageRatingSubquery = getAverageRatingSubquery(root, query, criteriaBuilder);

			// Comparación del promedio con el valor mínimo
			Predicate averageRatingGreaterThanEqual = criteriaBuilder
					.greaterThanOrEqualTo(averageRatingSubquery, this.searchCriteria.minAverageRating().doubleValue());

			predicates.add(averageRatingGreaterThanEqual);

		}
		
		
		

		Predicate[] predicatesAsArray = new Predicate[0];

		return criteriaBuilder.and(predicates.toArray(predicatesAsArray));

		// select m.* from movie where 1=1
		// and m.tile like '%?1%'
		// and m.genre=?2 and
		// m.releaseYear>=?3
		// m.releaseYear<=?4
		// and (SELECT AVG(r1_0.RATING) FROM RATING r1_0 where r1_0.movie_id=:m1_0.id)
		// >=searchCriteria.minAverageRating()
	}

	private Subquery<Double> getAverageRatingSubquery(Root<Movie> root, CriteriaQuery<?> query,
			CriteriaBuilder criteriaBuilder) {
		// (SELECT AVG(r1_0.RATING) FROM RATING r1_0 where r1_0.movie_id=:movie) >=?5
		// query.subquery(Rating.class) de esta manera se regresa toda la entidad
		// query.subquery(Double.class) aqui estoy regresando un valor que es
		// AVG(r1_0.RATING) osea un Double

		// Subquery para calcular el promedio de calificaciones
		Subquery<Double> averageRatingSubquery = query.subquery(Double.class); // select avg(rating)
		Root<Rating> ratingRoot = averageRatingSubquery.from(Rating.class); // from rating

		averageRatingSubquery.select(criteriaBuilder.avg(ratingRoot.get("rating"))); // avg(r1_0.rating)

		Predicate movieIdEqual = criteriaBuilder.equal(root.get("id"), ratingRoot.get("movieId"));
		averageRatingSubquery.where(movieIdEqual);

		return averageRatingSubquery;
	}

}
