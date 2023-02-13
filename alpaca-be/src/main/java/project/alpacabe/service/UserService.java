package project.alpacabe.service;

import java.util.List;

import project.alpacabe.model.User;
import project.alpacabe.model.dto.UserDTO;

public interface UserService {

	public void login(String username);

	public void logout(Long userId);

	public List<UserDTO> findAll(Long userId);

	public UserDTO findById(Long userId, Long id);

	public User findByUsernameAuth(String username);

	public UserDTO create(UserDTO userDTO);

	public UserDTO update(Long userId, UserDTO userDTO);

	public UserDTO deleteById(Long userId, Long id);
}
