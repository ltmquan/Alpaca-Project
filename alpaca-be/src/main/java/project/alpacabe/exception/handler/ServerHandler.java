package project.alpacabe.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import project.alpacabe.exception.ServerException;
import project.alpacabe.response.ErrorResponse;

@RestControllerAdvice
public class ServerHandler {

	@ExceptionHandler(ServerException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorResponse handleServerException(ServerException ex) {
		return new ErrorResponse(ex.message, ex.note);
	}
}
