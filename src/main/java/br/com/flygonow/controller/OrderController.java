package br.com.flygonow.controller;

import br.com.flygonow.entities.OrderItem;
import br.com.flygonow.enums.OrderItemStatusEnum;
import br.com.flygonow.service.OrderService;
import br.com.flygonow.util.JSONView;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/order")
public class OrderController implements MessageSourceAware{

	private static final Logger LOGGER = Logger.getLogger(OrderController.class);
	
	private MessageSource messageSource;
	
	@Autowired
	private OrderService orderService;
	
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@RequestMapping("/listPendentsToday")
	public @ResponseBody String listPendentsToday(
			@RequestParam(required = false) String strSearch,
			Locale locale
	){
		List<OrderItem> orderItems = null;
		try{
			orderItems = orderService.listPendentsToday(strSearch);
			return JSONView.fromOrderItems(orderItems);
		}catch(Exception e){
			String message = messageSource.getMessage("error.list.items", null, locale);
			LOGGER.error(message, e);
			return JSONView.getJsonSuccess(false, message);
		}
	}


	@RequestMapping("/listOrderItemStatus")
	public @ResponseBody String listOrderItemStatus(
			Locale locale
	){
		try{
			return JSONView.fromOrderItemStatus(OrderItemStatusEnum.values());
		}catch(Exception e){
			String message = messageSource.getMessage("error.list.items", null, locale);
			LOGGER.error(message, e);
			return JSONView.getJsonSuccess(false, message);
		}
	}

	@RequestMapping("/listOrders")
	public @ResponseBody String listOrders(
			@RequestParam(required = false) String strSearch,
			@RequestParam(required = false) String dateIniStr,
			@RequestParam(required = false) String dateEndStr,
			@RequestParam(required = false) Long statusId,
			Locale locale
	) throws ParseException {
		List<OrderItem> orderItems = null;
		Date dateIni = null, dateEnd = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if(dateIniStr != null){
			dateIni = sdf.parse(dateIniStr);
		}
		if(dateEndStr != null){
			dateEnd = sdf.parse(dateEndStr);
		}
		try{
			orderItems = orderService.selectByParams(strSearch, dateIni, dateEnd, OrderItemStatusEnum.fromId(statusId));
			return JSONView.fromOrderItems(orderItems);
		}catch(Exception e){
			String message = messageSource.getMessage("error.list.items", null, locale);
			LOGGER.error(message, e);
			return JSONView.getJsonSuccess(false, message);
		}
	}
	
	@RequestMapping("/itemReady")
	public @ResponseBody String itemReady(
			@RequestParam Long orderItemId,
			Locale locale
	){
		try{
			orderService.setItemToReady(orderItemId);
			return JSONView.getJsonSuccess(true, null);
		}catch(Exception e){
			String message = messageSource.getMessage("error.orderitem.ready", null, locale);
			LOGGER.error(message, e);
			return JSONView.getJsonSuccess(false, message);
		}
	}
	
	@RequestMapping("/itemCancel")
	public @ResponseBody String itemCancel(
			@RequestParam Long orderItemId,
			Locale locale
	){
		try{
			orderService.setItemToCancel(orderItemId);
			return JSONView.getJsonSuccess(true, null);
		}catch(Exception e){
			String message = messageSource.getMessage("error.orderitem.cancel", null, locale);
			LOGGER.error(message, e);
			return JSONView.getJsonSuccess(false, message);
		}
	}
}
