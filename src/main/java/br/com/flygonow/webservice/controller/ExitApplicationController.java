package br.com.flygonow.webservice.controller;

import br.com.flygonow.webservice.service.ExitApplicationService;
import br.com.flygonow.webservice.util.JSONWebserviceView;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Locale;

@Controller
@RequestMapping("/webservice") 
public class ExitApplicationController implements MessageSourceAware{
	
private static final Logger LOGGER = Logger.getLogger(ExitApplicationController.class);
	
	private MessageSource messageSource;
	
	@Autowired
	private ExitApplicationService exitApplicationService;
	
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@RequestMapping("/exitApplication")
	public @ResponseBody String exitApplication(
			@RequestParam Short tabletNumber,
			Locale locale
	){
		try{
			exitApplicationService.onExitApplicationService(tabletNumber);
			return JSONWebserviceView.getJsonSuccess(true, null);
		}catch(Exception e){
			String message = messageSource.getMessage("error.exitApplication", null, locale);
			LOGGER.error(message, e);
			return JSONWebserviceView.getJsonSuccess(false, message);
		}
	}
}
