package project.alpacabe.security.service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import project.alpacabe.constants.DBConstants;
import project.alpacabe.model.User;

@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7897953722110308008L;

	private Long id;
	private String username;
	private String password;
	private Integer role;
	private GrantedAuthority authority;

	public static UserDetailsImpl build(User user) {
		GrantedAuthority authority = new SimpleGrantedAuthority(DBConstants.ROLE[user.getRole()-1]);

		return new UserDetailsImpl(user.getId(), user.getUsername(), user.getPassword(), user.getRole(), authority);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(authority);
	}

	public GrantedAuthority getAuthority() {
		return authority;
	}

	public Long getId() {
		return id;
	}

	public Integer getRole() {
		return role;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}