package br.com.flygonow.dao.impl;

import br.com.flygonow.dao.AccompanimentMediaDao;
import br.com.flygonow.entities.Accompaniment;
import br.com.flygonow.entities.AccompanimentMedia;
import br.com.flygonow.enums.MediaTypeEnum;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AccompanimentMediaDaoImp extends GenericDaoImp<AccompanimentMedia, Long>
		implements AccompanimentMediaDao {
	
	
	@Override
	public byte[] getPhoto(Long id) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<AccompanimentMedia> criteria = criteriaBuilder.createQuery(AccompanimentMedia.class);
		Root<AccompanimentMedia> root = criteria.from( AccompanimentMedia.class );
		criteria.multiselect( root.get("media"));
		Join<AccompanimentMedia, Accompaniment> join = root.join("accompaniment");
		Predicate predicate = criteriaBuilder.disjunction();
	    predicate = criteriaBuilder.or(predicate, criteriaBuilder.equal(join.get( "id" ), id) );
	    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get( "type" ), MediaTypeEnum.PHOTO.getId()) );
		criteria.where(predicate);
		try{
			List<AccompanimentMedia> list = getEntityManager().createQuery( criteria ).getResultList();
			return list.get(list.size()-1).getMedia();
		}catch(Exception e){
			return null;
		}
	}
	
	@Override
	public byte[] getVideo(Long id) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<AccompanimentMedia> criteria = criteriaBuilder.createQuery(AccompanimentMedia.class);
		Root<AccompanimentMedia> root = criteria.from( AccompanimentMedia.class );
		criteria.multiselect( root.get("media"));
		Join<AccompanimentMedia, Accompaniment> join = root.join("accompaniment");
		Predicate predicate = criteriaBuilder.disjunction();
	    predicate = criteriaBuilder.or(predicate, criteriaBuilder.equal(join.get( "id" ), id) );
	    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get( "type" ), MediaTypeEnum.VIDEO.getId()) );
		criteria.where(predicate);
		try{
			List<AccompanimentMedia> list = getEntityManager().createQuery( criteria ).getResultList();
			return list.get(list.size()-1).getMedia();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public AccompanimentMedia findByAccompaniment(Long id, Byte mediaType) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("type", mediaType);
		List<AccompanimentMedia> list = this.listByParams("SELECT pm from AccompanimentMedia pm JOIN FETCH pm.accompaniment " +
				"WHERE pm.accompaniment.id = :id and pm.type = :type", params);
		return list.get(list.size()-1);
	}
	
	@Override
	public List<AccompanimentMedia> listByAccompaniment(Long id, Byte mediaType) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("type", mediaType);
		List<AccompanimentMedia> list = this.listByParams("SELECT pm from AccompanimentMedia pm JOIN FETCH pm.accompaniment " +
				"WHERE pm.accompaniment.id = :id and pm.type = :type", params);
		return list;
	}

}
