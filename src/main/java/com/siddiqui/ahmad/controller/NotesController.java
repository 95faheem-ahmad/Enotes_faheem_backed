package com.siddiqui.ahmad.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.siddiqui.ahmad.dto.NotesDto;
import com.siddiqui.ahmad.dto.NotesResponse;
import com.siddiqui.ahmad.entity.FileDetails;
import com.siddiqui.ahmad.exception.ResourceNotFoundException;
import com.siddiqui.ahmad.service.NoteService;
import com.siddiqui.ahmad.utils.CommonUtil;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {

	@Autowired
	private NoteService notesService;

	@PostMapping("/")
	public ResponseEntity<?> saveNotes(@RequestParam String notes,  @RequestParam(required=false) MultipartFile file) throws ResourceNotFoundException, JsonMappingException, JsonProcessingException, IOException {

		Boolean saveNotes = notesService.saveNotes(notes,file);

		if (saveNotes) {
			return CommonUtil.createBuildResponse("Notes save success", HttpStatus.CREATED);
		}

		return CommonUtil.createErrorResponseMessage("Notes not saved!", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping("/")
	public ResponseEntity<?> getAllNotes() {

		List<NotesDto> notes = notesService.getAllNotes();
		if (CollectionUtils.isEmpty(notes)) {
			return ResponseEntity.noContent().build();
		}

		return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
	}
	
	
	@GetMapping("/download/{id}")
	public ResponseEntity<?>downloadFile(@PathVariable Integer id)throws Exception{
		
	FileDetails fileDetails=	notesService.getFileDetails(id);
		
	 byte[]data=notesService.downloadFile(fileDetails);
	 HttpHeaders headers=new HttpHeaders();
	 
	 String contentType = CommonUtil.getContenType(fileDetails.getOriginalFileName());
	 headers.setContentType(MediaType.parseMediaType(contentType));
	 headers.setContentDispositionFormData("attachment", fileDetails.getOriginalFileName());
	 
	 return ResponseEntity.ok().headers(headers).body(data);
	 
		
		 
	}
	
	@GetMapping("/user-notes")
	public ResponseEntity<?>getAllNotesByUser(@RequestParam(name="pageNo",defaultValue="0")Integer pageNo,@RequestParam(name="pageSize",defaultValue="10") Integer pageSize){
		
		Integer userId =2;
		
		NotesResponse notes = notesService.getAllNotesByUser(userId, pageNo, pageNo);
//		if(CollectionUtils.isEmpty(notes)) {
//			
//			return ResponseEntity.noContent().build();
//		}
		return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
	}
}
