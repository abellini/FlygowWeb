package br.com.flygonow.dao;

import br.com.flygonow.entities.Category;

public interface CategoryDao extends GenericDao<Category, Long>{

	String getPhotoNameByCategory(Long id);

}
