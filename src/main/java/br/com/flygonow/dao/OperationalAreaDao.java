package br.com.flygonow.dao;

import br.com.flygonow.entities.OperationalArea;

import java.util.List;

public interface OperationalAreaDao extends GenericDao<OperationalArea, Long>{

	List<OperationalArea> listByRoleId(Long id);


}
