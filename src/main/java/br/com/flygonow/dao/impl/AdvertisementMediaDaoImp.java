package br.com.flygonow.dao.impl;

import br.com.flygonow.dao.AdvertisementMediaDao;
import br.com.flygonow.entities.Advertisement;
import br.com.flygonow.entities.AdvertisementMedia;
import br.com.flygonow.enums.MediaTypeEnum;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AdvertisementMediaDaoImp extends GenericDaoImp<AdvertisementMedia, Long>
		implements AdvertisementMediaDao {
	
	
	@Override
	public byte[] getPhoto(Long id) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<AdvertisementMedia> criteria = criteriaBuilder.createQuery(AdvertisementMedia.class);
		Root<AdvertisementMedia> root = criteria.from( AdvertisementMedia.class );
		criteria.multiselect( root.get("media"));
		Join<AdvertisementMedia, Advertisement> join = root.join("advertisement");
		Predicate predicate = criteriaBuilder.disjunction();
	    predicate = criteriaBuilder.or(predicate, criteriaBuilder.equal(join.get( "id" ), id) );
	    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get( "type" ), MediaTypeEnum.PHOTO.getId()) );
		criteria.where(predicate);
		try{
			List<AdvertisementMedia> list = getEntityManager().createQuery( criteria ).getResultList();
			return list.get(list.size()-1).getMedia();
		}catch(Exception e){
			return null;
		}
	}
	
	@Override
	public byte[] getVideo(Long id) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<AdvertisementMedia> criteria = criteriaBuilder.createQuery(AdvertisementMedia.class);
		Root<AdvertisementMedia> root = criteria.from( AdvertisementMedia.class );
		criteria.multiselect( root.get("media"));
		Join<AdvertisementMedia, Advertisement> join = root.join("advertisement");
		Predicate predicate = criteriaBuilder.disjunction();
	    predicate = criteriaBuilder.or(predicate, criteriaBuilder.equal(join.get( "id" ), id) );
	    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get( "type" ), MediaTypeEnum.VIDEO.getId()) );
		criteria.where(predicate);
		try{
			List<AdvertisementMedia> list = getEntityManager().createQuery( criteria ).getResultList();
			return list.get(list.size()-1).getMedia();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public AdvertisementMedia findByAdvertisement(Long id, Byte mediaType) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("type", mediaType);
		List<AdvertisementMedia> list = this.listByParams("SELECT pm from AdvertisementMedia pm JOIN FETCH pm.advertisement " +
				"WHERE pm.advertisement.id = :id and pm.type = :type", params);
		return list.get(list.size()-1);
	}

	@Override
	public List<AdvertisementMedia> listByAdvertisement(Long id, Byte mediaType) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("type", mediaType);
		List<AdvertisementMedia> list = this.listByParams("SELECT pm from AdvertisementMedia pm JOIN FETCH pm.advertisement " +
				"WHERE pm.advertisement.id = :id and pm.type = :type", params);
		return list;
	}

}
