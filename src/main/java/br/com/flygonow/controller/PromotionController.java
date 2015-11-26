package br.com.flygonow.controller;

import br.com.flygonow.dao.CategoryDao;
import br.com.flygonow.dao.FoodDao;
import br.com.flygonow.dao.PromotionDao;
import br.com.flygonow.dao.PromotionMediaDao;
import br.com.flygonow.entities.Category;
import br.com.flygonow.entities.Food;
import br.com.flygonow.entities.Promotion;
import br.com.flygonow.entities.PromotionMedia;
import br.com.flygonow.enums.FetchTypeEnum;
import br.com.flygonow.enums.MediaTypeEnum;
import br.com.flygonow.service.PromotionService;
import br.com.flygonow.util.JSONView;
import br.com.flygonow.util.MediaUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/promotion")
public class PromotionController implements MessageSourceAware{

	private static final Logger LOGGER = Logger.getLogger(PromotionController.class);
	
	private MessageSource messageSource;
	
	@Autowired
	private PromotionService promotionService;
	
	@Autowired
	private PromotionDao promotionDAO;
	
	@Autowired
	private PromotionMediaDao promotionMediaDAO;
	
	@Autowired
	private FoodDao foodDAO;
	
	@Autowired
	private CategoryDao categoryDAO;
	
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@RequestMapping("/listAll")
	public @ResponseBody String listAll(
			@RequestParam(required = false) String strSearch,
			Locale locale
		){
		try{
			Collection<Promotion> promo = null;
			if(strSearch != null && !"".equals(strSearch)){
				promo = promotionDAO.listAllByNameWithItems(strSearch);
			}else{
				promo = promotionDAO.getAllWithItems();
			}
			return JSONView.fromPromotion(promo);
		}catch(Exception e){
			String message = messageSource.getMessage("error.list.items", null, locale);
			LOGGER.error(message, e);
			return JSONView.getJsonSuccess(false, message);
		}
	}
	
	@RequestMapping("/delete")
	public @ResponseBody String delete(
			@RequestParam String ids,
			Locale locale
	){
		try{
			List<Long> toIds = new ArrayList<Long>();
			for (String id : Arrays.asList(ids.split(","))) {
				if (!"".equals(id))
					toIds.add(Long.parseLong(id));
			}
			for (Long id : toIds) {
				promotionService.delete(id);
			}
			return JSONView.getJsonSuccess(true, null);
		}catch(Exception e){
			String message = messageSource.getMessage("error.delete.items", null, locale);
			LOGGER.error(message, e);
			return JSONView.getJsonSuccess(false, message);
		}
	}
	
	@RequestMapping("/removeMedia")
	public @ResponseBody String removeMedia(
			@RequestParam Long id,
			@RequestParam String type,
			Locale locale
	){
		try{
			promotionService.removeMedia(id, MediaTypeEnum.fromName(type));
			return JSONView.getJsonSuccess(true, null);
		}catch(Exception e){
			String message = messageSource.getMessage("error.delete.media", null, locale);
			LOGGER.error(message, e);
			return JSONView.getJsonSuccess(false, message);
		}
	}
	
