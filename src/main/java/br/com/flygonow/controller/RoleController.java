package br.com.flygonow.controller;

import br.com.flygonow.dao.ModuleDao;
import br.com.flygonow.dao.RoleDao;
import br.com.flygonow.entities.Module;
import br.com.flygonow.entities.Role;
import br.com.flygonow.enums.RoleTypeEnum;
import br.com.flygonow.service.RoleService;
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
@RequestMapping("/role")
public class RoleController implements MessageSourceAware{

	private static final Logger LOGGER = Logger.getLogger(RoleController.class);
	
	private MessageSource messageSource;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private RoleDao roleDAO;
	
	@Autowired
	private ModuleDao moduleDAO;
	
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
			List<Role> roles = null;
			if(strSearch != null && !"".equals(strSearch)){
				roles = roleDAO.listAllByNameWithModules(strSearch);
			}else{
				roles = roleDAO.getAllWithModules();
			}
			return JSONView.fromRole(roles);
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
				if(!RoleTypeEnum.ADMIN.getId().equals(id) && !RoleTypeEnum.AUTHENTICATED.getId().equals(id)){
					roleService.delete(id);
				}	
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
		@RequestParam(required=false) String modules,
		Locale locale
	){
		Set<Module> mods = new HashSet<Module>();
		if(mods != null){
			for (String modId : Arrays.asList(modules.split(","))) {
				if (!"".equals(modId)){
					Module mod = moduleDAO.findById(Long.parseLong(modId), null);
					mods.add(mod);
				}	
			}
		}	
		Role role = null;
		try {
			role = new Role(id, name, description);
		}catch(Exception e1){
			e1.printStackTrace();
		}
		if(role.getId() == null){
			try{
				Role saved = roleDAO.save(role);
				if(!mods.isEmpty()){
					saved.setModules(mods);
					roleDAO.update(saved);
				}
			}catch(Exception e){
				String message = messageSource.getMessage("error.save.item", null, locale);
				LOGGER.error(message, e);
				return JSONView.getJsonSuccess(false, message);
			}
		}else{
			try{
				if(!mods.isEmpty()){
					role.setModules(mods);
				}
				roleDAO.update(role);
			}catch(Exception e){
				String message = messageSource.getMessage("error.update.item", null, locale);
				LOGGER.error(message, e);
				return JSONView.getJsonSuccess(false, message);
			}
		}
		return JSONView.getJsonSuccess(true, null);
	}
}
