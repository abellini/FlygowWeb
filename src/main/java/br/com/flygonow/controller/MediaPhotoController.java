package br.com.flygonow.controller;

import br.com.flygonow.dao.*;
import br.com.flygonow.util.JSONView;
import br.com.flygonow.util.MediaUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Locale;

@Controller
@RequestMapping("/getPhoto")
public class MediaPhotoController implements MessageSourceAware{

private static final Logger LOGGER = Logger.getLogger(MediaPhotoController.class);
	
	private MessageSource messageSource;
	
	@Autowired
	private CategoryMediaDao categoryDAO;
	
	@Autowired
	private AttendantMediaDao attendantDAO;
	
	@Autowired
	private FoodMediaDao foodDAO;
	
	@Autowired
	private AdvertisementMediaDao advertisementDAO;
	
	@Autowired
	private AccompanimentMediaDao accompanimentDAO;
	
	@Autowired
	private PromotionMediaDao promotionDAO;
	
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@RequestMapping(value = "/category/photoInfo/{imgId}")
	public @ResponseBody String getCategoryPhotoInfo(
			@PathVariable Long imgId,
			Locale locale
	){
		try{
			byte[] category = categoryDAO.getPhoto(imgId);
			InputStream is = new ByteArrayInputStream(category);
			BufferedImage read = ImageIO.read(is);
			int width = (int)(read.getWidth() * MediaUtils.getScale());
			int height = (int)(read.getHeight() * MediaUtils.getScale());
			return JSONView.getPhotoInfo(true, width, height); 
		}catch(Exception e){
			String message = messageSource.getMessage("error.parse.image", null, locale);
			LOGGER.error(message, e);
			return JSONView.getJsonSuccess(false, message);
		}
	}
	
	@RequestMapping(value = "/category/{imgId}")
	public @ResponseBody byte[] getCategoryImage(
			@PathVariable Long imgId,
			Locale locale
	){
		try{
			byte[] category = categoryDAO.getPhoto(imgId);
			InputStream is = new ByteArrayInputStream(category);
			return MediaUtils.convertImageWithOtherDimensions(is); 
		}catch(Exception e){
			String message = messageSource.getMessage("error.parse.image", null, locale);
			LOGGER.error(message, e);
		}
		return null;
	}
	
	@RequestMapping(value = "/attendant/photoInfo/{imgId}")
	public @ResponseBody String getAttendantPhotoInfo(
			@PathVariable Long imgId,
			Locale locale
	){
		try{
			byte[] attendant = attendantDAO.getPhoto(imgId);
			InputStream is = new ByteArrayInputStream(attendant);
			BufferedImage read = ImageIO.read(is);
			int width = (int)(read.getWidth() * MediaUtils.getScale());
			int height = (int)(read.getHeight() * MediaUtils.getScale());
			return JSONView.getPhotoInfo(true, width, height); 
		}catch(Exception e){
			String message = messageSource.getMessage("error.parse.image", null, locale);
			LOGGER.error(message, e);
			return JSONView.getJsonSuccess(false, message);
		}
	}
	
	@RequestMapping(value = "/attendant/{imgId}")
	public @ResponseBody byte[] getAttendantImage(
			@PathVariable Long imgId,
			Locale locale
	){
		try{
			byte[] attendant = attendantDAO.getPhoto(imgId);
			InputStream is = new ByteArrayInputStream(attendant);
			return MediaUtils.convertImageWithOtherDimensions(is); 
		}catch(Exception e){
			String message = messageSource.getMessage("error.parse.image", null, locale);
			LOGGER.error(message, e);
		}
		return null;
	}
	
	@RequestMapping(value = "/food/photoInfo/{imgId}")
	public @ResponseBody String getFoodPhotoInfo(
			@PathVariable Long imgId,
			Locale locale
	){
		try{
			byte[] food = foodDAO.getPhoto(imgId);
			InputStream is = new ByteArrayInputStream(food);
			BufferedImage read = ImageIO.read(is);
			int width = (int)(read.getWidth() * MediaUtils.getScale());
			int height = (int)(read.getHeight() * MediaUtils.getScale());
			return JSONView.getPhotoInfo(true, width, height); 
		}catch(Exception e){
			String message = messageSource.getMessage("error.parse.image", null, locale);
			LOGGER.error(message, e);
			return JSONView.getJsonSuccess(false, message);
		}
	}
	
