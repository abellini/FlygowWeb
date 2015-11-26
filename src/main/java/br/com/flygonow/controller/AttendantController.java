package br.com.flygonow.controller;

import br.com.flygonow.core.security.CryptUtil;
import br.com.flygonow.dao.AttendantDao;
import br.com.flygonow.dao.RoleDao;
import br.com.flygonow.entities.Attendant;
import br.com.flygonow.entities.AttendantAlert;
import br.com.flygonow.entities.AttendantMedia;
import br.com.flygonow.entities.Role;
import br.com.flygonow.enums.FetchTypeEnum;
import br.com.flygonow.enums.MediaTypeEnum;
import br.com.flygonow.enums.RoleTypeEnum;
import br.com.flygonow.enums.UserTypeEnum;
import br.com.flygonow.model.AttendantChartAlertByTimeModel;
import br.com.flygonow.model.AttendantChartLastAlertsModel;
import br.com.flygonow.service.AttendantService;
import br.com.flygonow.util.JSONView;
import br.com.flygonow.util.MediaUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Controller
@RequestMapping("/attendant")
public class AttendantController implements MessageSourceAware{

	private static final Logger LOGGER = Logger.getLogger(AttendantController.class);
	
	private static final String DEFAULT_PASSWORD = "flygow";
	
	private static final int MINIMUM_QTD_ROLE = 1;
	
	private MessageSource messageSource;
	
	@Autowired
	private AttendantService attendantService;
	
	@Autowired
	private AttendantDao attendantDAO;
	
