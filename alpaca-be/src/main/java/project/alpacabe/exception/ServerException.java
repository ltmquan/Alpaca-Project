package project.alpacabe.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ServerException extends RuntimeException {

	public String caller;
	public String message;
	public String note;
}
