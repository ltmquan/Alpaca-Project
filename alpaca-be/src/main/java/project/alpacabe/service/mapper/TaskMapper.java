package project.alpacabe.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import project.alpacabe.model.Task;
import project.alpacabe.model.dto.TaskDTO;

@Mapper(componentModel = "spring")
public interface TaskMapper extends EntityMapper<Task, TaskDTO> {

	@Override
	Task toEntity(TaskDTO taskDTO);

	@Override
	@Mappings({ @Mapping(source = "parent.id", target = "parentId"),
			@Mapping(source = "parent.name", target = "parentName"), @Mapping(source = "user.id", target = "userId"), })
	TaskDTO toDTO(Task task);

}
