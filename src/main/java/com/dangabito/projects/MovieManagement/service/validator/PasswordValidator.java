package com.dangabito.projects.MovieManagement.service.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.dangabito.projects.MovieManagement.exception.InvalidPasswordException;

public class PasswordValidator {
	private static Logger logger = LoggerFactory.getLogger(PasswordValidator.class);


	public static void validatePassword(String password, String passwordRepeated) {
		if (!StringUtils.hasText(password) || !StringUtils.hasText(passwordRepeated)) {
			logger.info("ESTE NO ES TOMADO EN CUENTA:{}", password);
			throw new IllegalArgumentException("Passwords must contain data");
		}

		if (!password.equals(passwordRepeated)) {
			throw new InvalidPasswordException(password, passwordRepeated, "Passwords do not match");
		}

		if (!containsNumber(password)) {
			throw new InvalidPasswordException(password, "Password must contain at least one numbre");
		}

		if (!containsUpperCase(password)) {
			throw new InvalidPasswordException(password, "Password must contain at least one uppercase letter");
		}

		if (!containsLowerCase(password)) {
			throw new InvalidPasswordException(password, "Password must contain at least one lowercase letter");
		}

		if (!containsSpecialCharacter(password)) {
			throw new InvalidPasswordException(password, "Password must contain at least one special character");
		}

	}

	private static boolean containsSpecialCharacter(String password) {
		return password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\",.<>/?].*");
	}

	private static boolean containsLowerCase(String password) {
		return password.matches(".*[a-a].*");
	}

	private static boolean containsUpperCase(String password) {
		return password.matches(".*[A-Z].*");
	}

	private static boolean containsNumber(String password) {
		return password.matches(".*\\d.*");
	}

}
