package br.com.flygonow.dao;

import br.com.flygonow.entities.Food;

import java.util.Collection;
import java.util.List;

public interface FoodDao extends GenericDao<Food, Long> {

	List<Food> getAllWithAccompaniments();

	Collection<Food> findByPromotionId(Long id);

	List<Food> listAllByNameWithAccompaniments(String strSearch);

	String getPhotoNameByFood(Long id);

	String getVideoNameByFood(Long id);

	List<Food> listByAccompanimentId(Long id);

	Collection<Food> listByPromotionId(Long id);

	List<Food> listByCategoryId(Long id);

	List<Food> listByOperationalAreaId(Long id);

	List<Food> getAllActives();

	List<Food> findAllByAllPromotions();


}
