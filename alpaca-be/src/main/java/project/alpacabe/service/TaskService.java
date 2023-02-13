package project.alpacabe.service;

import java.util.List;

import project.alpacabe.model.dto.TaskDTO;

public interface TaskService {

	public TaskDTO findById(Long userId, Long id);

	public List<TaskDTO> findByUserId(Long userId, Long userId2);

	public List<TaskDTO> findByParentId(Long userId, Long parentId);

	public TaskDTO create(Long userId, TaskDTO taskDTO);

	public TaskDTO update(Long userId, TaskDTO taskDTO);
	
	public TaskDTO markFinished(Long userId, Long id);
	
	public TaskDTO markUnfinished(Long userId, Long id);

	public TaskDTO deleteById(Long userId, Long id);
}
