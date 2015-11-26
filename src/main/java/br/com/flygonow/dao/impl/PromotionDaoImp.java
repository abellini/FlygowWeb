package br.com.flygonow.dao.impl;

import br.com.flygonow.dao.FoodDao;
import br.com.flygonow.dao.PromotionDao;
import br.com.flygonow.entities.Promotion;
import br.com.flygonow.enums.FetchTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.*;

@Repository
public class PromotionDaoImp extends GenericDaoImp<Promotion, Long>
		implements PromotionDao {

	@Autowired
	private FoodDao foodDAO;
	
	@Override
	public Collection<Promotion> getAllWithItems() {
		List<Promotion> promotions = null;
		try{
			promotions = this.getAll(FetchTypeEnum.LEFT.getName(), "foods");
		}catch(Exception e){
			System.out.println("Error ->> " + e);
		}
		return promotions;
	}

	@Override
	public Collection<Promotion> listAllByNameWithItems(String strSearch) {
		List<Promotion> promotions = null;
		try{
			promotions = this.listByName(strSearch, FetchTypeEnum.LEFT.getName(), "foods");
		}catch(Exception e){
			System.out.println("Error ->> " + e);
		}
		return promotions;
	}

	@Override
	public String getVideoNameByPromo(Long id) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Promotion> criteria = criteriaBuilder.createQuery(Promotion.class);
		Root<Promotion> promotionRoot = criteria.from( Promotion.class );
		criteria.multiselect( promotionRoot.get("videoName"));
		criteria.where( criteriaBuilder.equal( promotionRoot.get( "id" ), id ) );
		try{
			Promotion promo = getEntityManager().createQuery( criteria ).getSingleResult();
			return promo.getVideoName();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public String getPhotoNameByPromo(Long id) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Promotion> criteria = criteriaBuilder.createQuery(Promotion.class);
		Root<Promotion> promotionRoot = criteria.from( Promotion.class );
		criteria.multiselect( promotionRoot.get("photoName"));
		criteria.where( criteriaBuilder.equal( promotionRoot.get( "id" ), id ) );
		try{
			Promotion promo = getEntityManager().createQuery( criteria ).getSingleResult();
			return promo.getPhotoName();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<Promotion> listByAccompanimentId(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return this.listByParams("SELECT p FROM Promotion p LEFT JOIN p.accompaniments a WHERE a.id = :id", params);
	}

	@Override
	public List<Promotion> listByFoodId(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return this.listByParams("SELECT p FROM Promotion p LEFT JOIN p.foods f WHERE f.id = :id", params);
	}

	@Override
	public List<Promotion> listByCategoryId(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return this.listByParams("SELECT p FROM Promotion p LEFT JOIN p.category c WHERE c.id = :id", params);
	}

	@Override
	public List<Promotion> listByActiveState(boolean isActive) {
		return listAllByActiveStateAndVigency(isActive);
	}
	
	private List<Promotion> listAllByActiveStateAndVigency(boolean isActive){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("isActive", isActive);
		params.put("today", new java.sql.Date(new Date().getTime()));
		return this.listByParams("SELECT p FROM Promotion p WHERE p.isActive = :isActive AND p.inicialDate <= :today AND p.finalDate >= :today", params);
	}

}
