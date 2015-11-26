package br.com.flygonow.controller;

import br.com.flygonow.dao.PaymentFormDao;
import br.com.flygonow.entities.PaymentForm;
import br.com.flygonow.service.PaymentFormService;
import br.com.flygonow.util.JSONView;
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
@RequestMapping("/paymentform")
public class PaymentFormController implements MessageSourceAware{

	private static final Logger LOGGER = Logger.getLogger(PaymentFormController.class);
	
	private MessageSource messageSource;
	
	@Autowired
	private PaymentFormService paymentService;
	
	@Autowired
	private PaymentFormDao paymentDAO;
	
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
			List<PaymentForm> pms = null;
			if(strSearch != null && !"".equals(strSearch)){
				pms = paymentDAO.listByName(strSearch, null);
			}else{
				pms = paymentDAO.getAll(null);
			}
			return JSONView.fromPaymentForm(pms);
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
				paymentService.delete(id);
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
		Locale locale
	){
		PaymentForm pay = new PaymentForm(id, name, description);
		if(pay.getId() == null){
			try{
				paymentDAO.save(pay);
			}catch(Exception e){
				String message = messageSource.getMessage("error.save.item", null, locale);
				LOGGER.error(message, e);
				return JSONView.getJsonSuccess(false, message);
			}
		}else{
			try{
				paymentDAO.update(pay);
			}catch(Exception e){
				String message = messageSource.getMessage("error.update.item", null, locale);
				LOGGER.error(message, e);
				return JSONView.getJsonSuccess(false, message);
			}
		}
		return JSONView.getJsonSuccess(true, null);
	}
}
