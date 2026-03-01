package com.fenix.tech.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fenix.tech.response.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(ErrorResponse.builder().timestamp(Instant.now()).status(HttpStatus.NOT_FOUND.value())
						.error("Not Found").message(ex.getMessage()).path(request.getRequestURI()).build());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGeneric(Exception ex, HttpServletRequest request) {

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(ErrorResponse.builder().timestamp(Instant.now()).status(HttpStatus.BAD_REQUEST.value())
						.error("Bad Request").message(ex.getMessage()).path(request.getRequestURI()).build());
	}
}