	@RequestMapping("/saveUpdate")
	public @ResponseBody String saveUpdate(
		@RequestParam(required=false) Long id,
		@RequestParam String name,
		@RequestParam Double value,
		@RequestParam(required=false) MultipartFile photo,
		@RequestParam(required=false) MultipartFile video,
		@RequestParam String description,
		@RequestParam String inicialDate,
		@RequestParam String finalDate,
		@RequestParam Long category,
		@RequestParam String items,
		@RequestParam(required=false) String active,
		Locale locale
	){
		if(!MediaUtils.validateImageFormat(photo)){
			String message = messageSource.getMessage("error.format.imagefile", null, locale);
			LOGGER.error(message);
			return JSONView.getJsonSuccess(false, message);
		}
		
		if(!MediaUtils.validateVideoFormat(video)){
			String message = messageSource.getMessage("error.format.videofile", null, locale);
			LOGGER.error(message);
			return JSONView.getJsonSuccess(false, message);
		}
		DateFormat fm = new SimpleDateFormat("dd/MM/yyyy");
		Date iniDate;
		Date endDate;
		try {
			iniDate = fm.parse(inicialDate);
			endDate = fm.parse(finalDate);
		} catch (ParseException e1) {
			String message = messageSource.getMessage("error.parse.date", null, locale);
			LOGGER.error(message, e1);
			return JSONView.getJsonSuccess(false, message);
		}
		Set<Food> foods = new HashSet<Food>();
		if(items != null){
			for (String foodId : Arrays.asList(items.split(","))) {
				if (!"".equals(foodId)){
					Food food = foodDAO.findById(Long.parseLong(foodId), FetchTypeEnum.LEFT.getName(), "accompaniments");
					foods.add(food);
				}	
			}
		}	
		boolean isActive = false;
		if("on".equals(active))
			isActive = true;
		Category cat = new Category(category, null, null, null);
		Promotion promotion = null;
		String videoName = null;
		String photoName = null;
		Collection<PromotionMedia> promoMedias = new ArrayList<PromotionMedia>();
		byte[] bPhoto = null;
		byte[] bVideo = null;
		try {
			if(photo != null && photo.getBytes().length > 0){
				bPhoto = photo.getBytes();
				photoName = Long.toString(new Date().getTime());
			}
			if(video != null && video.getBytes().length > 0){
				bVideo = video.getBytes();
				videoName = Long.toString(new Date().getTime());
			}
			promotion = new Promotion(id, name, value, photoName, videoName, description, iniDate, endDate, isActive);
			promotion.setCategory(cat);
		}catch(Exception e1){
			e1.printStackTrace();
		}
		if(promotion.getId() == null){
			try{
				Promotion saved = promotionDAO.save(promotion);
				boolean hasRelationChange = false;
				if(!foods.isEmpty()){
					saved.setFoods(foods);
					hasRelationChange = true;
				}
				if(photoName != null || videoName != null){
					hasRelationChange = true;
					if(photoName != null){
						PromotionMedia promoMedia = new PromotionMedia();
						promoMedia.setMedia(bPhoto);
						promoMedia.setPromotion(saved);
						promoMedia.setType(MediaTypeEnum.PHOTO.getId());
						promoMedias.add(promoMedia);
					}
					if(videoName != null){
						PromotionMedia promoMedia = new PromotionMedia();
						promoMedia.setMedia(bVideo);
						promoMedia.setPromotion(saved);
						promoMedia.setType(MediaTypeEnum.VIDEO.getId());
						promoMedias.add(promoMedia);
					}
					saved.setPromotionMedia(promoMedias);
				}
				if(hasRelationChange){
					promotionDAO.update(saved);
				}
			}catch(Exception e){
				String message = messageSource.getMessage("error.save.item", null, locale);
				LOGGER.error(message, e);
				return JSONView.getJsonSuccess(false, message);
			}
		}else{
			try{
				if(!foods.isEmpty()){
					promotion.setFoods(foods);
				}
				if(bPhoto != null){
					PromotionMedia promoMediaPhoto = new PromotionMedia();
					promoMediaPhoto.setMedia(bPhoto);
					promoMediaPhoto.setPromotion(promotion);
					promoMediaPhoto.setType(MediaTypeEnum.PHOTO.getId());
					promoMedias.add(promoMediaPhoto);
				}else{
					photoName = promotionDAO.getPhotoNameByPromo(id);
				}
				promotion.setPhotoName(photoName);
				if(bVideo != null){
					PromotionMedia promoMediaVideo = new PromotionMedia();
					promoMediaVideo.setMedia(bVideo);
					promoMediaVideo.setPromotion(promotion);
					promoMediaVideo.setType(MediaTypeEnum.VIDEO.getId());
					promoMedias.add(promoMediaVideo);
				}else{
					videoName = promotionDAO.getVideoNameByPromo(id);
				}
				promotion.setVideoName(videoName);					
				if(!promoMedias.isEmpty()){
					promotion.setPromotionMedia(promoMedias);
				}
				promotionDAO.update(promotion);
			}catch(Exception e){
				String message = messageSource.getMessage("error.update.item", null, locale);
				LOGGER.error(message, e);
				return JSONView.getJsonSuccess(false, message);
			}
		}
		return JSONView.getJsonSuccess(true, null);
	}
}
