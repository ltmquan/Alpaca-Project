package project.alpacabe.model.dto;

import lombok.Data;

@Data
public class NoteDTO {

	private Long id;

	private String name;

	private String content;

	private Long taskId;

	private String taskName;

	private Long userId;

}
