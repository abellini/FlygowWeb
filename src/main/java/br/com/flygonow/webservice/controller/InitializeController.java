package br.com.flygonow.webservice.controller;

import br.com.flygonow.dao.*;
import br.com.flygonow.entities.Advertisement;
import br.com.flygonow.entities.Attendant;
import br.com.flygonow.entities.Coin;
import br.com.flygonow.entities.Tablet;
import br.com.flygonow.enums.TabletStatusEnum;
import br.com.flygonow.service.MediaService;
import br.com.flygonow.util.JSONView;
import br.com.flygonow.webservice.service.InitializeService;
import br.com.flygonow.webservice.util.JSONWebserviceView;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/webservice")
public class InitializeController implements MessageSourceAware{

	private static final Logger LOGGER = Logger.getLogger(InitializeController.class);
	
	private MessageSource messageSource;

	@Autowired
	private InitializeService initializeService;
	
	@Autowired
	private MediaService mediaService;
	
	@Autowired
	private TabletDao tabletDao;
	
	@Autowired
	private CoinDao coinDao;
	
	@Autowired
	private AttendantDao attendantDao;
	
	@Autowired
	private AdvertisementDao advertisementDao;
	
	@Autowired
	private AdvertisementMediaDao advertisementMediaDao;
	
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@RequestMapping("/ping")
	public @ResponseBody String ping(
			Locale locale
	){
		return JSONWebserviceView.getJsonSuccess(true, null);
	}
	
	@RequestMapping("/connect")
	public @ResponseBody String connect(
			@RequestParam(required=false) boolean isReconnect,
			@RequestParam(required=false) boolean isChangeConfiguration,
			@RequestParam(required=false) Long previousTabletNumber,
			@RequestParam(required=false) String tabletJson,
			Locale locale
	){
		try{
			ObjectMapper mapper = new ObjectMapper();
			Tablet tablet = mapper.readValue(tabletJson, Tablet.class);
			Tablet byParams;
			if(isChangeConfiguration){
				byParams = tabletDao.getByNumber(previousTabletNumber.shortValue());
			}else{
				byParams = tabletDao.getByNumber(tablet.getNumber());
			}
			if(byParams != null && !isReconnect){
				String message = messageSource.getMessage("error.exists.tablet", null, locale);
				return JSONWebserviceView.getJsonSuccess(false, message);
			}else{
				String message;
				if(!isReconnect || byParams == null){
					message = messageSource.getMessage("sucess.save.tablet", null, locale);
					tabletDao.save(tablet);
				}else{
					message = messageSource.getMessage("sucess.connect.tablet", null, locale);
					byParams.setServiceStatus(TabletStatusEnum.fromTabletStatus(TabletStatusEnum.AVALIABLE));
					byParams.setNumber(tablet.getNumber());
					byParams.setIp(tablet.getIp());
					byParams.setPort(tablet.getPort());
					byParams.setServerIP(tablet.getServerIP());
					byParams.setServerPort(tablet.getServerPort());
					tabletDao.update(byParams);
				}
				List<Coin> coins = coinDao.getAll(null);
				List<Attendant> attendants = attendantDao.getAll(null);
				List<Advertisement> advertisements = advertisementDao.getAllActives();
				JSONObject response = new JSONObject();
				response.put("success", true);
				response.put("message", message);
				response.put("coins", JSONWebserviceView.fromCoin(coins));
				response.put("attendants", JSONWebserviceView.fromAttendant(attendants));
				response.put("advertisements", JSONWebserviceView.fromAdvertisement(advertisements));
				return response.toString(); 
			}
		}catch(Exception e){
			String message = messageSource.getMessage("error.connect.tablet", null, locale);
			LOGGER.error(message, e);
			return JSONWebserviceView.getJsonSuccess(false, message);
		}
	}
		
	@RequestMapping("/registerDetails")
	public @ResponseBody String registerDetails(
			@RequestParam(required=false) String tabletDetailsJson,
			Locale locale
	){
		try{
			JSONObject fromObject = JSONObject.fromObject(tabletDetailsJson); 
			Tablet tablet = tabletDao.getByNumber(((Integer)fromObject.get("tabletNumber")).shortValue());
			String message = "";
			if(tablet == null){
				message = messageSource.getMessage("error.nonexists.tablet", null, locale);
				return JSONWebserviceView.getJsonSuccess(false, message);
			}else{
				Coin coin = coinDao.findById(fromObject.getLong(("coinId")), null);
				Attendant attendant = attendantDao.findById(fromObject.getLong("attendantId"), null);
				String advertisements = fromObject.getString("advertisements");
				Set<Advertisement> listAdvertisements = new HashSet<Advertisement>();
				if(advertisements != null){
					List<String> listIdsAdv = Arrays.asList(advertisements.split(","));
					for(String id : listIdsAdv){
						if(!"".equals(id)){
							listAdvertisements.add(
								advertisementDao.findById(Long.parseLong(id), null)
							);							
						}
		            }
				}
				tablet.setCoin(coin);
				tablet.setAttendant(attendant);
				tablet.setAdvertisements(listAdvertisements);
				tabletDao.update(tablet);
				
				message = messageSource.getMessage("success.registerdetails.tablet", null, locale);
				JSONObject initialData = initializeService.getInitialData();
				JSONObject response = new JSONObject();
				response.put("success", true);
				response.put("message", message);
				response.put("initialData", initialData);
				return response.toString();			
			}
		}catch(Exception e){
			String message = messageSource.getMessage("error.registerdetails.tablet", null, locale);
			LOGGER.error(message, e);
			return JSONWebserviceView.getJsonSuccess(false, message);
		}
	}
	
	@RequestMapping("/initializeProductVideo")
	public @ResponseBody String initializeProductVideo(
			@RequestParam Long entityId,
			@RequestParam String entityType,
			HttpServletRequest request,
			HttpServletResponse response,
			Locale locale
	){
		try{
			mediaService.getProductVideo(entityType, entityId, response);
			return JSONView.getJsonSuccess(true, null);
		}catch(IOException ioe){
			String message = messageSource.getMessage("error.broken.video", null, locale);
			LOGGER.error(message, ioe);
			return JSONView.getJsonSuccess(false, message);
		}
		catch(Exception e){
			String message = messageSource.getMessage("error.parse.video", null, locale);
			LOGGER.error(message, e);
			return JSONView.getJsonSuccess(false, message);
		}
	}
	
	@RequestMapping("/initializeProductImages")
	public @ResponseBody String initializeProductImages(
			@RequestParam Long entityId,
			@RequestParam String entityType,
			HttpServletRequest request,
			HttpServletResponse response,
			Locale locale
	){
		try {
			mediaService.getProductPhoto(entityType, entityId, response);
			return JSONView.getJsonSuccess(true, null);
		} catch(IOException ioe) {
			String message = messageSource.getMessage("error.broken.image", null, locale);
			LOGGER.error(message, ioe);
			return JSONView.getJsonSuccess(false, message);
		} catch(Exception e) {
			String message = messageSource.getMessage("error.parse.image", null, locale);
			LOGGER.error(message, e);
			return JSONView.getJsonSuccess(false, message);
		}
	}
}
