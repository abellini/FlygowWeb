package br.com.flygonow.webservice.controller;

import br.com.flygonow.service.TabletService;
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
public class TabletStatusController implements MessageSourceAware {

	private static final Logger LOGGER = Logger.getLogger(TabletStatusController.class);

	private MessageSource messageSource;
	
	@Autowired
	private TabletService tabletService;
	
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@RequestMapping("/refreshTabletStatus")
	public @ResponseBody
	String refreshTabletStatus(
		@RequestParam Long statusId,
		@RequestParam Long tabletNumber,
		Locale locale) {
		try{
			tabletService.changeTabletStatus(tabletNumber.shortValue(), statusId);
			return JSONWebserviceView.getJsonSuccess(true, null);
		}catch(Exception e){
			String message = messageSource.getMessage("error.save.tabletStatus", null, locale);
			LOGGER.error(message, e);
			return JSONWebserviceView.getJsonSuccess(false, message);
		}
	}
}
