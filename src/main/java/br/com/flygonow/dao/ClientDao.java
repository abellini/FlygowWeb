package br.com.flygonow.dao;

import br.com.flygonow.entities.Client;

import java.util.List;

public interface ClientDao  extends GenericDao<Client, Long>{

	List<Client> listByRoleId(Long id);

}
