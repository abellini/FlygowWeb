package br.com.flygonow.controller;

import br.com.flygonow.dao.ModuleDao;
import br.com.flygonow.entities.Module;
import br.com.flygonow.util.JSONView;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/module")
public class ModuleController implements MessageSourceAware{

	private static final Logger LOGGER = Logger.getLogger(ModuleController.class);
	
	private MessageSource messageSource;
	
	@Autowired
	private ModuleDao moduleDAO;
	
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@RequestMapping("/listAll")
	public @ResponseBody String listAll(Locale locale){
		try{
			List<Module> modules = moduleDAO.getAll(null);
			return JSONView.fromModule(modules);
		}catch(Exception e){
			String message = messageSource.getMessage("error.list.items", null, locale);
			LOGGER.error(message, e);
			return JSONView.getJsonSuccess(false, message);
		}
	}
}
