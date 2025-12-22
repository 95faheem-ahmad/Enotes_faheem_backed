package com.siddiqui.ahmad.service;

import java.util.List;

import com.siddiqui.ahmad.dto.CategoryDto;
import com.siddiqui.ahmad.dto.CategoryResponse;
import com.siddiqui.ahmad.exception.ExistDataException;
import com.siddiqui.ahmad.exception.ResourceNotFoundException;

public interface CategoryService {
	
	public Boolean saveCategory(CategoryDto categoryDto) throws ExistDataException;
	
	public List<CategoryDto>getAllCategory();
	
	public List<CategoryResponse>getActiveCategory();
	
	public CategoryDto getCategoryById(Integer id) throws ResourceNotFoundException;
	
	public Boolean deleteCategory(Integer id);
	

	
}
