package br.com.flygonow.dao;

import br.com.flygonow.entities.Tablet;

import java.util.Collection;
import java.util.List;

public interface TabletDao extends GenericDao<Tablet, Long>{

	Collection<Tablet> listByNumber(String strSearch);

	List<Tablet> listByCoinId(Long id);

	List<Tablet> listByAttendantId(Long id);

	List<Tablet> listByAdvertisementId(Long id);

	Tablet getByNumber(Short number);
}
