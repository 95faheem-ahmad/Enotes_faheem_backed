package com.siddiqui.ahmad.utils;

import java.util.LinkedHashMap;
import java.util.Map;


import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.siddiqui.ahmad.dto.CategoryDto;
import com.siddiqui.ahmad.exception.ValidationException;

@Component
public class Validation {

	public void categoryValidation(CategoryDto categoryDto) {
		
		Map<String,Object>error=new LinkedHashMap<>();
		
		if(ObjectUtils.isEmpty(categoryDto)) {
			throw new IllegalArgumentException("category object/JSON shouln't be null or empty");
		}
		
		else {
			if(ObjectUtils.isEmpty(categoryDto.getName())){
				
				error.put("name", "name field is empty or null");
			}
			if(categoryDto.getName().length()<10) {
				error.put("name", "name lenegth min 10");
			}
			if(categoryDto.getName().length()>100) {
				error.put("name", "name length max 100");
			}
		}
		
		
		// validation description
		 if(ObjectUtils.isEmpty(categoryDto.getDescription())){
			
			 error.put("description", "description field is empty or null");
		 }
		 
		 // validation isActive
		 if(ObjectUtils.isEmpty(categoryDto.getIsActive())) {
			 error.put("isActive", "isActive field is empty or null");
		 }
		 else {
			 if(categoryDto.getIsActive()!=Boolean.TRUE.booleanValue()&&categoryDto.getIsActive()!=Boolean.FALSE.booleanValue()) {
				 error.put("isActive", "invalid value isActive field ");
			 }
		 }
		 if(!error.isEmpty()) {
			 throw new ValidationException(error);
		 }
	}
	
	
}
