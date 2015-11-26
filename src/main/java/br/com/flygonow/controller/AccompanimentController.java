package br.com.flygonow.controller;

import br.com.flygonow.dao.AccompanimentDao;
import br.com.flygonow.dao.CategoryDao;
import br.com.flygonow.dao.OperationalAreaDao;
import br.com.flygonow.entities.Accompaniment;
import br.com.flygonow.entities.AccompanimentMedia;
import br.com.flygonow.entities.Category;
import br.com.flygonow.entities.OperationalArea;
import br.com.flygonow.enums.MediaTypeEnum;
import br.com.flygonow.service.AccompanimentService;
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
@RequestMapping("/accompaniment")
public class AccompanimentController implements MessageSourceAware{

	private static final Logger LOGGER = Logger.getLogger(AccompanimentController.class);
	
	private MessageSource messageSource;
	
	@Autowired
	private AccompanimentService accompanimentService;
	
	@Autowired
	private AccompanimentDao accompanimentDAO;
	
	@Autowired
	private CategoryDao categoryDAO;
	
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
			List<Accompaniment> acc = null;
			if(strSearch != null && !"".equals(strSearch)){
				acc = accompanimentDAO.listByName(strSearch, null);
			} else{
				acc = accompanimentDAO.getAll(null);
			}
			return JSONView.fromAccompaniment(acc);
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
				accompanimentService.delete(id);
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
			accompanimentService.removeMedia(id, MediaTypeEnum.fromName(type));
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
		@RequestParam(required=false) String active,
		@RequestParam Long category,
		@RequestParam Long operationalArea,
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
		boolean isActive = false;
		if("on".equals(active))
			isActive = true;
		Category cat = new Category(category, null, null, null);
		OperationalArea opArea = new OperationalArea(operationalArea, null, null);
		Accompaniment accompaniment = null;
		String videoName = null;
		String photoName = null;
		Collection<AccompanimentMedia> accompanimentMedias = new ArrayList<AccompanimentMedia>();
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
			accompaniment = new Accompaniment(id, name, value, photoName, videoName, description, isActive);
			accompaniment.setCategory(cat);
			accompaniment.setOperationalArea(opArea);
		}catch(Exception e1){
			e1.printStackTrace();
		}
		if(accompaniment.getId() == null){
			try{
				Accompaniment saved = accompanimentDAO.save(accompaniment);
				boolean hasRelationChange = false;
				if(photoName != null || videoName != null){
					hasRelationChange = true;
					if(photoName != null){
						AccompanimentMedia accMedia = new AccompanimentMedia();
						accMedia.setMedia(bPhoto);
						accMedia.setAccompaniment(saved);
						accMedia.setType(MediaTypeEnum.PHOTO.getId());
						accompanimentMedias.add(accMedia);
					}
					if(videoName != null){
						AccompanimentMedia accMedia = new AccompanimentMedia();
						accMedia.setMedia(bVideo);
						accMedia.setAccompaniment(saved);
						accMedia.setType(MediaTypeEnum.VIDEO.getId());
						accompanimentMedias.add(accMedia);
					}
					saved.setAccompanimentMedia(accompanimentMedias);
				}
				if(hasRelationChange){
					accompanimentDAO.update(saved);
				}
			}catch(Exception e){
				String message = messageSource.getMessage("error.save.item", null, locale);
				LOGGER.error(message, e);
				return JSONView.getJsonSuccess(false, message);
			}
		}else{
			try{
				if(bPhoto != null){
					AccompanimentMedia accMedia = new AccompanimentMedia();
					accMedia.setMedia(bPhoto);
					accMedia.setAccompaniment(accompaniment);
					accMedia.setType(MediaTypeEnum.PHOTO.getId());
					accompanimentMedias.add(accMedia);
				}else{
					photoName = accompanimentDAO.getPhotoNameByAcc(id);
				}
				accompaniment.setPhotoName(photoName);
				if(bVideo != null){
					AccompanimentMedia accMedia = new AccompanimentMedia();
					accMedia.setMedia(bVideo);
					accMedia.setAccompaniment(accompaniment);
					accMedia.setType(MediaTypeEnum.VIDEO.getId());
					accompanimentMedias.add(accMedia);
				}else{
					videoName = accompanimentDAO.getVideoNameByAcc(id);
				}
				accompaniment.setVideoName(videoName);					
				if(!accompanimentMedias.isEmpty()){
					accompaniment.setAccompanimentMedia(accompanimentMedias);
				}
				accompanimentDAO.update(accompaniment);
			}catch(Exception e){
				String message = messageSource.getMessage("error.update.item", null, locale);
				LOGGER.error(message, e);
				return JSONView.getJsonSuccess(false, message);
			}
		}
		return JSONView.getJsonSuccess(true, null);
	}
}
