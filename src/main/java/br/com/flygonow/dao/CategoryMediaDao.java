package br.com.flygonow.dao;

import br.com.flygonow.entities.CategoryMedia;

import java.util.List;

public interface CategoryMediaDao extends GenericDao<CategoryMedia, Long>{

	byte[] getPhoto(Long id);

	CategoryMedia findByCategory(Long id, Byte mediaType);

	List<CategoryMedia> listByCategoryId(Long id, Byte mediaType);

}
