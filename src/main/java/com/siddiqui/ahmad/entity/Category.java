package com.siddiqui.ahmad.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="CATEGORY")
public class Category {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String description;
	private String noteCount;

	private Boolean isActive;
	private Boolean isDeleted;
	private Integer createdBy;
	
	@CreationTimestamp
	@Column(updatable = false)
	private Date creationOn;
	private Integer updatedBy;
	@UpdateTimestamp
	@Column(insertable=false)
	private Date updatedOn;

}
