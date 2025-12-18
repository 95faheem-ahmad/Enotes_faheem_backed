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

        Map<String, Object> error = new LinkedHashMap<>();

        if (ObjectUtils.isEmpty(categoryDto)) {
            throw new IllegalArgumentException(
                "Category object/JSON shouldn't be null or empty"
            );
        }

        // Name validation
        if (ObjectUtils.isEmpty(categoryDto.getName())) {
            error.put("name", "Name field is empty or null");
        } else {
            if (categoryDto.getName().length() < 10) {
                error.put("name", "Name length minimum is 10");
            }
            if (categoryDto.getName().length() > 100) {
                error.put("name", "Name length maximum is 100");
            }
        }

        // Description validation
        if (ObjectUtils.isEmpty(categoryDto.getDescription())) {
            error.put("description", "Description field is empty or null");
        }

        // isActive validation
        if (categoryDto.getIsActive() == null) {
            error.put("isActive", "isActive field is null");
        }

        if (!error.isEmpty()) {
            throw new ValidationException(error);
        }
    }
}
