package br.com.flygonow.dao.impl;

import br.com.flygonow.dao.OperationalAreaDao;
import br.com.flygonow.entities.OperationalArea;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OperationalAreaDaoImp extends GenericDaoImp<OperationalArea, Long> implements OperationalAreaDao{

	@Override
	public List<OperationalArea> listByRoleId(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return this.listByParams("SELECT op FROM OperationalArea op LEFT JOIN op.roles r WHERE r.id = :id", params);
	}


}
