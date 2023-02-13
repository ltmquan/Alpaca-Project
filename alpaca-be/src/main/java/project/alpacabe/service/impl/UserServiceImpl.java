package project.alpacabe.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import project.alpacabe.constants.ExceptionConstants;
import project.alpacabe.model.User;
import project.alpacabe.model.dto.UserDTO;
import project.alpacabe.repository.UserRepository;
import project.alpacabe.service.UserService;
import project.alpacabe.service.mapper.UserMapper;
import project.alpacabe.util.ExceptionUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserMapper userMapper;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public void login(String username) {
		User user = findByUsernameAuth(username);

		user.setIsLoggedIn(true);
		
		userRepository.save(user);
	}

	@Override
	public void logout(Long userId) {
		Optional<User> userOptional = userRepository.findById(userId);
		if (userOptional.isEmpty()) {
			ExceptionUtil.error("UserService::logout", ExceptionConstants.ID_NOT_FOUND);
		}

		User user = userOptional.get();
		user.setIsLoggedIn(false);
		
		userRepository.save(user);
	}

	@Override
	public List<UserDTO> findAll(Long userId) {
		Optional<User> userOptional = userRepository.findById(userId);
		if (userOptional.isEmpty()) {
			ExceptionUtil.error("UserService::findAll", ExceptionConstants.ID_NOT_FOUND);
		}

		User user = userOptional.get();
		if (user.getRole() != 1) {
			ExceptionUtil.error("UserService::findAll", ExceptionConstants.INVALID_ACCESS);
		}

		return userMapper.toDTO(userRepository.findAll());
	}

	@Override
	public User findByUsernameAuth(String username) {
		List<User> userList = userRepository.findByUsername(username);
		if (userList.isEmpty()) {
			ExceptionUtil.error("UserService::findByUsernameAuth", ExceptionConstants.USERNAME_NOT_FOUND);
		}

		if (userList.size() > 1) {
			ExceptionUtil.error("UserService::findByUsernameAuth", ExceptionConstants.USERNAME_NOT_FOUND);
		}

		return userList.get(0);
	}

	@Override
	public UserDTO findById(Long userId, Long id) {
		Optional<User> adminOptional = userRepository.findById(userId);
		if (adminOptional.isEmpty()) {
			ExceptionUtil.error("UserService::findById", ExceptionConstants.ID_NOT_FOUND);
		}

		User admin = adminOptional.get();
		if (admin.getRole() != 1) {
			ExceptionUtil.error("UserService::findById", ExceptionConstants.INVALID_ACCESS);
		}

		Optional<User> userOptional = userRepository.findById(id);
		if (userOptional.isEmpty()) {
			ExceptionUtil.error("UserService::findById", ExceptionConstants.ID_NOT_FOUND);
		}

		return userMapper.toDTO(userOptional.get());
	}

	@Override
	public UserDTO create(UserDTO userDTO) {
		if (userDTO.getId() != null) {
			ExceptionUtil.error("UserService::create", ExceptionConstants.CREATE_ID_NOT_NULL);
		}

		if (userRepository.existsByUsername(userDTO.getUsername())) {
			ExceptionUtil.error("UserService::create", ExceptionConstants.USERNAME_DUPLICATE);
		}

		User user = userMapper.toEntity(userDTO);
		user.setIsLoggedIn(false);
		user.setRole(2);
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		return userMapper.toDTO(userRepository.save(user));
	}

	@Override
	public UserDTO update(Long userId, UserDTO userDTO) {
		if (userDTO.getId() == null || !userRepository.existsById(userDTO.getId())) {
			ExceptionUtil.error("UserService::update", ExceptionConstants.UPDATE_ID_INVALID);
		}

		User user = userMapper.toEntity(userDTO);
		if (user.getId() != userId) {
			ExceptionUtil.error("UserService::update", ExceptionConstants.INVALID_ACCESS);
		}

		return userMapper.toDTO(userRepository.save(user));
	}

	@Override
	public UserDTO deleteById(Long userId, Long id) {
		Optional<User> userOptional = userRepository.findById(id);
		if (userOptional.isEmpty()) {
			ExceptionUtil.error("UserService::deleteById", ExceptionConstants.ID_NOT_FOUND);
		}

		User user = userOptional.get();
		if (user.getId() != userId) {
			ExceptionUtil.error("UserService::deleteById", ExceptionConstants.INVALID_ACCESS);
		}

		userRepository.deleteById(id);

		return userMapper.toDTO(user);
	}
}
