package com.siddiqui.ahmad.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.siddiqui.ahmad.dto.CategoryDto;
import com.siddiqui.ahmad.dto.NotesDto;
import com.siddiqui.ahmad.entity.Notes;
import com.siddiqui.ahmad.exception.ResourceNotFoundException;
import com.siddiqui.ahmad.repository.CategoryRepository;
import com.siddiqui.ahmad.repository.NotesRepository;
import com.siddiqui.ahmad.service.NoteService;

@Service
public class NotesServiceImpl implements NoteService {

	@Autowired
	private NotesRepository notesRepo;

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private CategoryRepository categoryRepo;

	@Override
	public Boolean saveNotes(NotesDto notesDto) throws ResourceNotFoundException {

		checkCategoryExist(notesDto.getCategory());
		
		Notes notes = mapper.map(notesDto, Notes.class);
		Notes saveNotes = notesRepo.save(notes);
		if (ObjectUtils.isEmpty(saveNotes)) {
			return false;
		}
		return true;
	}

	private void checkCategoryExist(CategoryDto category) throws ResourceNotFoundException {
		categoryRepo.findById(category.getId()).orElseThrow(()->new ResourceNotFoundException("category id invalid "));
		
	}

	@Override
	public List<NotesDto> getAllNotes() {
		return notesRepo.findAll().stream().map(note -> mapper.map(note, NotesDto.class)).toList();
	}

}
