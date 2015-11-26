package br.com.flygonow.dao.impl;

import br.com.flygonow.dao.ClientDao;
import br.com.flygonow.entities.Client;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ClientDaoImp extends GenericDaoImp<Client, Long> implements ClientDao {

	@Override
	public List<Client> listByRoleId(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return this.listByParams("SELECT c FROM Client c LEFT JOIN c.roles r WHERE r.id = :id", params);
	}

}
