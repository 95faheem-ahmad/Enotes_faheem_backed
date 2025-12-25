package com.siddiqui.ahmad.service;

import java.util.List;

import com.siddiqui.ahmad.dto.NotesDto;
import com.siddiqui.ahmad.exception.ResourceNotFoundException;

public interface NoteService {

	public Boolean saveNotes(NotesDto notesDto) throws ResourceNotFoundException;
	
	public List<NotesDto>getAllNotes();
}
