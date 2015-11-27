package br.com.flygonow.controller;

import br.com.flygonow.config.FlygowConfigExternalProperties;
import br.com.flygonow.entities.Accompaniment;
import br.com.flygonow.entities.Advertisement;
import br.com.flygonow.entities.Food;
import br.com.flygonow.entities.Promotion;
import br.com.flygonow.service.MediaService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@Controller
@RequestMapping("/getVideo")
public class MediaVideoController implements MessageSourceAware{

	private static final Logger LOGGER = Logger.getLogger(MediaVideoController.class);

	private MessageSource messageSource;
	
	@Autowired
	private MediaService mediaService;

	@Autowired
	private FlygowConfigExternalProperties externalProperties;

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@RequestMapping(value = "/promotion/{videoId}")
	public void getPromotionVideo(
			@PathVariable Long videoId,
			HttpServletRequest request,
			HttpServletResponse response,
			Locale locale
			){
		try{
			String rootPath = externalProperties.getProperty("url.temp.root.videos");
			mediaService.runPromotionVideo(
					Promotion.class.getSimpleName(), 
					rootPath, 
					videoId, 
					request, 
					response);
		}catch(IOException ioe){
			String message = messageSource.getMessage("error.broken.video", null, locale);
			LOGGER.error(message, ioe);
		}
		catch(Exception e){
			String message = messageSource.getMessage("error.parse.video", null, locale);
			LOGGER.error(message, e);
		}
	}
	
	@RequestMapping(value = "/advertisement/{videoId}")
	public void getAdvertisementVideo(
			@PathVariable Long videoId,
			HttpServletRequest request,
			HttpServletResponse response,
			Locale locale
			){
		try{
			String rootPath = externalProperties.getProperty("url.temp.root.videos");
			mediaService.runAdvertisementVideo(
					Advertisement.class.getSimpleName(), 
					rootPath, 
					videoId, 
					request, 
					response);
		}catch(IOException ioe){
			String message = messageSource.getMessage("error.broken.video", null, locale);
			LOGGER.error(message, ioe);
		}
		catch(Exception e){
			String message = messageSource.getMessage("error.parse.video", null, locale);
			LOGGER.error(message, e);
		}
	}
	
	@RequestMapping(value = "/accompaniment/{videoId}")
	public void getAccompanimentVideo(
			@PathVariable Long videoId,
			HttpServletRequest request,
			HttpServletResponse response,
			Locale locale
			){
		try{
			String rootPath = externalProperties.getProperty("url.temp.root.videos");
			mediaService.runAccompanimentVideo(
					Accompaniment.class.getSimpleName(), 
					rootPath, 
					videoId, 
					request, 
					response);
		}catch(IOException ioe){
			String message = messageSource.getMessage("error.broken.video", null, locale);
			LOGGER.error(message, ioe);
		}
		catch(Exception e){
			String message = messageSource.getMessage("error.parse.video", null, locale);
			LOGGER.error(message, e);
		}
	}
	
	@RequestMapping(value = "/food/{videoId}")
	public void getFoodVideo(
			@PathVariable Long videoId,
			HttpServletRequest request,
			HttpServletResponse response,
			Locale locale
			){
		try{
			String rootPath = externalProperties.getProperty("url.temp.root.videos");
			mediaService.runFoodVideo(
					Food.class.getSimpleName(), 
					rootPath, 
					videoId, 
					request, 
					response);
		}catch(IOException ioe){
			String message = messageSource.getMessage("error.broken.video", null, locale);
			LOGGER.error(message, ioe);
		}
		catch(Exception e){
			String message = messageSource.getMessage("error.parse.video", null, locale);
			LOGGER.error(message, e);
		}
	}
}
