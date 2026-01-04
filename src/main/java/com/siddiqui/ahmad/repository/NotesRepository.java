package com.siddiqui.ahmad.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.siddiqui.ahmad.entity.Notes;

public interface NotesRepository extends JpaRepository<Notes,Integer> {

	Page<Notes>findByCreatedBy(Integer userId, PageRequest pageRequest);

}
