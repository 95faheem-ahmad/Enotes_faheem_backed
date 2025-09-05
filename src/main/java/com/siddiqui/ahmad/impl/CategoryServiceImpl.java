package com.siddiqui.ahmad.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.siddiqui.ahmad.dto.CategoryDto;
import com.siddiqui.ahmad.dto.CategoryResponse;
import com.siddiqui.ahmad.entity.Category;
import com.siddiqui.ahmad.repository.CategoryRepository;
import com.siddiqui.ahmad.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepo;

	@Override
	public Boolean saveCategory(CategoryDto categoryDto) {

		Category category = new Category();
		category.setName(categoryDto.getName());
		category.setDescription(categoryDto.getDescription());
		category.setIsActive(categoryDto.getIsActive());
		category.setIsDeleted(false);
		category.setCreatedBy(1);
		Category saveCategory = categoryRepo.save(category);
		if (ObjectUtils.isEmpty(saveCategory)) {
			return false;
		}

		return true;
	}

	@Override
	public List getAllCategory() {
		List<Category> categoris = categoryRepo.findAll();
		List<Category> collect = categoris.stream().collect(Collectors.toList());
		return collect;
	}

	@Override
	public List<CategoryResponse> getActiveCategory() {
   
		List<Category> categories = categoryRepo.findByIsActiveTrueAndIsDeletedFalse();
		
		List<Category> collecIsActive = categories.stream().collect(Collectors.toList());
		
		List<CategoryResponse> responseList = categories.stream()
		        .map(category -> {
		            CategoryResponse response = new CategoryResponse();
		            response.setId(category.getId());
		            response.setName(category.getName());
		            response.setDescription(category.getDescription());
		            // Set more fields as needed
		            return response;
		        })
		        .collect(Collectors.toList());

		    return responseList;
		}
	
	@Override
	public CategoryDto getCategoryById(Integer id) {
		Optional<Category> optional = categoryRepo.findByIdAndIsDeletedFalse(id);
		if (optional != null && optional.isPresent()) {
	        Category category = optional.get();
	        CategoryDto dto = new CategoryDto();
	        dto.setId(category.getId());
	        dto.setName(category.getName());
	        dto.setNoteCount(category.getNoteCount());
	        dto.setIsActive(category.getIsActive());
	        dto.setIsDeleted(category.getIsDeleted());
	        dto.setCreatedBy(category.getCreatedBy());
	        dto.setCreationOn(category.getCreationOn());
	        dto.setUpdatedBy(category.getUpdatedBy());
	        dto.setUpdatedOn(category.getUpdatedOn());
	        dto.setDescription(category.getDescription());
	        return dto;
	    } else {
	       
	    }
		return null;
	}

	@Override
	public Boolean deleteCategory(Integer id) {
		
		 Optional<Category> findByCategory = categoryRepo.findById(id);
		if( findByCategory.isPresent()) {
			Category category = findByCategory.get();
			category.setIsDeleted(true);
			categoryRepo.save(category);
			return true;
		}
		return  false;
	}

}
