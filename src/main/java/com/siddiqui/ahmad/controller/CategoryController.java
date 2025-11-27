package com.siddiqui.ahmad.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;   // <-- add this

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

    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);  // <-- logger

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/save-categoy")
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto category) {
        log.info("Received request to save category: {}", category);

        try {
            Boolean saveCategory = categoryService.saveCategory(category);
            log.debug("Service saveCategory(category) returned: {}", saveCategory);

            if (saveCategory) {
                log.info("Category saved successfully");
                return new ResponseEntity<>("saved success", HttpStatus.CREATED);
            } else {
                log.warn("Category not saved by service");
                return new ResponseEntity<>("not saved", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            log.error("Exception while saving category", e);
            return new ResponseEntity<>("Error while saving category", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/categoyr")
    public ResponseEntity<?> getAllCategory() {
        log.info("Received request to fetch all categories");

        try {
            List<CategoryDto> allCategory = categoryService.getAllCategory();
            log.debug("Fetched {} categories", (allCategory != null ? allCategory.size() : 0));

            if (CollectionUtils.isEmpty(allCategory)) {
                log.warn("No categories found");
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity<>(allCategory, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("Exception while fetching all categories", e);
            return new ResponseEntity<>("Error while fetching categories", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/active")
    public ResponseEntity<?> getActiveCategory() {
        log.info("Received request to fetch active categories");

        try {
            List<CategoryResponse> activeCategory = categoryService.getActiveCategory();
            log.debug("Fetched {} active categories", (activeCategory != null ? activeCategory.size() : 0));

            if (ObjectUtils.isEmpty(activeCategory)) {
                log.warn("No active categories found");
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity<>(activeCategory, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("Exception while fetching active categories", e);
            return new ResponseEntity<>("Error while fetching active categories", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Integer id) {
        log.info("Received request to delete category with id={}", id);

        try {
            Boolean delete = categoryService.deleteCategory(id);
            log.debug("Service deleteCategory({}) returned: {}", id, delete);

            if (delete) {
                log.info("Category deleted successfully for id={}", id);
                return new ResponseEntity<>("Category deleted success", HttpStatus.OK);
            } else {
                log.warn("Category not deleted for id={}", id);
                return new ResponseEntity<>("Category not deleted", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            log.error("Exception while deleting category id=" + id, e);
            return new ResponseEntity<>("Error while deleting category", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryDetailsById(@PathVariable Integer id) {
        log.info("Received request to fetch category details for id={}", id);

        try {
            CategoryDto categoryById = categoryService.getCategoryById(id);
            log.debug("Service getCategoryById({}) returned: {}", id, categoryById);

            if (ObjectUtils.isEmpty(categoryById)) {
                log.warn("Category not found for id={}", id);
                return new ResponseEntity<>("Category not found with id=" + id, HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(categoryById, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("Exception while fetching category details for id=" + id, e);
            return new ResponseEntity<>("Error while fetching category details", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
