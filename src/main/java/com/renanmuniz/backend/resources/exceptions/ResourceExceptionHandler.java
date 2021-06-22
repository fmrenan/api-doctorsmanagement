package com.renanmuniz.backend.resources.exceptions;

import java.time.Instant;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import com.renanmuniz.backend.services.exceptions.AddressNotFoundException;
import com.renanmuniz.backend.services.exceptions.DataBaseException;
import com.renanmuniz.backend.services.exceptions.ResourceNotFoundException;

@ControllerAdvice 
public class ResourceExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Resource not found");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());

		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(EntityNotFoundException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Resource not found");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());

		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(DataBaseException.class)
	public ResponseEntity<StandardError> dataBase(DataBaseException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("DataBase exception");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());

		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(AddressNotFoundException.class)
	public ResponseEntity<StandardError> addressNotFound(AddressNotFoundException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Address not found");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());

		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<StandardError> addressNotFound(HttpClientErrorException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Invalid Address");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());

		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		
		ValidationError err = new ValidationError();
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Argument exception");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		
		for(FieldError f : e.getBindingResult().getFieldErrors()) {
			err.addError(f.getField(), f.getDefaultMessage());
		}

		return ResponseEntity.status(status).body(err);
	}
}