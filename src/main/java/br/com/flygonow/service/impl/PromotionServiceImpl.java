package br.com.flygonow.service.impl;

import br.com.flygonow.dao.*;
import br.com.flygonow.entities.*;
import br.com.flygonow.enums.MediaTypeEnum;
import br.com.flygonow.service.PromotionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class PromotionServiceImpl implements PromotionService {

	private static Logger LOGGER = Logger.getLogger(PromotionServiceImpl.class);
	
	@Autowired
	private PromotionDao promotionDAO;
	
	@Autowired
	private AccompanimentDao accompanimentDAO;
	
	@Autowired
	private FoodDao foodDAO;
	
	@Autowired
	private OrderItemDao orderItemDAO;
	
	@Autowired
	private PromotionMediaDao promotionMediaDAO;
	
	@Override
	public void delete(Long id){
		try{
			Promotion findById = promotionDAO.findById(id, null);
			deleteAssociations(findById);
			Promotion expurge = new Promotion();
			expurge.setId(id);
			promotionDAO.update(expurge);
			promotionDAO.delete(expurge);
		}catch(Exception e){
			LOGGER.error("DELETE ASSOCIATIONS ERROR ->> " + e);
		}
	}

	private void deleteAssociations(Promotion root) {
		deleteFoodAssociations(root);
		deleteAccompanimentAssociations(root);
		deleteOrderItemAssociations(root);
		deleteMediaAssociations(root);
	}

	private void deleteOrderItemAssociations(Promotion root) {
		List<OrderItem> orderItens = orderItemDAO.listByPromotionId(root.getId());
		for (OrderItem item : orderItens) {
			item.setPromotion(null);
			orderItemDAO.update(item);
		}
		root.setOrderItems(null);
	}

	private void deleteAccompanimentAssociations(Promotion root) {
		List<Accompaniment> accompaniments = accompanimentDAO.listByPromotionId(root.getId());
		for (Accompaniment accompaniment : accompaniments) {
			Collection<Promotion> promotions = promotionDAO.listByAccompanimentId(accompaniment.getId());
			Collection<Promotion> retainAll = new ArrayList<Promotion>();
			for (Promotion promo : promotions) {
				if(!promo.getId().equals(root.getId())){
					retainAll.add(promo);
				}
			}
			promotions.retainAll(retainAll);
			accompaniment.setPromotions(promotions);
			accompanimentDAO.update(accompaniment);
		}
		root.setAccompaniments(null);
	}

	private void deleteMediaAssociations(Promotion root) {
		if(root.getPhotoName() != null){
			List<PromotionMedia> medias = 
					promotionMediaDAO.listByPromotion(root.getId(), MediaTypeEnum.PHOTO.getId());
			for (PromotionMedia promoMedia : medias) {
				promoMedia.setPromotion(null);
				promotionMediaDAO.update(promoMedia);
				promotionMediaDAO.delete(promoMedia);
			}
		}
		if(root.getPhotoName() != null){
			List<PromotionMedia> medias = 
					promotionMediaDAO.listByPromotion(root.getId(), MediaTypeEnum.VIDEO.getId());
			for (PromotionMedia promoMedia : medias) {
				promoMedia.setPromotion(null);
				promotionMediaDAO.update(promoMedia);
				promotionMediaDAO.delete(promoMedia);
			}
		}
	}

	private void deleteFoodAssociations(Promotion root) {
		Collection<Food> foods = foodDAO.listByPromotionId(root.getId());
		for (Food food : foods) {
			Collection<Promotion> promotions = promotionDAO.listByFoodId(food.getId());
			Collection<Promotion> retainAll = new ArrayList<Promotion>();
			for (Promotion promo : promotions) {
				if(!promo.getId().equals(root.getId())){
					retainAll.add(promo);
				}
			}
			promotions.retainAll(retainAll);
			food.setPromotions(promotions);
			foodDAO.update(food);
		}
		root.setFoods(null);
	}
	
	@Override
	public void removeMedia(Long id, MediaTypeEnum mediaType) {
		List<PromotionMedia> findBy = 
				promotionMediaDAO.listByPromotion(id, mediaType.getId());
		for (PromotionMedia promotionMedia : findBy) {
			promotionMedia.setPromotion(null);
			promotionMediaDAO.update(promotionMedia);
		}
		Promotion findById = promotionDAO.findById(id, null);
		if(MediaTypeEnum.PHOTO.equals(mediaType)){
			findById.setPhotoName(null);
			promotionDAO.update(findById);
		}
		else if(MediaTypeEnum.VIDEO.equals(mediaType)){
			findById.setVideoName(null);
			promotionDAO.update(findById);
		}
	}
}