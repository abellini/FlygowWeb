package br.com.flygonow.dao.impl;

import br.com.flygonow.dao.AccompanimentDao;
import br.com.flygonow.entities.Accompaniment;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AcompanimentDaoImp extends GenericDaoImp<Accompaniment, Long>
		implements AccompanimentDao {

	@Override
	public Collection<Accompaniment> findByFoodId(Long foodId) {
		Collection<Accompaniment> accompaniments = null;
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("foodId", foodId);
		try{
			accompaniments = this.listByParams(
				"SELECT acc FROM Accompaniment acc " +
				"JOIN acc.foods f " +
				"WHERE f.id = :foodId", 
			params);
		}catch(Exception e){
			System.out.println("Error ->> " + e);
		}
		return accompaniments;
	}
	
	@Override
	public String getVideoNameByAcc(Long id) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Accompaniment> criteria = criteriaBuilder.createQuery(Accompaniment.class);
		Root<Accompaniment> root = criteria.from( Accompaniment.class );
		criteria.multiselect( root.get("videoName"));
		criteria.where( criteriaBuilder.equal( root.get( "id" ), id ) );
		try{
			Accompaniment result = getEntityManager().createQuery( criteria ).getSingleResult();
			return result.getVideoName();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public String getPhotoNameByAcc(Long id) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Accompaniment> criteria = criteriaBuilder.createQuery(Accompaniment.class);
		Root<Accompaniment> root = criteria.from( Accompaniment.class );
		criteria.multiselect( root.get("photoName"));
		criteria.where( criteriaBuilder.equal( root.get( "id" ), id ) );
		try{
			Accompaniment result = getEntityManager().createQuery( criteria ).getSingleResult();
			return result.getPhotoName();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<Accompaniment> listByFoodId(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return this.listByParams("SELECT a FROM Accompaniment a LEFT JOIN a.foods f WHERE f.id = :id", params);
	}
	
	@Override
	public List<Accompaniment> listByCategoryId(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return this.listByParams("SELECT a FROM Accompaniment a LEFT JOIN a.category c WHERE c.id = :id", params);
	}

	@Override
	public List<Accompaniment> listByOperationalAreaId(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return this.listByParams("SELECT a FROM Accompaniment a LEFT JOIN a.operationalArea oa WHERE oa.id = :id", params);
	}

	@Override
	public List<Accompaniment> listByPromotionId(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return this.listByParams("SELECT a FROM Accompaniment a LEFT JOIN a.promotions p WHERE p.id = :id", params);
	}

	@Override
	public List<Accompaniment> listByAllFoods() {
		return this.listByQuery("SELECT a FROM Accompaniment a INNER JOIN FETCH a.foods f WHERE a.isActive = true AND f.isActive = true");
	}
	
	@Override
	public List<Accompaniment> listByAllCategories() {
		return this.listByQuery(
			"SELECT a " +
			"FROM Accompaniment a " +
			"WHERE a NOT IN ( " +
				"SELECT a from Accompaniment a " +
				"INNER JOIN a.foods f " +
			" ) AND a NOT IN ( " +
				"SELECT a from Accompaniment a " +
				"INNER JOIN a.promotions p " +
			" ) AND a.category != null AND a.isActive = true"
		);
	}

	@Override
	public List<Accompaniment> listByOrderItemId(Long orderItemId) {
		List<Accompaniment> accompaniments = null;
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("orderItemId", orderItemId);
		try{
			accompaniments = this.listByParams(
				"SELECT acc FROM Accompaniment acc " +
				"JOIN acc.orderItems oi " +
				"WHERE oi.id = :orderItemId", 
			params);
		}catch(Exception e){
			System.out.println("Error ->> " + e);
		}
		return accompaniments;
	}

}
