package br.com.flygonow.dao;

import br.com.flygonow.entities.Role;

import java.util.Collection;
import java.util.List;

public interface RoleDao extends GenericDao<Role, Long>{
	Collection<Role> findByUserId(Long userId);

	List<Role> getAllWithModules();

	List<Role> listAllByNameWithModules(String strSearch);

	List<Role> listByAttendantId(Long id);

	List<Role> listByOperationalAreaId(Long id);
}
