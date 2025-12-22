package com.siddiqui.ahmad.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.siddiqui.ahmad.utils.CommonUtil;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<?>handleNullPointerException(Exception e){
		
		log.error("GlobalExceptionHandler :: NullPointerException ::",e.getMessage());
		 //return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		 
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
	
	@ExceptionHandler(ExistDataException.class)
	public ResponseEntity<?>ExistDataException(ExistDataException e){
		return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
	}
}
