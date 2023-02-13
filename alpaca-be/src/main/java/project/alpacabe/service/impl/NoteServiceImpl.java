package project.alpacabe.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.alpacabe.constants.ExceptionConstants;
import project.alpacabe.model.Note;
import project.alpacabe.model.Task;
import project.alpacabe.model.User;
import project.alpacabe.model.dto.NoteDTO;
import project.alpacabe.repository.NoteRepository;
import project.alpacabe.repository.TaskRepository;
import project.alpacabe.repository.UserRepository;
import project.alpacabe.service.NoteService;
import project.alpacabe.service.mapper.NoteMapper;
import project.alpacabe.util.ExceptionUtil;

@Service
public class NoteServiceImpl implements NoteService {

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private NoteMapper noteMapper;

	@Override
	public NoteDTO findById(Long userId, Long id) {
		Optional<Note> noteOptional = noteRepository.findById(id);
		if (noteOptional.isEmpty()) {
			ExceptionUtil.error("NoteService::findById", ExceptionConstants.ID_NOT_FOUND);
		}

		Note note = noteOptional.get();

		if (note.getUser() == null || (note.getUser().getRole() != 1 && note.getUser().getId() != userId)) {
			ExceptionUtil.error("NoteService::findById", ExceptionConstants.INVALID_ACCESS);
		}

		return noteMapper.toDTO(note);
	}

	@Override
	public List<NoteDTO> findByTaskId(Long userId, Long taskId) {
		Optional<Task> taskOptional = taskRepository.findById(taskId);
		if (taskOptional.isEmpty()) {
			ExceptionUtil.error("NoteService::findByTaskId", ExceptionConstants.ID_NOT_FOUND);
		}

		Task task = taskOptional.get();
		if (task.getUser() == null || (task.getUser().getRole() != 1 && task.getUser().getId() != userId)) {
			ExceptionUtil.error("NoteService::findByTaskId", ExceptionConstants.INVALID_ACCESS);
		}

		List<Note> noteList = noteRepository.findByTaskId(taskId);

		return noteMapper.toDTO(noteList);
	}

	@Override
	public NoteDTO create(Long userId, NoteDTO noteDTO) {
		if (noteDTO.getId() != null) {
			ExceptionUtil.error("NoteService::create", ExceptionConstants.CREATE_ID_NOT_NULL);
		}

		Note note = noteMapper.toEntity(noteDTO);

		Optional<Task> taskOptional = taskRepository.findById(noteDTO.getTaskId());
		if (taskOptional.isEmpty()) {
			ExceptionUtil.error("NoteService::create", ExceptionConstants.ID_NOT_FOUND, "task");
		}

		Optional<User> userOptional = userRepository.findById(noteDTO.getUserId());
		if (userOptional.isEmpty()) {
			ExceptionUtil.error("NoteService::create", ExceptionConstants.ID_NOT_FOUND, "user");
		}

		Task task = taskOptional.get();
		User user = userOptional.get();

		if (task.getUser() == null || task.getUser().getId() != userId || user.getId() != userId) {
			ExceptionUtil.error("NoteService::create", ExceptionConstants.INVALID_ACCESS);
		}

		note.setTask(task);
		note.setUser(user);

		return noteMapper.toDTO(noteRepository.save(note));
	}

	@Override
	public NoteDTO update(Long userId, NoteDTO noteDTO) {
		if (noteDTO.getId() == null || !noteRepository.existsById(noteDTO.getId())) {
			ExceptionUtil.error("NoteService::update", ExceptionConstants.UPDATE_ID_INVALID);
		}

		Note note = noteMapper.toEntity(noteDTO);

		Optional<Task> taskOptional = taskRepository.findById(noteDTO.getTaskId());
		if (taskOptional.isEmpty()) {
			ExceptionUtil.error("NoteService::update", ExceptionConstants.ID_NOT_FOUND, "task");
		}

		Optional<User> userOptional = userRepository.findById(noteDTO.getUserId());
		if (userOptional.isEmpty()) {
			ExceptionUtil.error("NoteService::update", ExceptionConstants.ID_NOT_FOUND, "user");
		}

		Task task = taskOptional.get();
		User user = userOptional.get();

		if (task.getUser() == null || task.getUser().getId() != userId || user.getId() != userId) {
			ExceptionUtil.error("NoteService::update", ExceptionConstants.INVALID_ACCESS);
		}

		note.setTask(task);
		note.setUser(user);

		return noteMapper.toDTO(noteRepository.save(note));
	}

	@Override
	public NoteDTO deleteById(Long userId, Long id) {
		Optional<Note> noteOptional = noteRepository.findById(id);
		if (noteOptional.isEmpty()) {
			ExceptionUtil.error("NoteService::deleteById", ExceptionConstants.ID_NOT_FOUND);
		}

		Note note = noteOptional.get();
		if (note.getUser() == null || note.getUser().getId() != userId) {
			ExceptionUtil.error("NoteService::deleteById", ExceptionConstants.INVALID_ACCESS);
		}

		noteRepository.deleteById(id);

		return noteMapper.toDTO(note);
	}

}
