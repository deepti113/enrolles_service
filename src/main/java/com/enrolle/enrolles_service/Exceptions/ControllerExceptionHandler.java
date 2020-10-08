package com.enrolle.enrolles_service.Exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ControllerExceptionHandler {
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorMessage> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(new Date(), ex.getMessage(), request.getDescription(false));

		return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> globalException(Exception ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(new Date(), ex.getMessage(), request.getDescription(false));

		return new ResponseEntity<ErrorMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
