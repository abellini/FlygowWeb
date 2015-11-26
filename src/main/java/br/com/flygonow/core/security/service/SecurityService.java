package br.com.flygonow.core.security.service;

import br.com.flygonow.entities.Attendant;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface SecurityService {

	Attendant getLoggedUserModel();

	String getLoggedUser();

	Collection<GrantedAuthority> getLoggedUserAuthorities();
}
