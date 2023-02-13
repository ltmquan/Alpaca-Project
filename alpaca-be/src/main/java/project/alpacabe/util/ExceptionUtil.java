package project.alpacabe.util;

import lombok.extern.slf4j.Slf4j;
import project.alpacabe.exception.ServerException;

@Slf4j
public class ExceptionUtil {

	public static void error(String caller, String msg, String note) {
		log.error("@%s: %s (%s)".formatted(caller, msg, note));
		throw new ServerException(caller, msg, note);
	}

	public static void error(String caller, String msg) {
		error(caller, msg, "");
	}
}
