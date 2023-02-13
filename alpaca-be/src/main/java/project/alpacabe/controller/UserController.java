package project.alpacabe.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.alpacabe.model.dto.UserDTO;
import project.alpacabe.response.JWTResponse;
import project.alpacabe.security.service.UserDetailsImpl;
import project.alpacabe.service.UserService;
import project.alpacabe.util.AuthUtil;
import project.alpacabe.util.JWTUtil;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	AuthenticationManager authManager;

	@Autowired
	JWTUtil jwtUtil;

	@PostMapping("/login")
	public ResponseEntity<JWTResponse> login(@RequestBody UserDTO userDTO) {
		Authentication auth = authManager
				.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));

		userService.login(userDTO.getUsername());

		SecurityContextHolder.getContext().setAuthentication(auth);
		String jwt = null;
		try {
			jwt = jwtUtil.generateJwtToken(auth);
		} catch (ParseException e) {
		}

		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();

		return ResponseEntity
				.ok(new JWTResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getRole()));
	}

	@GetMapping("/logout")
	public ResponseEntity<Void> logout() {
		userService.logout(AuthUtil.authUser().getId());
		return ResponseEntity.ok(null);
	}

	@GetMapping
	public ResponseEntity<List<UserDTO>> findAll() {

		return ResponseEntity.ok(userService.findAll(AuthUtil.authUser().getId()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable Long id) {

		return ResponseEntity.ok(userService.findById(AuthUtil.authUser().getId(), id));
	}

	@PostMapping
	public ResponseEntity<UserDTO> create(@RequestBody UserDTO userDTO) {
		
		return ResponseEntity.ok(userService.create(userDTO));
	}

	@PutMapping
	public ResponseEntity<UserDTO> update(@RequestBody UserDTO userDTO) {

		return ResponseEntity.ok(userService.update(AuthUtil.authUser().getId(), userDTO));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<UserDTO> deleteById(@PathVariable Long id) {

		return ResponseEntity.ok(userService.deleteById(AuthUtil.authUser().getId(), id));
	}
}
