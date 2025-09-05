package com.siddiqui.ahmad.dto;

import java.util.Date;

import lombok.Data;

@Data
public class CategoryDto {

	private Integer id;
	private String name;
	private String description;
	private String noteCount;

	private Boolean isActive;
	private Boolean isDeleted;
	private Integer createdBy;

	private Date creationOn;
	private Integer updatedBy;

	private Date updatedOn;
}
