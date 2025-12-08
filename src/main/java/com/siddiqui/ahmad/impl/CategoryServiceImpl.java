package com.siddiqui.ahmad.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;   // <-- logging import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.siddiqui.ahmad.dto.CategoryDto;
import com.siddiqui.ahmad.dto.CategoryResponse;
import com.siddiqui.ahmad.entity.Category;
import com.siddiqui.ahmad.exception.ResourceNotFoundException;
import com.siddiqui.ahmad.repository.CategoryRepository;
import com.siddiqui.ahmad.service.CategoryService;
import com.siddiqui.ahmad.utils.Validation;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);  // <-- logger

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private Validation validation;

    @Override
    public Boolean saveCategory(CategoryDto categoryDto) {
        log.info("Saving category: {}", categoryDto);

        try {
        	// validation checking
        	validation.categoryValidation(categoryDto);
            Category category = modelMapper.map(categoryDto, Category.class);

            if (ObjectUtils.isEmpty(category.getId())) {
                log.debug("Creating new category");
                category.setIsDeleted(false);
                category.setCreatedBy(1);
                category.setCreationOn(new Date());
            } else {
                log.debug("Updating existing category with id={}", category.getId());
                updateCategory(category);
            }

            Category saveCategory = categoryRepo.save(category);
            if (ObjectUtils.isEmpty(saveCategory)) {
                log.warn("Category not saved by repository");
                return false;
            }

            log.info("Category saved successfully with id={}", saveCategory.getId());
            return true;

        } catch (Exception e) {
            log.error("Exception while saving category: {}", categoryDto, e);
            return false;
        }
    }

    private void updateCategory(Category category) {
        log.debug("Fetching existing category for update, id={}", category.getId());

        Optional<Category> findById = categoryRepo.findById(category.getId());

        if (findById.isPresent()) {
            Category existingCategory = findById.get();
            log.debug("Existing category found for id={}", existingCategory.getId());

            category.setCreatedBy(existingCategory.getCreatedBy());
            category.setCreationOn(existingCategory.getCreationOn());
            category.setIsDeleted(existingCategory.getIsDeleted());
            // keep active status from DTO or from existing? you chose existing earlier:
            category.setIsActive(existingCategory.getIsActive());
            category.setUpdatedBy(1);
            category.setUpdatedOn(new Date());
        } else {
            log.warn("No existing category found for id={} while updating", category.getId());
        }
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        log.info("Fetching all categories (including deleted)");

        List<Category> categories = categoryRepo.findAll();
        log.debug("Total categories fetched from DB: {}", (categories != null ? categories.size() : 0));

        List<CategoryDto> dtoList = categories.stream()
                .map(cat -> modelMapper.map(cat, CategoryDto.class))
                .collect(Collectors.toList());

        log.debug("Converted categories to DTO list size={}", dtoList.size());
        return dtoList;
    }

    @Override
    public List<CategoryResponse> getActiveCategory() {
        log.info("Fetching active and not deleted categories");

        List<Category> categories = categoryRepo.findByIsActiveTrueAndIsDeletedFalse();
        log.debug("Active, not deleted categories count={}", (categories != null ? categories.size() : 0));

        List<CategoryResponse> responseList = categories.stream()
                .map(category -> {
                    CategoryResponse response = new CategoryResponse();
                    response.setId(category.getId());
                    response.setName(category.getName());
                    response.setDescription(category.getDescription());
                    // set more fields if needed
                    return response;
                })
                .collect(Collectors.toList());

        log.debug("Converted active categories to response list size={}", responseList.size());
        return responseList;
    }

    @Override
    public CategoryDto getCategoryById(Integer id) throws ResourceNotFoundException {
        log.info("Fetching category by id={}", id);

        Optional<Category> optional = Optional.of(categoryRepo.findByIdAndIsDeletedFalse(id).orElseThrow(()->new ResourceNotFoundException("Category not found with "+id)));
        if (optional != null && optional.isPresent()) {
            Category category = optional.get();
            log.debug("Category found for id={}", id);

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
            log.warn("No category found (or deleted) for id={}", id);
        }
        return null;
    }

    @Override
    public Boolean deleteCategory(Integer id) {
        log.info("Soft deleting category with id={}", id);

        Optional<Category> findByCategory = categoryRepo.findById(id);
        if (findByCategory.isPresent()) {
            Category category = findByCategory.get();
            category.setIsDeleted(true);
            categoryRepo.save(category);
            log.info("Category soft deleted successfully for id={}", id);
            return true;
        }

        log.warn("Category not found for delete, id={}", id);
        return false;
    }
}
