package project.alpacabe.service.mapper;

import org.mapstruct.Mapper;

import project.alpacabe.model.User;
import project.alpacabe.model.dto.UserDTO;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<User, UserDTO> {

	@Override
	User toEntity(UserDTO userDTO);

	@Override
	UserDTO toDTO(User user);
}
