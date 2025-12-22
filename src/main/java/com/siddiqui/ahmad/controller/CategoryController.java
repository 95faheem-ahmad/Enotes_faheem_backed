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
import com.siddiqui.ahmad.EnotesServiceBackendApplication;
import com.siddiqui.ahmad.dto.CategoryDto;
import com.siddiqui.ahmad.dto.CategoryResponse;
import com.siddiqui.ahmad.exception.ExistDataException;
import com.siddiqui.ahmad.exception.ResourceNotFoundException;
import com.siddiqui.ahmad.service.CategoryService;
import com.siddiqui.ahmad.utils.CommonUtil;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

  
  
	@Autowired
	private CategoryService categoryService;

	@PostMapping("/save-categoy")
	public ResponseEntity<?>saveCategory(@RequestBody CategoryDto category) throws ExistDataException{
		
		Boolean saveCategory = categoryService.saveCategory(category);
		
		System.out.println("saveCategory"+saveCategory);
		
		if(saveCategory) {
			//return new ResponseEntity<>("saved success",HttpStatus.CREATED);
			return CommonUtil.createBuildResponse("saved success", HttpStatus.CREATED);
		}
		//return new ResponseEntity<>("not saved",HttpStatus.INTERNAL_SERVER_ERROR);
		return CommonUtil.createErrorResponseMessage("Category Not saved", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/")
	public ResponseEntity<?>getAllCategory(){
		List<CategoryDto> allCategory = categoryService.getAllCategory();
		if(CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();
		}
		else {
			//return new ResponseEntity<>(allCategory,HttpStatus.OK);
			return CommonUtil.createBuildResponse(allCategory, HttpStatus.OK);
		}
	}
	
	@GetMapping("/active")
	public ResponseEntity<?>getActiveCategory(){
		
		List<CategoryResponse> activeCategory = categoryService.getActiveCategory();
		if(ObjectUtils.isEmpty(activeCategory)) {
			return ResponseEntity.noContent().build();
		}
		else {
			//return new ResponseEntity<>(activeCategory,HttpStatus.OK);
			return CommonUtil.createBuildResponse(activeCategory, HttpStatus.OK);
		}
	}

	@DeleteMapping("/{id}")
public ResponseEntity<?>deleteCategoryById(@PathVariable Integer id){
	Boolean delete = categoryService.deleteCategory(id);
	if(delete) {
		//return new ResponseEntity<>("Category deleted success",HttpStatus.OK);
		return CommonUtil.createBuildResponse("Category deleted success", HttpStatus.OK);
	}
	//return new ResponseEntity<>("Category not deleted",HttpStatus.INTERNAL_SERVER_ERROR);
	return CommonUtil.createErrorResponseMessage("Category Not deleted", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?>getCategoryDetailsById(@PathVariable Integer id) throws Exception{
		 CategoryDto categoryById = categoryService.getCategoryById(id);
		if(ObjectUtils.isEmpty(categoryById)) {
			//return new ResponseEntity<>("Category not found with id="+id,HttpStatus.NOT_FOUND);
			
			return CommonUtil.createErrorResponseMessage("Internal server Error", HttpStatus.NOT_FOUND);
		}
		else {
			//return new ResponseEntity<>(categoryById,HttpStatus.OK);
			
			return CommonUtil.createBuildResponse(categoryById,HttpStatus.OK );
		}
	}
	
}
