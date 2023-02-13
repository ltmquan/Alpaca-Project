package project.alpacabe.model.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class TaskDTO {

	private Long id;

	private String name;

	private Date deadline;

	private Integer priorityLevel;

	private String description;

	private Boolean finished;

	private Long parentId;

	private String parentName;

	private Long userId;
	
	private Integer progress;
	
	private List<TaskDTO> children;
	
	private List<NoteDTO> notes;

}
