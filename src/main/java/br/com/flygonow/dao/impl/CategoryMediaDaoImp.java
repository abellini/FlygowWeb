package br.com.flygonow.dao.impl;

import br.com.flygonow.dao.CategoryMediaDao;
import br.com.flygonow.entities.Category;
import br.com.flygonow.entities.CategoryMedia;
import br.com.flygonow.enums.MediaTypeEnum;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CategoryMediaDaoImp extends GenericDaoImp<CategoryMedia, Long>
		implements CategoryMediaDao {
	
	
	@Override
	public byte[] getPhoto(Long promoId) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<CategoryMedia> criteria = criteriaBuilder.createQuery(CategoryMedia.class);
		Root<CategoryMedia> root = criteria.from( CategoryMedia.class );
		criteria.multiselect( root.get("media"));
		Join<CategoryMedia, Category> join = root.join("category");
		Predicate predicate = criteriaBuilder.disjunction();
	    predicate = criteriaBuilder.or(predicate, criteriaBuilder.equal(join.get( "id" ), promoId) );
	    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get( "type" ), MediaTypeEnum.PHOTO.getId()) );
		criteria.where(predicate);
		try{
			List<CategoryMedia> list = getEntityManager().createQuery( criteria ).getResultList();
			return list.get(list.size()-1).getMedia();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public CategoryMedia findByCategory(Long id, Byte mediaType) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("type", mediaType);
		List<CategoryMedia> list = this.listByParams("SELECT pm from CategoryMedia pm JOIN FETCH pm.category " +
				"WHERE pm.category.id = :id and pm.type = :type", params);
		return list.get(list.size()-1);
	}

	@Override
	public List<CategoryMedia> listByCategoryId(Long id, Byte mediaType) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("type", mediaType);
		List<CategoryMedia> list = this.listByParams("SELECT pm from CategoryMedia pm JOIN FETCH pm.category " +
				"WHERE pm.category.id = :id and pm.type = :type", params);
		return list;
	}

}
