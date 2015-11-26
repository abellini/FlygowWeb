package br.com.flygonow.service;

import br.com.flygonow.entities.OperationalArea;

import java.util.List;

public interface OperationalAreaService {

	void delete(Long id);

	List<OperationalArea> listOperationalAreasFromLoggerUser();

}
