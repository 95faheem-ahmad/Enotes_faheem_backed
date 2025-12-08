package com.siddiqui.ahmad.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<?>handleNullPointerException(Exception e){
		
		 return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		 
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?>ResourceNotFoundException(Exception e){
		
		 return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		 
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?>handlException(Exception e){
		
		 return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		 
	}
	
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<?>handlValidationException(ValidationException e){
		return new ResponseEntity<>(e.getErrors(),HttpStatus.BAD_REQUEST);
	}
}
