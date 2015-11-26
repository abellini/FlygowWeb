package br.com.flygonow.dao.impl;

import br.com.flygonow.dao.AccompanimentDao;
import br.com.flygonow.dao.FoodDao;
import br.com.flygonow.dao.PromotionDao;
import br.com.flygonow.entities.Food;
import br.com.flygonow.enums.FetchTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FoodDaoImp extends GenericDaoImp<Food, Long> implements FoodDao {

	@Autowired
	private AccompanimentDao accompanimentDAO;
	
	@Autowired
	private PromotionDao promotionDAO;
	
	@Override
	public List<Food> getAllWithAccompaniments() {
		List<Food> foods = null;
		try{
			foods = this.getAll(FetchTypeEnum.LEFT.getName(), "accompaniments");
		}catch(Exception e){
			System.out.println("Error ->> " + e);
		}
		return foods;
	}

	@Override
	public Collection<Food> listByPromotionId(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return this.listByParams("SELECT f FROM Food f LEFT JOIN f.promotions p WHERE p.id = :id", params);
	}
	
	@Override
	public Collection<Food> findByPromotionId(Long promotionId) {
		Collection<Food> foods = null;
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("promotionId", promotionId);
		try{
			foods = this.listByParams(
				"SELECT food FROM Food food " +
				"JOIN food.promotions p " +
				"WHERE p.id = :promotionId", 
			params);
		}catch(Exception e){
			System.out.println("Error ->> " + e);
		}
		return foods;
	}

	@Override
	public List<Food> listAllByNameWithAccompaniments(String strSearch) {
		List<Food> foods = null;
		try{
			foods = this.listByName(strSearch, FetchTypeEnum.LEFT.getName(), "accompaniments");
		}catch(Exception e){
			System.out.println("Error ->> " + e);
		}
		return foods;
	}

	@Override
	public String getPhotoNameByFood(Long id) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Food> criteria = criteriaBuilder.createQuery(Food.class);
		Root<Food> root = criteria.from( Food.class );
		criteria.multiselect( root.get("photoName"));
		criteria.where( criteriaBuilder.equal( root.get( "id" ), id ) );
		try{
			Food resp = getEntityManager().createQuery( criteria ).getSingleResult();
			return resp.getPhotoName();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public String getVideoNameByFood(Long id) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Food> criteria = criteriaBuilder.createQuery(Food.class);
		Root<Food> root = criteria.from( Food.class );
		criteria.multiselect( root.get("videoName"));
		criteria.where( criteriaBuilder.equal( root.get( "id" ), id ) );
		try{
			Food resp = getEntityManager().createQuery( criteria ).getSingleResult();
			return resp.getVideoName();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<Food> listByAccompanimentId(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return this.listByParams("SELECT f FROM Food f LEFT JOIN f.accompaniments a WHERE a.id = :id", params);
	}


	@Override
	public List<Food> listByCategoryId(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return this.listByParams("SELECT f FROM Food f LEFT JOIN f.category c WHERE c.id = :id", params);
	}

	@Override
	public List<Food> listByOperationalAreaId(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return this.listByParams("SELECT f FROM Food f LEFT JOIN f.operationalArea oa WHERE oa.id = :id", params);
	}

	@Override
	public List<Food> getAllActives() {
		return this.listByQuery("SELECT f FROM Food f WHERE f.isActive = true");
	}

	@Override
	public List<Food> findAllByAllPromotions() {
		return this.listByQuery("SELECT f FROM Food f JOIN FETCH f.promotions p WHERE p.isActive = true AND f.isActive = true");
	}
}
