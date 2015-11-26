package br.com.flygonow.dao.impl;

import br.com.flygonow.dao.ModuleDao;
import br.com.flygonow.dao.RoleDao;
import br.com.flygonow.entities.Role;
import br.com.flygonow.enums.FetchTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RoleDaoImp extends GenericDaoImp<Role, Long> implements RoleDao{

	@Autowired
	private ModuleDao moduleDAO;
	
	@Override
	public Collection<Role> findByUserId(Long userId) {
		Collection<Role> roles = null;
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userId", userId);
		try{
			roles = this.listByParams(
				"SELECT rol FROM Role rol " +
				"JOIN rol.attendants a " +
				"WHERE a.id = :userId", 
			params);
		}catch(Exception e){
			System.out.println("Error ->> " + e);
		}
		return roles;
	}

	@Override
	public List<Role> getAllWithModules() {
		List<Role> roles = null;
		try{
			roles = this.getAll(FetchTypeEnum.LEFT.getName(), "modules");
		}catch(Exception e){
			System.out.println("Error ->> " + e);
		}
		return roles;
	}

	@Override
	public List<Role> listAllByNameWithModules(String strSearch) {
		List<Role> roles = null;
		try{
			roles = this.listByName(strSearch, FetchTypeEnum.LEFT.getName(), "modules");
		}catch(Exception e){
			System.out.println("Error ->> " + e);
		}
		return roles;
	}

	@Override
	public List<Role> listByAttendantId(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return this.listByParams("SELECT r FROM Role r LEFT JOIN FETCH r.modules m LEFT JOIN r.attendants a WHERE a.id = :id", params);
	}

	@Override
	public List<Role> listByOperationalAreaId(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return this.listByParams("SELECT r FROM Role r LEFT JOIN FETCH r.modules m LEFT JOIN r.operationalAreas oa WHERE oa.id = :id", params);
	}

}
