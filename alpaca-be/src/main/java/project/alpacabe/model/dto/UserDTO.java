package project.alpacabe.model.dto;

import lombok.Data;

@Data
public class UserDTO {

	private Long id;

	private String username;

	private String password;

	private Integer role;
}
