package br.com.flygonow.dao;

import br.com.flygonow.entities.AccompanimentMedia;

import java.util.List;

public interface AccompanimentMediaDao extends GenericDao<AccompanimentMedia, Long>{

	byte[] getPhoto(Long id);

	byte[] getVideo(Long id);
	
	AccompanimentMedia findByAccompaniment(Long id, Byte mediaType);

	List<AccompanimentMedia> listByAccompaniment(Long id, Byte mediaType);

}
