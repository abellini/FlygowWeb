package br.com.flygonow.dao;

import br.com.flygonow.entities.PromotionMedia;

import java.util.List;

public interface PromotionMediaDao extends GenericDao<PromotionMedia, Long>{

	byte[] getVideo(Long promoId);

	byte[] getPhoto(Long promoId);

	PromotionMedia findByPromotion(Long promoId, Byte mediaType);

	List<PromotionMedia> listByPromotion(Long id, Byte mediaType);

}
