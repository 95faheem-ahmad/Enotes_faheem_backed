package com.siddiqui.ahmad.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.siddiqui.ahmad.dto.NotesDto;
import com.siddiqui.ahmad.dto.NotesResponse;
import com.siddiqui.ahmad.entity.FileDetails;
import com.siddiqui.ahmad.exception.ResourceNotFoundException;

public interface NoteService {

	public Boolean saveNotes(String notes,MultipartFile file) throws ResourceNotFoundException, JsonMappingException, JsonProcessingException, IOException;
	
	public List<NotesDto>getAllNotes();
	
	public byte[] downloadFile(FileDetails fileDtls)throws Exception;
	
	public FileDetails getFileDetails(Integer id)throws ResourceNotFoundException;
	
	public NotesResponse getAllNotesByUser(Integer userId,Integer pageNo,Integer pageSize);
	
}
