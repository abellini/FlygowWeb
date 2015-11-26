package br.com.flygonow.dao.impl;

import br.com.flygonow.dao.FoodMediaDao;
import br.com.flygonow.entities.Food;
import br.com.flygonow.entities.FoodMedia;
import br.com.flygonow.enums.MediaTypeEnum;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FoodMediaDaoImp extends GenericDaoImp<FoodMedia, Long>
		implements FoodMediaDao {
	
	
	@Override
	public byte[] getPhoto(Long id) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<FoodMedia> criteria = criteriaBuilder.createQuery(FoodMedia.class);
		Root<FoodMedia> root = criteria.from( FoodMedia.class );
		criteria.multiselect( root.get("media"));
		Join<FoodMedia, Food> join = root.join("food");
		Predicate predicate = criteriaBuilder.disjunction();
	    predicate = criteriaBuilder.or(predicate, criteriaBuilder.equal(join.get( "id" ), id) );
	    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get( "type" ), MediaTypeEnum.PHOTO.getId()) );
		criteria.where(predicate);
		try{
			List<FoodMedia> list = getEntityManager().createQuery( criteria ).getResultList();
			return list.get(list.size()-1).getMedia();
		}catch(Exception e){
			return null;
		}
	}
	
	@Override
	public byte[] getVideo(Long id) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<FoodMedia> criteria = criteriaBuilder.createQuery(FoodMedia.class);
		Root<FoodMedia> root = criteria.from( FoodMedia.class );
		criteria.multiselect( root.get("media"));
		Join<FoodMedia, Food> join = root.join("food");
		Predicate predicate = criteriaBuilder.disjunction();
	    predicate = criteriaBuilder.or(predicate, criteriaBuilder.equal(join.get( "id" ), id) );
	    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get( "type" ), MediaTypeEnum.VIDEO.getId()) );
		criteria.where(predicate);
		try{
			List<FoodMedia> list = getEntityManager().createQuery( criteria ).getResultList();
			return list.get(list.size()-1).getMedia();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public FoodMedia findByFood(Long id, Byte mediaType) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("type", mediaType);
		List<FoodMedia> list = this.listByParams("SELECT pm from FoodMedia pm JOIN FETCH pm.food " +
				"WHERE pm.food.id = :id and pm.type = :type", params);
		return list.get(list.size()-1);
	}

	@Override
	public List<FoodMedia> listByFood(Long id, Byte mediaType) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("type", mediaType);
		List<FoodMedia> list = this.listByParams("SELECT pm from FoodMedia pm JOIN FETCH pm.food " +
				"WHERE pm.food.id = :id and pm.type = :type", params);
		return list;
	}

}
