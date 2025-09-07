package com.siddiqui.ahmad.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siddiqui.ahmad.dto.CategoryDto;
import com.siddiqui.ahmad.dto.CategoryResponse;
import com.siddiqui.ahmad.service.CategoryService;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
  
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/save-categoy")
	public ResponseEntity<?>saveCategory(@RequestBody CategoryDto category){
		
		Boolean saveCategory = categoryService.saveCategory(category);
		
		if(saveCategory) {
			return new ResponseEntity<>("saved success",HttpStatus.CREATED);
		}
		return new ResponseEntity<>("not saved",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/categoyr")
	public ResponseEntity<?>getAllCategory(){
		List<CategoryDto> allCategory = categoryService.getAllCategory();
		if(CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();
		}
		else {
			return new ResponseEntity<>(allCategory,HttpStatus.OK);
		}
	}
	
	@GetMapping("/active")
	public ResponseEntity<?>getActiveCategory(){
		
		List<CategoryResponse> activeCategory = categoryService.getActiveCategory();
		if(ObjectUtils.isEmpty(activeCategory)) {
			return ResponseEntity.noContent().build();
		}
		else {
			return new ResponseEntity<>(activeCategory,HttpStatus.OK);
		}
	}

	@DeleteMapping("/{id}")
public ResponseEntity<?>deleteCategoryById(@PathVariable Integer id){
	Boolean delete = categoryService.deleteCategory(id);
	if(delete) {
		return new ResponseEntity<>("Category deleted success",HttpStatus.OK);
	}
	return new ResponseEntity<>("Category not deleted",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?>getCategoryDetailsById(@PathVariable Integer id){
		CategoryDto categoryById = categoryService.getCategoryById(id);
		if(ObjectUtils.isEmpty(categoryById)) {
			return new ResponseEntity<>("Category not found with id="+id,HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity<>(categoryById,HttpStatus.OK);
		}
	}
	
}
