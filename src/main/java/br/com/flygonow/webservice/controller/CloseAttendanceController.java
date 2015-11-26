package br.com.flygonow.webservice.controller;

import br.com.flygonow.service.TabletService;
import br.com.flygonow.webservice.service.CloseAttendanceService;
import br.com.flygonow.webservice.util.JSONWebserviceView;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/webservice") 
public class CloseAttendanceController implements MessageSourceAware{
	
private static final Logger LOGGER = Logger.getLogger(CloseAttendanceController.class);
	
	private MessageSource messageSource;
	
	@Autowired
	private TabletService tabletService;
	
	@Autowired
	private CloseAttendanceService closeAttendanceService;
	
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@RequestMapping("/closeAttendance")
	public @ResponseBody String closeAttendance(
			@RequestParam String closeAttendanceJson,
			Locale locale
	){
		try{
			JSONObject fromTablet = JSONObject.fromObject(closeAttendanceJson);
			Long tabletNumber = fromTablet.getLong("tabletNumber");
			Long alertTypeId = fromTablet.getLong("alertType");
			
			String paymentFormsString = fromTablet.getString("paymentFormIds");
			List<Long> paymentFormIds = new ArrayList<Long>();
			List<String> asList = Arrays.asList(paymentFormsString.split(","));
			for(String srtId : asList){
				paymentFormIds.add(Long.parseLong(srtId));
			}
			closeAttendanceService.closeAttendance(tabletNumber, alertTypeId, paymentFormIds);
			return JSONWebserviceView.getJsonSuccess(true, null);
		}catch(Exception e){
			String message = messageSource.getMessage("error.closeAttendance", null, locale);
			LOGGER.error(message, e);
			return JSONWebserviceView.getJsonSuccess(false, message);
		}
	}
}
