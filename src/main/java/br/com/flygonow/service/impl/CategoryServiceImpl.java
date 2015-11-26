package br.com.flygonow.service.impl;

import br.com.flygonow.dao.*;
import br.com.flygonow.entities.*;
import br.com.flygonow.enums.MediaTypeEnum;
import br.com.flygonow.service.CategoryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

	private static Logger LOGGER = Logger.getLogger(CategoryServiceImpl.class);
	
	@Autowired
	private CategoryDao categoryDAO;
	
	@Autowired
	private CategoryMediaDao categoryMediaDAO;
	
	@Autowired
	private PromotionDao promotionDAO;
	
	@Autowired
	private AccompanimentDao accompanimentDAO;

	@Autowired
	private FoodDao foodDAO;
	
	@Override
	public void delete(Long id){
		try{
			Category findById = categoryDAO.findById(id, null);
			deleteAssociations(findById);
			Category expurge = new Category();
			expurge.setId(id);
			categoryDAO.update(expurge);
			categoryDAO.delete(expurge);
		}catch(Exception e){
			LOGGER.error("DELETE ASSOCIATIONS ERROR ->> " + e);
		}
	}

	private void deleteAssociations(Category root) {
		deletePromotionAssociations(root);
		deleteAccompanimentAssociations(root);
		deleteFoodAssociations(root);
		deleteMediaAssociations(root);
	}

	private void deletePromotionAssociations(Category root) {
		List<Promotion> promotions = promotionDAO.listByCategoryId(root.getId());
		for (Promotion promotion : promotions) {
			promotion.setCategory(null);
			promotionDAO.update(promotion);
		}
		root.setPromotion(null);
	}

	private void deleteAccompanimentAssociations(Category root) {
		List<Accompaniment> accompaniments = accompanimentDAO.listByCategoryId(root.getId());
		for (Accompaniment accompaniment : accompaniments) {
			accompaniment.setCategory(null);
			accompanimentDAO.update(accompaniment);
		}
		root.setAccompaniment(null);
	}

	private void deleteMediaAssociations(Category root) {
		if(root.getPhotoName() != null){
			List<CategoryMedia> medias = 
					categoryMediaDAO.listByCategoryId(root.getId(), MediaTypeEnum.PHOTO.getId());
			for (CategoryMedia categoryMedia : medias) {
				categoryMedia.setCategory(null);
				categoryMediaDAO.update(categoryMedia);
				categoryMediaDAO.delete(categoryMedia);
			}
		}
	}

	private void deleteFoodAssociations(Category root) {
		List<Food> foods = foodDAO.listByCategoryId(root.getId());
		for (Food food : foods) {
			food.setCategory(null);
			foodDAO.update(food);
		}
		root.setFoods(null);
	}
	
	@Override
	public void removeMedia(Long id, MediaTypeEnum mediaType) {
		List<CategoryMedia> findBy = 
				categoryMediaDAO.listByCategoryId(id, mediaType.getId());
		for (CategoryMedia catMedia : findBy) {
			catMedia.setCategory(null);
			categoryMediaDAO.update(catMedia);
		}
		Category findById = categoryDAO.findById(id, null);
		if(MediaTypeEnum.PHOTO.equals(mediaType)){
			findById.setPhotoName(null);
			categoryDAO.update(findById);
		}
	}
}
