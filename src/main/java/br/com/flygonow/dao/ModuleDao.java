package br.com.flygonow.dao;

import br.com.flygonow.entities.Module;

import java.util.Collection;

public interface ModuleDao extends GenericDao<Module, Long>{

	Collection<Module> listModulesToRole(Long id);

	Collection<Module> listByRoleId(Long id);

}
