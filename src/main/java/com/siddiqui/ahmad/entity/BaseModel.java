/**
 * 
 */
package com.siddiqui.ahmad.entity;

import java.util.Date;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 */

@Setter
@Getter
@MappedSuperclass
public abstract class BaseModel {

	@CreatedBy
	@Column(updatable = false)
	private Integer createdBy;

	@CreatedDate
	@Column(updatable=false)
	private Date creationOn;

	
	@LastModifiedBy
	@Column(insertable=false)
	private Integer updatedBy;

	
	@LastModifiedDate
	@Column(insertable=false)
	private Date updatedOn;
}
