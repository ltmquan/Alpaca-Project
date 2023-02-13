package project.alpacabe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.alpacabe.model.dto.NoteDTO;
import project.alpacabe.service.NoteService;
import project.alpacabe.util.AuthUtil;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/note")
public class NoteController {

	@Autowired
	NoteService noteService;

	@GetMapping("/{id}")
	public ResponseEntity<NoteDTO> findById(@PathVariable Long id) {

		return ResponseEntity.ok(noteService.findById(AuthUtil.authUser().getId(), id));
	}

	@GetMapping("/task/{taskId}")
	public ResponseEntity<List<NoteDTO>> findByTaskId(@PathVariable Long taskId) {

		return ResponseEntity.ok(noteService.findByTaskId(AuthUtil.authUser().getId(), taskId));
	}

	@PostMapping
	public ResponseEntity<NoteDTO> create(@RequestBody NoteDTO noteDTO) {

		return ResponseEntity.ok(noteService.create(AuthUtil.authUser().getId(), noteDTO));
	}

	@PutMapping
	public ResponseEntity<NoteDTO> update(@RequestBody NoteDTO noteDTO) {
		
		return ResponseEntity.ok(noteService.update(AuthUtil.authUser().getId(), noteDTO));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<NoteDTO> deleteById(@PathVariable Long id) {

		return ResponseEntity.ok(noteService.deleteById(AuthUtil.authUser().getId(), id));
	}
}
