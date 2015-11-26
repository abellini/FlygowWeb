package br.com.flygonow.controller;

import br.com.flygonow.dao.AdvertisementDao;
import br.com.flygonow.entities.Advertisement;
import br.com.flygonow.entities.AdvertisementMedia;
import br.com.flygonow.enums.MediaTypeEnum;
import br.com.flygonow.service.AdvertisementService;
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

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/advertisement")
public class AdvertisementController implements MessageSourceAware{

	private static final Logger LOGGER = Logger.getLogger(AdvertisementController.class);
	
	private MessageSource messageSource;
	
	@Autowired
	private AdvertisementService advertisementService;
	
	@Autowired
	private AdvertisementDao advertisementDAO;
	
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
			List<Advertisement> advertisements = null;
			if(strSearch != null && !"".equals(strSearch)){
				advertisements = advertisementDAO.listByName(strSearch, null);
			}else{
				advertisements = advertisementDAO.getAll(null);				
			}
			return JSONView.fromAdvertisement(advertisements);
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
				advertisementService.delete(id);
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
			advertisementService.removeMedia(id, MediaTypeEnum.fromName(type));
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
		@RequestParam String inicialDate,
		@RequestParam String finalDate,
		@RequestParam(required=false) String active,
		@RequestParam(required=false) MultipartFile photo,
		@RequestParam(required=false) MultipartFile video,
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
		Advertisement adv = null;
		byte[] bPhoto = null;
		byte[] bVideo = null;
		String videoName = null;
		String photoName = null;
		Collection<AdvertisementMedia> advertisementMedias = new ArrayList<AdvertisementMedia>();
		try {
			if(photo != null && photo.getBytes().length > 0){
				bPhoto = photo.getBytes();
				photoName = Long.toString(new Date().getTime());
			}
			if(video != null && video.getBytes().length > 0){
				bVideo = video.getBytes();
				videoName = Long.toString(new Date().getTime());
			}
			adv = new Advertisement(id, name, iniDate, endDate, isActive, photoName, videoName);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if(adv.getId() == null){
			try{
				Advertisement saved = advertisementDAO.save(adv);
				boolean hasRelationChange = false;
				if(photoName != null || videoName != null){
					hasRelationChange = true;
					if(photoName != null){
						AdvertisementMedia advMedia = new AdvertisementMedia();
						advMedia.setMedia(bPhoto);
						advMedia.setAdvertisement(saved);
						advMedia.setType(MediaTypeEnum.PHOTO.getId());
						advertisementMedias.add(advMedia);
					}
					if(videoName != null){
						AdvertisementMedia advMedia = new AdvertisementMedia();
						advMedia.setMedia(bVideo);
						advMedia.setAdvertisement(saved);
						advMedia.setType(MediaTypeEnum.VIDEO.getId());
						advertisementMedias.add(advMedia);
					}
					saved.setAdvertisementMedia(advertisementMedias);
				}
				if(hasRelationChange){
					advertisementDAO.update(saved);
				}
			}catch(Exception e){
				String message = messageSource.getMessage("error.save.item", null, locale);
				LOGGER.error(message, e);
				return JSONView.getJsonSuccess(false, message);
			}
		}else{
			try{
				if(bPhoto != null){
					AdvertisementMedia advMedia = new AdvertisementMedia();
					advMedia.setMedia(bPhoto);
					advMedia.setAdvertisement(adv);
					advMedia.setType(MediaTypeEnum.PHOTO.getId());
					advertisementMedias.add(advMedia);
				}else{
					photoName = advertisementDAO.getPhotoNameByAdvertisement(id);
				}
				adv.setPhotoName(photoName);
				if(bVideo != null){
					AdvertisementMedia advMedia = new AdvertisementMedia();
					advMedia.setMedia(bVideo);
					advMedia.setAdvertisement(adv);
					advMedia.setType(MediaTypeEnum.VIDEO.getId());
					advertisementMedias.add(advMedia);
				}else{
					videoName = advertisementDAO.getVideoNameByAdvertisement(id);
				}
				adv.setVideoName(videoName);					
				if(!advertisementMedias.isEmpty()){
					adv.setAdvertisementMedia(advertisementMedias);
				}
				advertisementDAO.update(adv);
			}catch(Exception e){
				String message = messageSource.getMessage("error.update.item", null, locale);
				LOGGER.error(message, e);
				return JSONView.getJsonSuccess(false, message);
			}
		}
		return JSONView.getJsonSuccess(true, null);
	}
}
