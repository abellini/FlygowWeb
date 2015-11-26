package br.com.flygonow.dao.impl;

import br.com.flygonow.dao.PromotionMediaDao;
import br.com.flygonow.entities.Promotion;
import br.com.flygonow.entities.PromotionMedia;
import br.com.flygonow.enums.MediaTypeEnum;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PromotionMediaDaoImp extends GenericDaoImp<PromotionMedia, Long>
		implements PromotionMediaDao {
	
	@Override
	public byte[] getVideo(Long promoId) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<PromotionMedia> criteria = criteriaBuilder.createQuery(PromotionMedia.class);
		Root<PromotionMedia> promotionRoot = criteria.from( PromotionMedia.class );
		criteria.multiselect( promotionRoot.get("media"));
		Join<PromotionMedia, Promotion> promotion = promotionRoot.join("promotion");
		Predicate predicate = criteriaBuilder.disjunction();
	    predicate = criteriaBuilder.or(predicate, criteriaBuilder.equal(promotion.get( "id" ), promoId) );
	    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(promotionRoot.get( "type" ), MediaTypeEnum.VIDEO.getId()) );
		criteria.where(predicate);
		try{
			List<PromotionMedia> promos = getEntityManager().createQuery( criteria ).getResultList();
			return promos.get(promos.size()-1).getMedia();
		}catch(Exception e){
			return null;
		}
	}
	
	@Override
	public byte[] getPhoto(Long promoId) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<PromotionMedia> criteria = criteriaBuilder.createQuery(PromotionMedia.class);
		Root<PromotionMedia> promotionRoot = criteria.from( PromotionMedia.class );
		criteria.multiselect( promotionRoot.get("media"));
	    Join<PromotionMedia, Promotion> promotion = promotionRoot.join("promotion");
	    Predicate predicate = criteriaBuilder.disjunction();
	    predicate = criteriaBuilder.or(predicate, criteriaBuilder.equal(promotion.get( "id" ), promoId) );
	    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(promotionRoot.get( "type" ), MediaTypeEnum.PHOTO.getId()) );
		criteria.where(predicate);
		try{
			List<PromotionMedia> promos = getEntityManager().createQuery( criteria ).getResultList();
			return promos.get(promos.size()-1).getMedia();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public PromotionMedia findByPromotion(Long promoId, Byte mediaType) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("promoId", promoId);
		params.put("type", mediaType);
		List<PromotionMedia> promos = this.listByParams("SELECT pm from PromotionMedia pm JOIN FETCH pm.promotion " +
				"WHERE pm.promotion.id = :promoId and pm.type = :type", params);
		if(promos != null && !promos.isEmpty()){
			return promos.get(promos.size()-1);
		}else{
			return null;
		}
	}

	@Override
	public List<PromotionMedia> listByPromotion(Long id, Byte mediaType) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("promoId", id);
		params.put("type", mediaType);
		List<PromotionMedia> promos = this.listByParams("SELECT pm from PromotionMedia pm JOIN FETCH pm.promotion " +
				"WHERE pm.promotion.id = :promoId and pm.type = :type", params);
		return promos;
	}

}
