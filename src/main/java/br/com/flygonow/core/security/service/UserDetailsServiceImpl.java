package br.com.flygonow.core.security.service;

import br.com.flygonow.dao.AttendantDao;
import br.com.flygonow.entities.Attendant;
import br.com.flygonow.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private AttendantDao attendantDao;

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		Attendant userModel = attendantDao.findByLogin(username);
		if (userModel == null)
			throw new UsernameNotFoundException("User not found: " + username);
		else {
			User newUser = makeUser(userModel);
			return newUser;
		}
	}

	private User makeUser(Attendant user) {
		String login = user.getLogin();
		String password = user.getPassword();
		boolean credentialsNonExpired = true;
		boolean enabled = true;
		boolean accountNonLocked = true;
		boolean accountNonExpired = true;
		List<GrantedAuthority> role = makeGrantedAuthorities(user);
		User roleUser = new User(login, password, enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked, role);
		return roleUser;
	}

	@SuppressWarnings("deprecation")
	private List<GrantedAuthority> makeGrantedAuthorities(Attendant user) {
		List<GrantedAuthority> result = new ArrayList<GrantedAuthority>();
		for (Role role : user.getRoles()) {
			result.add(new SimpleGrantedAuthority(role.getName()));
		}
		return result;
	}
}
