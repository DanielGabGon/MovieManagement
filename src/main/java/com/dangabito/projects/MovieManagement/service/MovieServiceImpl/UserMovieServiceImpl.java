package com.dangabito.projects.MovieManagement.service.MovieServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.dangabito.projects.MovieManagement.dto.request.SaveUser;
import com.dangabito.projects.MovieManagement.dto.response.GetUser;
import com.dangabito.projects.MovieManagement.exception.ObjectNotfoundException;
import com.dangabito.projects.MovieManagement.mapper.UserMovieMapper;
import com.dangabito.projects.MovieManagement.persistence.entity.UserMovie;
import com.dangabito.projects.MovieManagement.persistence.repository.UserMovieCrudRepository;
import com.dangabito.projects.MovieManagement.service.UserMovieService;
import com.dangabito.projects.MovieManagement.service.validator.PasswordValidator;



@Service
@Transactional
public class UserMovieServiceImpl implements UserMovieService {


	public UserMovieServiceImpl() {
	}

	private UserMovieCrudRepository userMovieCrudRepository;

	@Autowired
	public void setUserMovieCrudRepository(UserMovieCrudRepository userMovieCrudRepository) {
		this.userMovieCrudRepository = userMovieCrudRepository;
	}



	@Override
	@Transactional(readOnly = true)
	public List<GetUser> findAll() {
		List<UserMovie> entities = userMovieCrudRepository.findAll();
		return UserMovieMapper.toGetDtoList(entities);
	}

	@Override
	@Transactional(readOnly = true)
	public List<GetUser> findAllByName(String name) {
		List<UserMovie> entities = userMovieCrudRepository.findByNameContaining(name);
		return UserMovieMapper.toGetDtoList(entities);
	}

	@Transactional(readOnly = true)
	public UserMovie findOneEntityByUsernameMovie(String username) {
		return userMovieCrudRepository.findByUsername(username)
				.orElseThrow(
						() -> new ResponseStatusException(HttpStatusCode.valueOf(404), "User not found:" + username));
//				.orElseThrow(() -> new ObjectNotfoundException("[userMovie:" + username + "]"));

	}

	@Override
	@Transactional(readOnly = true)
	public GetUser findOneByUsernameMovie(String username) {
		return UserMovieMapper.toGetDto(findOneEntityByUsernameMovie(username));

	}

	@Override
	public GetUser updateOneByUserName(String username, SaveUser saveDto) {
		PasswordValidator.validatePassword(saveDto.password(), saveDto.passwordRepeated());

		UserMovie oldUserMovie = findOneEntityByUsernameMovie(username);
		UserMovieMapper.updateEntity(oldUserMovie, saveDto);
		return UserMovieMapper.toGetDto(userMovieCrudRepository.save(oldUserMovie));
	}

	@Override
	public GetUser creteOne(SaveUser saveDto) {
		PasswordValidator.validatePassword(saveDto.password(), saveDto.passwordRepeated());

		UserMovie newUserMovie = UserMovieMapper.toEntity(saveDto);
		return UserMovieMapper.toGetDto(userMovieCrudRepository.save(newUserMovie));
	}

	@Override
	@Transactional
	public void deleteOneByUsername(String username) {
		int deleteRecords = userMovieCrudRepository.deleteByUsername(username);
		if (deleteRecords != 1) {
			throw new ObjectNotfoundException("[userMovie:" + username + "]");
		}
	}


}
