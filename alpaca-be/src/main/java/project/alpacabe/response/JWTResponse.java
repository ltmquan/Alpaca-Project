package project.alpacabe.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JWTResponse {

	private String accessToken;
	private final String type = "Bearer ";
	private Long id;
	private String username;
	private Integer role;
}
