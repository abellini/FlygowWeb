package br.com.flygonow.service.impl;

import br.com.flygonow.dao.*;
import br.com.flygonow.entities.*;
import br.com.flygonow.enums.MediaTypeEnum;
import br.com.flygonow.service.FoodService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Service
public class FoodServiceImpl implements FoodService {

	private static Logger LOGGER = Logger.getLogger(FoodServiceImpl.class);
	
	@Autowired
	private AccompanimentDao accompanimentDAO;
	
	@Autowired
	private FoodDao foodDAO;
	
	@Autowired
	private FoodMediaDao foodMediaDAO;
	
	@Autowired
	private PromotionDao promotionDAO;
	
	@Autowired
	private OrderItemDao orderItemDAO;
	
	@Override
	public void delete(Long id){
		try{
			Food findById = foodDAO.findById(id, null);
			deleteAssociations(findById);
			Food expurge = new Food();
			expurge.setId(id);
			foodDAO.update(expurge);
			foodDAO.delete(expurge);
		}catch(Exception e){
			LOGGER.error("DELETE ASSOCIATIONS ERROR ->> " + e);
		}
	}

	private void deleteAssociations(Food root) {
		deleteAccompanimentAssociations(root);
		deletePromotionAssociations(root);
		deleteOrderItemAssociations(root);
		deleteMediaAssociations(root);
	}

	private void deleteOrderItemAssociations(Food root) {
		List<OrderItem> orderItens = orderItemDAO.listByFoodId(root.getId());
		for (OrderItem item : orderItens) {
			item.setFood(null);
			orderItemDAO.update(item);
		}
		root.setOrderItems(null);
	}

	private void deletePromotionAssociations(Food root) {
		List<Promotion> promotions = promotionDAO.listByFoodId(root.getId());
		for (Promotion promotion : promotions) {
			Collection<Food> foods = foodDAO.listByPromotionId(promotion.getId());
			Collection<Food> retainAll = new ArrayList<Food>();
			for (Food food : foods) {
				if(!food.getId().equals(root.getId())){
					retainAll.add(food);
				}
			}
			foods.retainAll(retainAll);
			promotion.setFoods(new HashSet<Food>(foods));
			promotionDAO.update(promotion);
		}
		root.setPromotions(null);
	}

	private void deleteMediaAssociations(Food root) {
		if(root.getPhotoName() != null){
			List<FoodMedia> medias = 
					foodMediaDAO.listByFood(root.getId(), MediaTypeEnum.PHOTO.getId());
			for (FoodMedia foodMedia : medias) {
				foodMedia.setFood(null);
				foodMediaDAO.update(foodMedia);
				foodMediaDAO.delete(foodMedia);
			}
		}
		if(root.getVideoName() != null){
			List<FoodMedia> medias = 
					foodMediaDAO.listByFood(root.getId(), MediaTypeEnum.VIDEO.getId());
			for (FoodMedia foodMedia : medias) {
				foodMedia.setFood(null);
				foodMediaDAO.update(foodMedia);
				foodMediaDAO.delete(foodMedia);
			}
		}
	}

	private void deleteAccompanimentAssociations(Food root) {
		List<Accompaniment> accs = accompanimentDAO.listByFoodId(root.getId());
		for (Accompaniment acc : accs) {
			acc.setFoods(null);
			accompanimentDAO.update(acc);
		}
		root.setAccompaniments(null);
	}
	
	@Override
	public void removeMedia(Long id, MediaTypeEnum mediaType) {
		List<FoodMedia> findBy = 
				foodMediaDAO.listByFood(id, mediaType.getId());
		for (FoodMedia foodMedia : findBy) {
			foodMedia.setFood(null);
			foodMediaDAO.update(foodMedia);
		}
		Food findById = foodDAO.findById(id, null);
		if(MediaTypeEnum.PHOTO.equals(mediaType)){
			findById.setPhotoName(null);
			foodDAO.update(findById);
		}
		else if(MediaTypeEnum.VIDEO.equals(mediaType)){
			findById.setVideoName(null);
			foodDAO.update(findById);
		}
	}
	
}
