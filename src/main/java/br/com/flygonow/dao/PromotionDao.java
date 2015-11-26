package br.com.flygonow.dao;

import br.com.flygonow.entities.Promotion;

import java.util.Collection;
import java.util.List;

public interface PromotionDao extends GenericDao<Promotion, Long>{

	Collection<Promotion> getAllWithItems();

	Collection<Promotion> listAllByNameWithItems(String strSearch);

	String getVideoNameByPromo(Long id);

	String getPhotoNameByPromo(Long id);

	List<Promotion> listByAccompanimentId(Long id);

	List<Promotion> listByFoodId(Long id);

	List<Promotion> listByCategoryId(Long id);
	
	List<Promotion> listByActiveState(boolean isActive);
}
