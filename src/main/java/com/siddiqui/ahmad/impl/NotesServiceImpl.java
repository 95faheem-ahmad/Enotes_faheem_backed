package com.siddiqui.ahmad.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siddiqui.ahmad.dto.CategoryDto;
import com.siddiqui.ahmad.dto.NotesDto;
import com.siddiqui.ahmad.dto.NotesResponse;
import com.siddiqui.ahmad.entity.FileDetails;
import com.siddiqui.ahmad.entity.Notes;
import com.siddiqui.ahmad.exception.ResourceNotFoundException;
import com.siddiqui.ahmad.repository.CategoryRepository;
import com.siddiqui.ahmad.repository.FileRepository;
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

	@Value("${file.upload.path}")
	private String uploadpath;

	@Autowired
	private FileRepository fileRepo;

	@Override
	public Boolean saveNotes(String notes, MultipartFile file) throws ResourceNotFoundException, IOException {

		ObjectMapper ob = new ObjectMapper();

		NotesDto notesDto = ob.readValue(notes, NotesDto.class);

		checkCategoryExist(notesDto.getCategory());

		Notes notesMap = mapper.map(notesDto, Notes.class);

		FileDetails saveFileDetails = saveFileDetails(file);

		if (!ObjectUtils.isEmpty(saveFileDetails)) {
			notesMap.setFileDetails(saveFileDetails);
		} else {
			notesMap.setFileDetails(null);
		}
		Notes saveNotes = notesRepo.save(notesMap);
		if (ObjectUtils.isEmpty(saveNotes)) {
			return false;
		}
		return true;
	}

	private FileDetails saveFileDetails(MultipartFile file) throws IOException {

		if (!ObjectUtils.isEmpty(file) && !file.isEmpty()) {
			String originalFilename = file.getOriginalFilename();
			String extension = FilenameUtils.getExtension(originalFilename);

			List<String> extensionAllow = Arrays.asList("pdf", "xlsx", "jpg", "png");

			if (!extensionAllow.contains(extension)) {

				throw new IllegalArgumentException("invalid file format! upload only .pdf ,.xlsx, .jpg");
			}
			String rndString = UUID.randomUUID().toString();

			String uploadedfileName = rndString + "." + extension;

			File saveFile = new File(uploadpath);
			if (!saveFile.exists()) {

				saveFile.mkdir();
			}
			String storPath = uploadpath.concat(uploadedfileName);

			// upload file

			long upload = Files.copy(file.getInputStream(), Paths.get(storPath));

			if (upload != 0) {

				FileDetails fileDtls = new FileDetails();

				fileDtls.setOriginalFileName(originalFilename);
				fileDtls.setDisplayFileName(getDisplayName(originalFilename));
				fileDtls.setUploadedFileName(uploadedfileName);
				fileDtls.setFileSize(file.getSize());
				fileDtls.setPath(storPath);
				FileDetails saveFileDtls = fileRepo.save(fileDtls);

				return saveFileDtls;
			}
		}

		return null;
	}

	private String getDisplayName(String originalFilename) {

		String extension = FilenameUtils.getExtension(originalFilename);
		String fileName = FilenameUtils.removeExtension(originalFilename);

		if (fileName.length() > 8) {

			fileName = fileName.substring(0, 7);
		}
		fileName = fileName + "." + extension;
		return fileName;
	}

	private void checkCategoryExist(CategoryDto category) throws ResourceNotFoundException {
		categoryRepo.findById(category.getId())
				.orElseThrow(() -> new ResourceNotFoundException("category id invalid "));

	}

	@Override
	public List<NotesDto> getAllNotes() {
		return notesRepo.findAll().stream().map(note -> mapper.map(note, NotesDto.class)).toList();
	}

	@Override
	public byte[] downloadFile(FileDetails fileDtls) throws  Exception {
		
		  FileInputStream io = new FileInputStream(fileDtls.getPath());
		  return StreamUtils.copyToByteArray(io);
		  
	}

	@Override
	public FileDetails getFileDetails(Integer id) throws ResourceNotFoundException   {
		FileDetails fileDetails = fileRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("File is not available"));
		return fileDetails;
	}

	@Override
	public NotesResponse getAllNotesByUser(Integer userId, Integer pageNo, Integer pageSize) {
		PageRequest pageable = PageRequest.of(pageNo, pageSize);
		Page<Notes> pageNotes = notesRepo.findByCreatedBy(userId,pageable);
		List<NotesDto> notesDto = pageNotes.get().map(n->mapper.map(n, NotesDto.class)).toList();
		
		NotesResponse notes = NotesResponse.builder()
		.notes(notesDto)
		.pageNo(pageNo)
		.pageSize(pageSize)
		.totalElements(pageNotes.getTotalElements())
		.totalPages(pageNotes.getTotalPages())
		.isFirst(pageNotes.isFirst())
		.isLast(pageNotes.isLast())
		.build();
		
		return notes;
	}

}
