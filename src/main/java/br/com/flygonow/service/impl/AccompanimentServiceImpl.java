package br.com.flygonow.service.impl;

import br.com.flygonow.dao.*;
import br.com.flygonow.entities.*;
import br.com.flygonow.enums.MediaTypeEnum;
import br.com.flygonow.service.AccompanimentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Service
public class AccompanimentServiceImpl implements AccompanimentService {

	private static Logger LOGGER = Logger.getLogger(AccompanimentServiceImpl.class);
	
	@Autowired
	private AccompanimentDao accompanimentDAO;
	
	@Autowired
	private FoodDao foodDAO;
	
	@Autowired
	private PromotionDao promotionDAO;
	
	@Autowired
	private OrderItemDao orderItemDAO;
	
	@Autowired
	private AccompanimentMediaDao accompanimentMediaDAO;
	
	@Override
	public void delete(Long id){
		try{
			Accompaniment findById = accompanimentDAO.findById(id, null);
			deleteAssociations(findById);
			Accompaniment expurge = new Accompaniment();
			expurge.setId(id);
			accompanimentDAO.update(expurge);
			accompanimentDAO.delete(expurge);
		}catch(Exception e){
			LOGGER.error("DELETE ASSOCIATIONS ERROR ->> " + e);
		}
	}

	private void deleteAssociations(Accompaniment root) {
		deleteFoodAssociations(root);
		deletePromotionAssociations(root);
		deleteOrderItemAssociations(root);
		deleteMediaAssociations(root);
	}

	private void deleteOrderItemAssociations(Accompaniment root) {
		List<OrderItem> orderItens = orderItemDAO.listByAccompanimentId(root.getId());
		for (OrderItem item : orderItens) {
			item.setAccompaniments(null);
			orderItemDAO.update(item);
		}
		root.setOrderItems(null);
	}

	private void deletePromotionAssociations(Accompaniment root) {
		List<Promotion> promotions = promotionDAO.listByAccompanimentId(root.getId());
		for (Promotion promotion : promotions) {
			promotion.setAccompaniments(null);
			promotionDAO.update(promotion);
		}
		root.setPromotions(null);
	}

	private void deleteMediaAssociations(Accompaniment root) {
		if(root.getPhotoName() != null){
			List<AccompanimentMedia> medias = 
					accompanimentMediaDAO.listByAccompaniment(root.getId(), MediaTypeEnum.PHOTO.getId());
			for (AccompanimentMedia accompanimentMedia : medias) {
				accompanimentMedia.setAccompaniment(null);
				accompanimentMediaDAO.update(accompanimentMedia);
				accompanimentMediaDAO.delete(accompanimentMedia);
			}
		}
		if(root.getVideoName() != null){
			List<AccompanimentMedia> medias = 
					accompanimentMediaDAO.listByAccompaniment(root.getId(), MediaTypeEnum.VIDEO.getId());
			for (AccompanimentMedia accompanimentMedia : medias) {
				accompanimentMedia.setAccompaniment(null);
				accompanimentMediaDAO.update(accompanimentMedia);
				accompanimentMediaDAO.delete(accompanimentMedia);
			}
		}
	}

	private void deleteFoodAssociations(Accompaniment root) {
		List<Food> foods = foodDAO.listByAccompanimentId(root.getId());
		for (Food food : foods) {
			Collection<Accompaniment> accompaniments = accompanimentDAO.listByFoodId(food.getId());
			Collection<Accompaniment> retainAll = new ArrayList<Accompaniment>();
			for (Accompaniment accompaniment : accompaniments) {
				if(!accompaniment.getId().equals(root.getId())){
					retainAll.add(accompaniment);
				}
			}
			accompaniments.retainAll(retainAll);
			food.setAccompaniments(new HashSet<Accompaniment>(accompaniments));
			foodDAO.update(food);
		}
		root.setFoods(null);
	}
	
	@Override
	public void removeMedia(Long id, MediaTypeEnum mediaType) {
		List<AccompanimentMedia> findBy = 
				accompanimentMediaDAO.listByAccompaniment(id, mediaType.getId());
		for (AccompanimentMedia accompanimentMedia : findBy) {
			accompanimentMedia.setAccompaniment(null);
			accompanimentMediaDAO.update(accompanimentMedia);
		}
		Accompaniment findById = accompanimentDAO.findById(id, null);
		if(MediaTypeEnum.PHOTO.equals(mediaType)){
			findById.setPhotoName(null);
			accompanimentDAO.update(findById);
		}
		else if(MediaTypeEnum.VIDEO.equals(mediaType)){
			findById.setVideoName(null);
			accompanimentDAO.update(findById);
		}
	}
	
}
