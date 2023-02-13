package project.alpacabe.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import project.alpacabe.security.service.UserDetailsImpl;

public class AuthUtil {

	public static UserDetailsImpl authUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl user = null;
		try {
			user = (UserDetailsImpl) auth.getPrincipal();
		} catch (ClassCastException e) {
			ExceptionUtil.error("AuthUtil::authUser", "unauthenticated user");
		}

		return user;
	}
}