	@Autowired
	private RoleDao roleDAO;
	
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
			List<Attendant> attendant = attendantService.listAll(strSearch);
			return JSONView.fromAttendant(attendant);
		}catch(Exception e){
			String message = messageSource.getMessage("error.list.items", null, locale);
			LOGGER.error(message, e);
			return JSONView.getJsonSuccess(false, message);
		}
	}

	@RequestMapping("/listPendentAlerts")
	public @ResponseBody String listPendentAlerts(Locale locale){
		try{
			List<AttendantAlert> alerts = attendantService.listPendentAlerts();
			return JSONView.fromAttendantAlert(alerts);
		}catch(Exception e){
			String message = messageSource.getMessage("error.list.pendent.alerts", null, locale);
			LOGGER.error(message, e);
			return JSONView.getJsonSuccess(false, message);
		}
	}

	@RequestMapping("/listLastAlerts/{size}")
	public @ResponseBody String listLastAlerts(@PathVariable Integer size, Locale locale){
		try{
			List<AttendantAlert> alerts = attendantService.listLastAlerts(size);
			return JSONView.fromAttendantAlert(alerts);
		}catch(Exception e){
			String message = messageSource.getMessage("error.list.last.alerts", null, locale);
			LOGGER.error(message, e);
			return JSONView.getJsonSuccess(false, message);
		}
	}

	@RequestMapping("/listLastAlertsForChart/{size}")
	public @ResponseBody String listLastAlertsForChart(@PathVariable Integer size, Locale locale){
		try{
			List<AttendantChartLastAlertsModel> attendantChartLastAlertsModels = attendantService.listLastAlertsForChart(size);
			return JSONView.fromAttendantChartModel(attendantChartLastAlertsModels);
		}catch(Exception e){
			String message = messageSource.getMessage("error.list.last.alerts", null, locale);
			LOGGER.error(message, e);
			return JSONView.getJsonSuccess(false, message);
		}
	}

	@RequestMapping("/listLastAlertsByTime/{size}")
	public @ResponseBody String listLastAlertsByTime(@PathVariable Integer size, Locale locale){
		try{
			List<AttendantChartAlertByTimeModel> attendantChartLastAlertsModels = attendantService.listLastAlertsByTime(size);
			return JSONView.fromAttendantChartAlertByTimeModel(attendantChartLastAlertsModels);
		}catch(Exception e){
			String message = messageSource.getMessage("error.list.last.alerts", null, locale);
			LOGGER.error(message, e);
			return JSONView.getJsonSuccess(false, message);
		}
	}

	@RequestMapping("/readNotification/{alertId}")
	public @ResponseBody String readNotification(@PathVariable Long alertId, Locale locale){
		try{
			List<AttendantAlert> alerts = attendantService.readNotification(alertId);
			return JSONView.fromAttendantAlert(alerts);
		}catch(Exception e){
			String message = messageSource.getMessage("error.read.alert", null, locale);
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
				if(!UserTypeEnum.ADMIN.getId().equals(id)){	
					attendantService.delete(id);
				}	
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
			attendantService.removeMedia(id, MediaTypeEnum.fromName(type));
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
		@RequestParam String lastName,
		@RequestParam String address,
		@RequestParam Date birthDate, 
		@RequestParam(required=false) MultipartFile photo,
		@RequestParam String login,
		@RequestParam(required=false) String password,
		@RequestParam(required=false) String stringRoles,
		@RequestParam(required=false) String email,
		Locale locale
	){
		if(!MediaUtils.validateImageFormat(photo)){
			String message = messageSource.getMessage("error.format.imagefile", null, locale);
			LOGGER.error(message);
			return JSONView.getJsonSuccess(false, message);
		}
		
		Attendant attendant = null;
		byte[] bPhoto = null;
		String photoName = null;
		Collection<AttendantMedia> attendantMedias = new ArrayList<AttendantMedia>();
		String passwordCrypt = null;
		try {
			if(photo != null && photo.getBytes().length > 0){
				bPhoto = photo.getBytes();
				photoName = Long.toString(new Date().getTime());
			}
		}catch(Exception e1){
			LOGGER.error(e1.getMessage());
		}
		Set<Role> roles = new HashSet<Role>();
		roles.add(RoleTypeEnum.fromRoleType(RoleTypeEnum.AUTHENTICATED));
		if(!"".equals(stringRoles)){
			for (String rolId : Arrays.asList(stringRoles.split(","))) {
				if (!"".equals(rolId)){
					Role rol = roleDAO.findById(Long.parseLong(rolId), FetchTypeEnum.LEFT.getName(), "modules");
					roles.add(rol);
				}	
			}
		}	
		try{
			if(!"".equals(password) && id == null){
				passwordCrypt = CryptUtil.md5base64(password);
			}else if(id != null && "".equals(password)){
				passwordCrypt = attendantDAO.findById(id, null).getPassword();
			}else{
				passwordCrypt = CryptUtil.md5base64(DEFAULT_PASSWORD);
			}
		}catch(Exception e){
			LOGGER.error(e.getMessage());
		}
		attendant = new Attendant(id, name, lastName, address, birthDate, photoName, login, passwordCrypt, email);
		if(attendant.getId() == null){
			try{
				Attendant saved = attendantDAO.save(attendant);
				saved.setRoles(roles);
				if(photoName != null){
					AttendantMedia attMedia = new AttendantMedia();
					attMedia.setMedia(bPhoto);
					attMedia.setAttendant(saved);
					attMedia.setType(MediaTypeEnum.PHOTO.getId());
					attendantMedias.add(attMedia);
				}
				attendant.setAttendantMedia(attendantMedias);
				attendantDAO.update(saved);
			}catch(Exception e){
				String message = messageSource.getMessage("error.save.item", null, locale);
				LOGGER.error(message, e);
				return JSONView.getJsonSuccess(false, message);
			}
		} else {
			try{
				if(roles.size() == MINIMUM_QTD_ROLE){
					Collection<Role> findByUserId = roleDAO.findByUserId(id);
					roles.clear();
					roles.addAll(findByUserId);					
				}
				attendant.setRoles(roles);
				if(bPhoto != null){
					AttendantMedia attMediaPhoto = new AttendantMedia();
					attMediaPhoto.setMedia(bPhoto);
					attMediaPhoto.setAttendant(attendant);
					attMediaPhoto.setType(MediaTypeEnum.PHOTO.getId());
					attendantMedias.add(attMediaPhoto);
				}else{
					photoName = attendantDAO.getPhotoNameByAttendant(id);
				}
				attendant.setPhotoName(photoName);
				if(!attendantMedias.isEmpty()){
					attendant.setAttendantMedia(attendantMedias);
				}
				attendantDAO.update(attendant);
			}catch(Exception e){
				String message = messageSource.getMessage("error.update.item", null, locale);
				LOGGER.error(message, e);
				return JSONView.getJsonSuccess(false, message);
			}
		}
		return JSONView.getJsonSuccess(true, null);
	}
}
