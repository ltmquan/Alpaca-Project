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

import project.alpacabe.model.dto.TaskDTO;
import project.alpacabe.service.TaskService;
import project.alpacabe.util.AuthUtil;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/task")
public class TaskController {

	@Autowired
	TaskService taskService;

	@GetMapping("/{id}")
	public ResponseEntity<TaskDTO> findById(@PathVariable Long id) {

		return ResponseEntity.ok(taskService.findById(AuthUtil.authUser().getId(), id));
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<List<TaskDTO>> findByUserId(@PathVariable Long userId) {
		
		return ResponseEntity.ok(taskService.findByUserId(AuthUtil.authUser().getId(), userId));
	}

	@GetMapping("/parent/{parentId}")
	public ResponseEntity<List<TaskDTO>> findByParentId(@PathVariable Long parentId) {

		return ResponseEntity.ok(taskService.findByParentId(AuthUtil.authUser().getId(), parentId));
	}

	@PostMapping
	public ResponseEntity<TaskDTO> create(@RequestBody TaskDTO taskDTO) {

		return ResponseEntity.ok(taskService.create(AuthUtil.authUser().getId(), taskDTO));
	}

	@PutMapping
	public ResponseEntity<TaskDTO> update(@RequestBody TaskDTO taskDTO) {

		return ResponseEntity.ok(taskService.update(AuthUtil.authUser().getId(), taskDTO));
	}

	@PutMapping("/finished/{id}")
	public ResponseEntity<TaskDTO> markFinished(@PathVariable Long id) {
		
		return ResponseEntity.ok(taskService.markFinished(AuthUtil.authUser().getId(), id));
	}

	@PutMapping("/unfinished/{id}")
	public ResponseEntity<TaskDTO> markUnfinished(@PathVariable Long id) {
		
		return ResponseEntity.ok(taskService.markUnfinished(AuthUtil.authUser().getId(), id));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<TaskDTO> deleteById(@PathVariable Long id) {

		return ResponseEntity.ok(taskService.deleteById(AuthUtil.authUser().getId(), id));
	}
}
