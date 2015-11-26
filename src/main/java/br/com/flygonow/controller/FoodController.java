package br.com.flygonow.controller;

import br.com.flygonow.dao.AccompanimentDao;
import br.com.flygonow.dao.CategoryDao;
import br.com.flygonow.dao.FoodDao;
import br.com.flygonow.dao.OperationalAreaDao;
import br.com.flygonow.entities.*;
import br.com.flygonow.enums.MediaTypeEnum;
import br.com.flygonow.service.FoodService;
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

import java.util.*;

@Controller
@RequestMapping("/food")
public class FoodController implements MessageSourceAware{

	private static final Logger LOGGER = Logger.getLogger(FoodController.class);
	
	private MessageSource messageSource;
	
	@Autowired
	private FoodService foodService;
	
	@Autowired
	private FoodDao foodDAO;
	
	@Autowired
	private CategoryDao categoryDAO;
	
	@Autowired
	private AccompanimentDao accompanimentDAO;
	
	@Autowired
	private OperationalAreaDao operationalAreaDAO;
	
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
			List<Food> food = null;
			if(strSearch != null && !"".equals(strSearch)){
				food = foodDAO.listAllByNameWithAccompaniments(strSearch);
			}else{
				food = foodDAO.getAllWithAccompaniments();
			}
			return JSONView.fromFood(food);
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
				foodService.delete(id);
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
			foodService.removeMedia(id, MediaTypeEnum.fromName(type));
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
		@RequestParam String description,
		@RequestParam Double value,
		@RequestParam(required=false) MultipartFile photo,
		@RequestParam(required=false) MultipartFile video,
		@RequestParam Long category,
		@RequestParam Long operationalArea,
		@RequestParam(required=false) String accompaniments,
		@RequestParam(required=false) String nutritionalInfo,
		@RequestParam(required=false) Integer maxQtdAccompaniments,
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
		Set<Accompaniment> accs = new HashSet<Accompaniment>();
		if(accompaniments != null){
			for (String accId : Arrays.asList(accompaniments.split(","))) {
				if (!"".equals(accId)){
					Accompaniment acc = accompanimentDAO.findById(Long.parseLong(accId), null);
					accs.add(acc);
				}	
			}
		}	
		boolean isActive = false;
		if("on".equals(active))
			isActive = true;
		Category cat = new Category(category, null, null, null);
		OperationalArea opArea = new OperationalArea(operationalArea, null, null);
		Food food = null;
		String videoName = null;
		String photoName = null;
		Collection<FoodMedia> foodMedias = new ArrayList<FoodMedia>();
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
			food = new Food(id, name, description, value, photoName, videoName, isActive, nutritionalInfo, maxQtdAccompaniments);
			food.setCategory(cat);
			food.setOperationalArea(opArea);
		}catch(Exception e1){
			e1.printStackTrace();
		}
		if(food.getId() == null){
			try{
				Food saved = foodDAO.save(food);
				boolean hasRelationChange = false;
				if(photoName != null || videoName != null){
					hasRelationChange = true;
					if(photoName != null){
						FoodMedia foodMedia = new FoodMedia();
						foodMedia.setMedia(bPhoto);
						foodMedia.setFood(saved);
						foodMedia.setType(MediaTypeEnum.PHOTO.getId());
						foodMedias.add(foodMedia);
					}
					if(videoName != null){
						FoodMedia foodMedia = new FoodMedia();
						foodMedia.setMedia(bVideo);
						foodMedia.setFood(saved);
						foodMedia.setType(MediaTypeEnum.VIDEO.getId());
						foodMedias.add(foodMedia);
					}
					saved.setFoodMedia(foodMedias);
				}
				if(!accs.isEmpty()){
					hasRelationChange = true;
					saved.setAccompaniments(accs);
				}
				if(hasRelationChange){
					foodDAO.update(saved);
				}
			}catch(Exception e){
				String message = messageSource.getMessage("error.save.item", null, locale);
				LOGGER.error(message, e);
				return JSONView.getJsonSuccess(false, message);
			}
		}else{
			try{
				if(bPhoto != null){
					FoodMedia foodMedia = new FoodMedia();
					foodMedia.setMedia(bPhoto);
					foodMedia.setFood(food);
					foodMedia.setType(MediaTypeEnum.PHOTO.getId());
					foodMedias.add(foodMedia);
				}else{
					photoName = foodDAO.getPhotoNameByFood(id);
				}
				food.setPhotoName(photoName);
				if(bVideo != null){
					FoodMedia foodMedia = new FoodMedia();
					foodMedia.setMedia(bVideo);
					foodMedia.setFood(food);
					foodMedia.setType(MediaTypeEnum.VIDEO.getId());
					foodMedias.add(foodMedia);
				}else{
					videoName = foodDAO.getVideoNameByFood(id);
				}
				food.setVideoName(videoName);					
				if(!foodMedias.isEmpty()){
					food.setFoodMedia(foodMedias);
				}
				if(!accs.isEmpty()){
					food.setAccompaniments(accs);
				}
				foodDAO.update(food);
			}catch(Exception e){
				String message = messageSource.getMessage("error.update.item", null, locale);
				LOGGER.error(message, e);
				return JSONView.getJsonSuccess(false, message);
			}
		}
		return JSONView.getJsonSuccess(true, null);
	}
}
