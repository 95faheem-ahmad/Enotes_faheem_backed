package com.siddiqui.ahmad.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siddiqui.ahmad.dto.NotesDto;
import com.siddiqui.ahmad.exception.ResourceNotFoundException;
import com.siddiqui.ahmad.service.NoteService;
import com.siddiqui.ahmad.utils.CommonUtil;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {

	@Autowired
	private NoteService notesService;

	@PostMapping("/")
	public ResponseEntity<?> saveNotes(@RequestBody NotesDto notesDto) throws ResourceNotFoundException {

		Boolean saveNotes = notesService.saveNotes(notesDto);

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
}
