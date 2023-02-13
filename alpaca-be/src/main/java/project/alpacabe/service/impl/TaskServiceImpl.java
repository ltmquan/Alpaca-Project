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
import project.alpacabe.model.dto.TaskDTO;
import project.alpacabe.repository.NoteRepository;
import project.alpacabe.repository.TaskRepository;
import project.alpacabe.repository.UserRepository;
import project.alpacabe.service.TaskService;
import project.alpacabe.service.mapper.NoteMapper;
import project.alpacabe.service.mapper.TaskMapper;
import project.alpacabe.util.ExceptionUtil;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private TaskMapper taskMapper;

	@Autowired
	private NoteMapper noteMapper;

	@Override
	public TaskDTO findById(Long userId, Long id) {
		Optional<Task> taskOptional = taskRepository.findById(id);
		if (taskOptional.isEmpty()) {
			ExceptionUtil.error("TaskService::findById", ExceptionConstants.ID_NOT_FOUND);
		}

		Task task = taskOptional.get();
		if (task.getUser() == null || (task.getUser().getRole() != 1 && task.getUser().getId() != userId)) {
			ExceptionUtil.error("TaskService::findById", ExceptionConstants.INVALID_ACCESS);
		}

		TaskDTO ret = taskMapper.toDTO(task);
		
		findChildren(userId, ret);
		ret.setProgress(calcProgress(ret));

		return ret;
	}

	@Override
	public List<TaskDTO> findByUserId(Long userId, Long userId2) {
		if (userId2 != userId) {
			ExceptionUtil.error("TaskService::findByUserId", ExceptionConstants.INVALID_ACCESS);
		}

		List<Task> taskList = taskRepository.findByUserIdAndParentId(userId2, null);
		List<TaskDTO> ret = taskMapper.toDTO(taskList);

		findChildren(userId, ret);
		for (TaskDTO t : ret) {
			t.setProgress(calcProgress(t));
		}

		return ret;
	}

	@Override
	public List<TaskDTO> findByParentId(Long userId, Long parentId) {
		Optional<Task> taskOptional = taskRepository.findById(parentId);
		if (taskOptional.isEmpty()) {
			ExceptionUtil.error("TaskService::findByParentId", ExceptionConstants.ID_NOT_FOUND);
		}

		Task task = taskOptional.get();
		if (task.getId() != userId) {
			ExceptionUtil.error("TaskService::findByParentId", ExceptionConstants.INVALID_ACCESS);
		}

		List<Task> taskList = taskRepository.findByParentId(parentId);
		List<TaskDTO> ret = taskMapper.toDTO(taskList);
		
		findChildren(userId, ret);
		for (TaskDTO t : ret) {
			t.setProgress(calcProgress(t));
		}

		return ret;
	}

	@Override
	public TaskDTO create(Long userId, TaskDTO taskDTO) {
		if (taskDTO.getId() != null) {
			ExceptionUtil.error("TaskService::create", ExceptionConstants.CREATE_ID_NOT_NULL);
		}

		Task task = taskMapper.toEntity(taskDTO);

		if (taskDTO.getParentId() != null) {
			Optional<Task> taskOptional = taskRepository.findById(taskDTO.getParentId());
			if (taskOptional.isEmpty()) {
				ExceptionUtil.error("TaskService::create", ExceptionConstants.ID_NOT_FOUND, "task");
			}

			Task taskParent = taskOptional.get();

			if (taskParent.getUser() == null || taskParent.getUser().getId() != userId) {
				ExceptionUtil.error("TaskService::create", ExceptionConstants.INVALID_ACCESS);
			}

			task.setParent(taskParent);
		}

		Optional<User> userOptional = userRepository.findById(taskDTO.getUserId());
		if (userOptional.isEmpty()) {
			ExceptionUtil.error("TaskService::create", ExceptionConstants.ID_NOT_FOUND, "user");
		}

		User user = userOptional.get();

		if (user.getId() != userId) {
			ExceptionUtil.error("TaskService::create", ExceptionConstants.INVALID_ACCESS);
		}

		task.setUser(user);

		return taskMapper.toDTO(taskRepository.save(task));
	}

	@Override
	public TaskDTO update(Long userId, TaskDTO taskDTO) {
		if (taskDTO.getId() == null || !taskRepository.existsById(taskDTO.getId())) {
			ExceptionUtil.error("TaskService::update", ExceptionConstants.UPDATE_ID_INVALID);
		}

		Task task = taskMapper.toEntity(taskDTO);

		if (taskDTO.getParentId() != null) {
			Optional<Task> taskOptional = taskRepository.findById(taskDTO.getParentId());
			if (taskOptional.isEmpty()) {
				ExceptionUtil.error("TaskService::update", ExceptionConstants.ID_NOT_FOUND, "task");
			}

			Task taskParent = taskOptional.get();

			if (taskParent.getUser() == null || taskParent.getUser().getId() != userId) {
				ExceptionUtil.error("TaskService::update", ExceptionConstants.INVALID_ACCESS);
			}

			task.setParent(taskParent);
		}

		Optional<User> userOptional = userRepository.findById(taskDTO.getUserId());
		if (userOptional.isEmpty()) {
			ExceptionUtil.error("TaskService::update", ExceptionConstants.ID_NOT_FOUND, "user");
		}

		User user = userOptional.get();

		if (user.getId() != userId) {
			ExceptionUtil.error("TaskService::update", ExceptionConstants.INVALID_ACCESS);
		}

		task.setUser(user);
		
		Task taskFromDB = taskRepository.findById(task.getId()).get();
		if (!taskFromDB.getFinished() && task.getFinished()) {
			markFinishedRec(taskFromDB);
		}

		return taskMapper.toDTO(taskRepository.save(task));
	}
	
	@Override
	public TaskDTO markFinished(Long userId, Long id) {
		Optional<Task> taskOptional = taskRepository.findById(id);
		if (taskOptional.isEmpty()) {
			ExceptionUtil.error("TaskService::markFinished", ExceptionConstants.ID_NOT_FOUND);
		}
		
		Task task = taskOptional.get();
		if (task.getUser() == null || task.getUser().getId() != userId) {
			ExceptionUtil.error("TaskService::markFinished", ExceptionConstants.INVALID_ACCESS);
		}
		
		task.setFinished(true);
		markFinishedRec(task);
		
		task = taskRepository.save(task);
		propogateFinished(task);
		
		return taskMapper.toDTO(task);
	}
	
	@Override
	public TaskDTO markUnfinished(Long userId, Long id) {
		Optional<Task> taskOptional = taskRepository.findById(id);
		if (taskOptional.isEmpty()) {
			ExceptionUtil.error("TaskService::markUnfinished", ExceptionConstants.ID_NOT_FOUND);
		}
		
		Task task = taskOptional.get();
		if (task.getUser() == null || task.getUser().getId() != userId) {
			ExceptionUtil.error("TaskService::markUnfinished", ExceptionConstants.INVALID_ACCESS);
		}
		
		task.setFinished(false);
		markUnfinishedRec(task);
		
		task = taskRepository.save(task);
		propogateUnfinished(task);
		
		return taskMapper.toDTO(task);
	}

	@Override
	public TaskDTO deleteById(Long userId, Long id) {
		Optional<Task> taskOptional = taskRepository.findById(id);
		if (taskOptional.isEmpty()) {
			ExceptionUtil.error("TaskService::deleteById", ExceptionConstants.ID_NOT_FOUND);
		}

		Task task = taskOptional.get();
		if (task.getUser() == null || task.getUser().getId() != userId) {
			ExceptionUtil.error("TaskService::deleteById", ExceptionConstants.INVALID_ACCESS);
		}

		taskRepository.deleteById(id);

		return taskMapper.toDTO(task);
	}

	private void findChildren(Long userId, List<TaskDTO> taskList) {
		for (TaskDTO t : taskList) {
			List<Task> tasks = taskRepository.findByUserIdAndParentId(userId, t.getId());
			List<TaskDTO> children = taskMapper.toDTO(tasks);

			List<Note> notes = noteRepository.findByTaskId(t.getId());
			List<NoteDTO> noteList = noteMapper.toDTO(notes);

			t.setChildren(children);
			t.setNotes(noteList);

			findChildren(userId, t.getChildren());
		}
	}

	private void findChildren(Long userId, TaskDTO t) {
		List<Task> tasks = taskRepository.findByUserIdAndParentId(userId, t.getId());
		List<TaskDTO> children = taskMapper.toDTO(tasks);

		List<Note> notes = noteRepository.findByTaskId(t.getId());
		List<NoteDTO> noteList = noteMapper.toDTO(notes);

		t.setChildren(children);
		t.setNotes(noteList);

		findChildren(userId, t.getChildren());
	}

	private int calcProgress(TaskDTO taskDTO) {
		int progress = 0;

		for (TaskDTO t : taskDTO.getChildren()) {
			t.setProgress(calcProgress(t));
			progress += t.getProgress() / (taskDTO.getChildren().size()+1);
		}
		
		if (taskDTO.getFinished())
			return 100;

		return progress;
	}
	
	private void markFinishedRec(Task task) {
		List<Task> children = taskRepository.findByParentId(task.getId());
		for (Task t : children) {
			t.setFinished(true);
			markFinishedRec(t);
			taskRepository.save(t);
		}
	}
	
	private void markUnfinishedRec(Task task) {
		List<Task> children = taskRepository.findByParentId(task.getId());
		for (Task t : children) {
			t.setFinished(false);
			markUnfinishedRec(t);
			taskRepository.save(t);
		}
	}
	
	private void propogateFinished(Task task) {
		if (task.getParent() == null) return;
		
		Task parent = task.getParent();
		List<Task> children = taskRepository.findByParentId(parent.getId());
		for (Task t : children) {
			if (!t.getFinished()) {
				return;
			}
		}
		parent.setFinished(true);
		propogateFinished(taskRepository.save(parent));
	}
	
	private void propogateUnfinished(Task task) {
		if (task.getParent() == null) return;
		
		Task parent = task.getParent();
		if (parent.getFinished()) {
			parent.setFinished(false);
			propogateUnfinished(taskRepository.save(parent));
		}
	}
}
