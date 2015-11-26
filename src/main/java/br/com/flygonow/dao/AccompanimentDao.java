package br.com.flygonow.dao;

import br.com.flygonow.entities.Accompaniment;

import java.util.Collection;
import java.util.List;

public interface AccompanimentDao extends GenericDao<Accompaniment, Long>{

	Collection<Accompaniment> findByFoodId(Long id);

	String getPhotoNameByAcc(Long id);

	String getVideoNameByAcc(Long id);

	List<Accompaniment> listByFoodId(Long id);

	List<Accompaniment> listByCategoryId(Long id);

	List<Accompaniment> listByOperationalAreaId(Long id);

	List<Accompaniment> listByPromotionId(Long id);

	List<Accompaniment> listByAllFoods();

	List<Accompaniment> listByAllCategories();
	
	List<Accompaniment> listByOrderItemId(Long orderItemId);
}
