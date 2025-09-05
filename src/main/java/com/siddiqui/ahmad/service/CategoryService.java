package com.siddiqui.ahmad.service;

import java.util.List;

import com.siddiqui.ahmad.dto.CategoryDto;
import com.siddiqui.ahmad.dto.CategoryResponse;

public interface CategoryService {
	
	public Boolean saveCategory(CategoryDto categoryDto);
	
	public List<CategoryDto>getAllCategory();
	
	public List<CategoryResponse>getActiveCategory();
	
	public CategoryDto getCategoryById(Integer id);
	
	public Boolean deleteCategory(Integer id);
	

	
}
