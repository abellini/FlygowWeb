package br.com.flygonow.controller;

import br.com.flygonow.dao.OperationalAreaDao;
import br.com.flygonow.dao.RoleDao;
import br.com.flygonow.entities.OperationalArea;
import br.com.flygonow.entities.Role;
import br.com.flygonow.enums.FetchTypeEnum;
import br.com.flygonow.service.OperationalAreaService;
import br.com.flygonow.util.JSONView;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/operationalarea")
public class OperationalAreaController implements MessageSourceAware{

	private static final Logger LOGGER = Logger.getLogger(OperationalAreaController.class);
	
	private MessageSource messageSource;
	
	@Autowired
	private OperationalAreaService operationalAreaService;
	
	@Autowired
	private OperationalAreaDao operationalAreaDAO;
	
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
			List<OperationalArea> ops = null;
			if(strSearch != null && !"".equals(strSearch)){
				ops = operationalAreaDAO.listByName(strSearch, FetchTypeEnum.LEFT.getName(), "roles");
			}else{
				ops = operationalAreaDAO.getAll(FetchTypeEnum.LEFT.getName(), "roles");
			}
			return JSONView.fromOperationalArea(ops);
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
				operationalAreaService.delete(id);
			}
			return JSONView.getJsonSuccess(true, null);
		}catch(Exception e){
			String message = messageSource.getMessage("error.delete.items", null, locale);
			LOGGER.error(message, e);
			return JSONView.getJsonSuccess(false, message);
		}
	}
	
	@RequestMapping("/saveUpdate")
	public @ResponseBody String saveUpdate(
		@RequestParam(required=false) Long id,
		@RequestParam String name,
		@RequestParam String description,
		@RequestParam(required=false) String stringRoles,
		Locale locale
	){
		Set<Role> roles = new HashSet<Role>();
		if(!"".equals(stringRoles)){
			for (String rolId : Arrays.asList(stringRoles.split(","))) {
				if (!"".equals(rolId)){
					Role rol = roleDAO.findById(Long.parseLong(rolId), FetchTypeEnum.LEFT.getName(), "modules");
					roles.add(rol);
				}	
			}
		}
		OperationalArea op = new OperationalArea(id, name, description);
		if(op.getId() == null){
			try{
				OperationalArea saved = operationalAreaDAO.save(op);
				saved.setRoles(roles);
				operationalAreaDAO.update(saved);
			}catch(Exception e){
				String message = messageSource.getMessage("error.save.item", null, locale);
				LOGGER.error(message, e);
				return JSONView.getJsonSuccess(false, message);
			}
		}else{
			try{
				op.setRoles(roles);
				operationalAreaDAO.update(op);
			}catch(Exception e){
				String message = messageSource.getMessage("error.update.item", null, locale);
				LOGGER.error(message, e);
				return JSONView.getJsonSuccess(false, message);
			}
		}
		return JSONView.getJsonSuccess(true, null);
	}
}
