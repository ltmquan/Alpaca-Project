package project.alpacabe.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import project.alpacabe.model.Note;
import project.alpacabe.model.dto.NoteDTO;

@Mapper(componentModel = "spring")
public interface NoteMapper extends EntityMapper<Note, NoteDTO> {

	@Override
	Note toEntity(NoteDTO noteDTO);

	@Override
	@Mappings({ @Mapping(source = "task.id", target = "taskId"), @Mapping(source = "task.name", target = "taskName"),
			@Mapping(source = "user.id", target = "userId"), })
	NoteDTO toDTO(Note note);

}
