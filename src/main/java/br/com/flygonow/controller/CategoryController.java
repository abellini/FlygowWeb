package br.com.flygonow.controller;

import br.com.flygonow.dao.CategoryDao;
import br.com.flygonow.entities.Category;
import br.com.flygonow.entities.CategoryMedia;
import br.com.flygonow.enums.MediaTypeEnum;
import br.com.flygonow.service.CategoryService;
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
import java.util.*;

@Controller
@RequestMapping("/category")
public class CategoryController implements MessageSourceAware{

	private static final Logger LOGGER = Logger.getLogger(CategoryController.class);
	
	private MessageSource messageSource;
	
	@Autowired
	private CategoryService categoryService;
	
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
			List<Category> categories = null;
			if(strSearch != null && !"".equals(strSearch)){
				categories = categoryDAO.listByName(strSearch, null);
			}else{
				categories = categoryDAO.getAll(null);
			}
			return JSONView.fromCategory(categories);
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
				categoryService.delete(id);
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
			categoryService.removeMedia(id, MediaTypeEnum.fromName(type));
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
		@RequestParam(required=false) MultipartFile photo,
		Locale locale
	){
		if(!MediaUtils.validateImageFormat(photo)){
			String message = messageSource.getMessage("error.format.imagefile", null, locale);
			LOGGER.error(message);
			return JSONView.getJsonSuccess(false, message);
		}
		Category cat = null;
		byte[] bPhoto = null;
		String photoName = null;
		Collection<CategoryMedia> categoryMedias = new ArrayList<CategoryMedia>();
		try {
			if(photo != null && photo.getBytes().length > 0){
				bPhoto = photo.getBytes();
				photoName = Long.toString(new Date().getTime());
			}
			cat = new Category(id, name, description, photoName);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if(cat.getId() == null){
			try{
				Category saved = categoryDAO.save(cat);
				if(photoName != null){
					CategoryMedia catMedia = new CategoryMedia();
					catMedia.setMedia(bPhoto);
					catMedia.setCategory(saved);
					catMedia.setType(MediaTypeEnum.PHOTO.getId());
					categoryMedias.add(catMedia);
				}
				saved.setCategoryMedia(categoryMedias);
				categoryDAO.update(saved);
			}catch(Exception e){
				String message = messageSource.getMessage("error.save.item", null, locale);
				LOGGER.error(message, e);
				return JSONView.getJsonSuccess(false, message);
			}
		}else{
			try{
				if(bPhoto != null){
					CategoryMedia catMedia = new CategoryMedia();
					catMedia.setMedia(bPhoto);
					catMedia.setCategory(cat);
					catMedia.setType(MediaTypeEnum.PHOTO.getId());
					categoryMedias.add(catMedia);
				}else{
					photoName = categoryDAO.getPhotoNameByCategory(id);
				}
				cat.setPhotoName(photoName);
				if(!categoryMedias.isEmpty()){
					cat.setCategoryMedia(categoryMedias);
				}
				categoryDAO.update(cat);
			}catch(Exception e){
				String message = messageSource.getMessage("error.update.item", null, locale);
				LOGGER.error(message, e);
				return JSONView.getJsonSuccess(false, message);
			}
		}
		return JSONView.getJsonSuccess(true, null);
	}
}
