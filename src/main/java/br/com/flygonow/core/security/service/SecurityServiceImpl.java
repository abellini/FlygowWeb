/**
 * 
 */
package br.com.flygonow.core.security.service;

import br.com.flygonow.dao.AttendantDao;
import br.com.flygonow.dao.ModuleDao;
import br.com.flygonow.entities.Attendant;
import br.com.flygonow.entities.Module;
import br.com.flygonow.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 */
@Service
public class SecurityServiceImpl implements SecurityService {

	@Autowired
	private AttendantDao userDAO;
	
	@Autowired
	private ModuleDao moduleDAO;

	/*
	 * (non-Javadoc)
	 * @see br.com.flygonow.core.security.service.SecurityService#getLoggedUserModel()
	 */
	@Override
	public Attendant getLoggedUserModel() {
		String userName = 
			SecurityContextHolder.getContext().getAuthentication().getName();
		Attendant user = this.userDAO.findByLogin(userName);
		for (Role role : user.getRoles()) {
			Collection<Module> colMod = this.moduleDAO.listModulesToRole(role.getId());
			Set<Module> modules = new HashSet<Module>();
			modules.addAll(colMod);
			role.setModules(modules);
		}
		return user;
	}

	@Override
	public String getLoggedUser() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

	@Override
	public Collection<GrantedAuthority> getLoggedUserAuthorities() {
		return (Collection<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
	}

}
