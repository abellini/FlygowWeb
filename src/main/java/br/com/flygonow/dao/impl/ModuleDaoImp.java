package br.com.flygonow.dao.impl;

import br.com.flygonow.dao.ModuleDao;
import br.com.flygonow.entities.Module;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ModuleDaoImp  extends GenericDaoImp<Module, Long> implements ModuleDao{

	@Override
	public Collection<Module> listModulesToRole(Long roleId) {
		Collection<Module> modules = null;
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("roleId", roleId);
		try{
			modules = this.listByParams(
				"SELECT mod FROM Module mod " +
				"JOIN mod.roles r " +
				"WHERE r.id = :roleId", 
			params);
		}catch(Exception e){
			System.out.println("Error ->> " + e);
		}
		return modules;
	}
	
	public Collection<Module> listByRoleId(Long roleId) {
		Collection<Module> modules = null;
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("roleId", roleId);
		try{
			modules = this.listByParams(
				"SELECT mod FROM Module mod " +
				"JOIN mod.roles r " +
				"WHERE r.id = :roleId", 
			params);
		}catch(Exception e){
			System.out.println("Error ->> " + e);
		}
		return modules;
	
	}

}