	@RequestMapping(value = "/food/{imgId}")
	public @ResponseBody byte[] getFoodImage(
			@PathVariable Long imgId,
			Locale locale
	){
		try{
			byte[] food = foodDAO.getPhoto(imgId);
			InputStream is = new ByteArrayInputStream(food);
			return MediaUtils.convertImageWithOtherDimensions(is); 
		}catch(Exception e){
			String message = messageSource.getMessage("error.parse.image", null, locale);
			LOGGER.error(message, e);
		}
		return null;
	}
	
	@RequestMapping(value = "/advertisement/photoInfo/{imgId}")
	public @ResponseBody String getAdvertisementInfo(
			@PathVariable Long imgId,
			Locale locale
	){
		try{
			byte[] adv = advertisementDAO.getPhoto(imgId);
			InputStream is = new ByteArrayInputStream(adv);
			BufferedImage read = ImageIO.read(is);
			int width = (int)(read.getWidth() * MediaUtils.getScale());
			int height = (int)(read.getHeight() * MediaUtils.getScale());
			return JSONView.getPhotoInfo(true, width, height); 
		}catch(Exception e){
			String message = messageSource.getMessage("error.parse.image", null, locale);
			LOGGER.error(message, e);
			return JSONView.getJsonSuccess(false, message);
		}
	}
	
	@RequestMapping(value = "/advertisement/{imgId}")
	public @ResponseBody byte[] getAdvertisementImage(
			@PathVariable Long imgId,
			Locale locale
	){
		try{
			byte[] adv = advertisementDAO.getPhoto(imgId);
			InputStream is = new ByteArrayInputStream(adv);
			return MediaUtils.convertImageWithOtherDimensions(is); 
		}catch(Exception e){
			String message = messageSource.getMessage("error.parse.image", null, locale);
			LOGGER.error(message, e);
		}
		return null;
	}
	
	@RequestMapping(value = "/accompaniment/photoInfo/{imgId}")
	public @ResponseBody String getAccompanimentInfo(
			@PathVariable Long imgId,
			Locale locale
	){
		try{
			byte[] acc = accompanimentDAO.getPhoto(imgId);
			InputStream is = new ByteArrayInputStream(acc);
			BufferedImage read = ImageIO.read(is);
			int width = (int)(read.getWidth() * MediaUtils.getScale());
			int height = (int)(read.getHeight() * MediaUtils.getScale());
			return JSONView.getPhotoInfo(true, width, height); 
		}catch(Exception e){
			String message = messageSource.getMessage("error.parse.image", null, locale);
			LOGGER.error(message, e);
			return JSONView.getJsonSuccess(false, message);
		}
	}
	
	@RequestMapping(value = "/accompaniment/{imgId}")
	public @ResponseBody byte[] getAccompanimentImage(
			@PathVariable Long imgId,
			Locale locale
	){
		try{
			byte[] acc = accompanimentDAO.getPhoto(imgId);
			InputStream is = new ByteArrayInputStream(acc);
			return MediaUtils.convertImageWithOtherDimensions(is); 
		}catch(Exception e){
			String message = messageSource.getMessage("error.parse.image", null, locale);
			LOGGER.error(message, e);
		}
		return null;
	}
	
	@RequestMapping(value = "/promotion/photoInfo/{imgId}")
	public @ResponseBody String getPromotionInfo(
			@PathVariable Long imgId,
			Locale locale
	){
		try{
			byte[] promotion = promotionDAO.getPhoto(imgId);
			InputStream is = new ByteArrayInputStream(promotion);
			BufferedImage read = ImageIO.read(is);
			int width = (int)(read.getWidth() * MediaUtils.getScale());
			int height = (int)(read.getHeight() * MediaUtils.getScale());
			return JSONView.getPhotoInfo(true, width, height); 
		}catch(Exception e){
			String message = messageSource.getMessage("error.parse.image", null, locale);
			LOGGER.error(message, e);
			return JSONView.getJsonSuccess(false, message);
		}
	}
	
	@RequestMapping(value = "/promotion/{imgId}")
	public @ResponseBody byte[] getPromotionImage(
			@PathVariable Long imgId,
			Locale locale
	){
		try{
			byte[] promotion = promotionDAO.getPhoto(imgId);
			InputStream is = new ByteArrayInputStream(promotion);
			return MediaUtils.convertImageWithOtherDimensions(is); 
		}catch(Exception e){
			String message = messageSource.getMessage("error.parse.image", null, locale);
			LOGGER.error(message, e);
		}
		return null;
	}
}
