package com.dangabito.projects.MovieManagement.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ApiError (
	
		@JsonProperty("http_code") int httpCode,
	String url,
		@JsonProperty("http_method") String httpMethod, String message,
		@JsonProperty("backend_message") String backendMessage, LocalDateTime timestamp, List<String> details
) {
	
}
