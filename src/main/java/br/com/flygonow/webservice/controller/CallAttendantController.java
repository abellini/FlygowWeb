package br.com.flygonow.webservice.controller;

import br.com.flygonow.webservice.service.CallAttendantService;
import br.com.flygonow.webservice.util.JSONWebserviceView;
import br.com.flygonow.websocket.service.NotifyWebClientsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Locale;

@Controller
@RequestMapping("/webservice") 
public class CallAttendantController implements MessageSourceAware{
	
private static final Logger LOGGER = Logger.getLogger(CallAttendantController.class);
	
	private MessageSource messageSource;
	
	@Autowired
	private CallAttendantService callAttendantService;

	@Autowired
	private NotifyWebClientsService notifyWebClientsService;

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@RequestMapping("/callAttendant")
	public @ResponseBody String callAttendant(
			@RequestParam(required=false) Long alertTypeId,
			@RequestParam(required=false) Long deviceId,
			@RequestParam Long attendantId,
			@RequestParam Short tabletNumber,
			Locale locale
	){
		try{
			callAttendantService.callAttendant(tabletNumber, attendantId, alertTypeId, deviceId);
			return JSONWebserviceView.getJsonSuccess(true, null);
		}catch(Exception e){
			String message = messageSource.getMessage("error.callattendant", null, locale);
			LOGGER.error(message, e);
			return JSONWebserviceView.getJsonSuccess(false, message);
		}
	}
}
