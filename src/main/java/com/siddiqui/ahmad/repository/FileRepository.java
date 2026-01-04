package com.siddiqui.ahmad.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.siddiqui.ahmad.entity.FileDetails;

public interface FileRepository extends JpaRepository<FileDetails ,Integer>{

}
