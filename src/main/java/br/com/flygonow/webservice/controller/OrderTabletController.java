package br.com.flygonow.webservice.controller;

import br.com.flygonow.dao.*;
import br.com.flygonow.entities.*;
import br.com.flygonow.enums.EntityType;
import br.com.flygonow.enums.FetchTypeEnum;
import br.com.flygonow.enums.OrderItemStatusEnum;
import br.com.flygonow.enums.OrderStatusEnum;
import br.com.flygonow.webservice.service.OrderTabletService;
import br.com.flygonow.webservice.util.JSONWebserviceView;
import br.com.flygonow.websocket.service.NotifyWebClientsService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/webservice")
public class OrderTabletController implements MessageSourceAware {

	private static final Logger LOGGER = Logger.getLogger(InitializeController.class);

	private MessageSource messageSource;

	@Autowired
	private OrderTabletService orderTabletService;
	
	@Autowired
	private TabletDao tabletDao;
	
	@Autowired
	private FoodDao foodDao;
	
	@Autowired
	private PromotionDao promotionDao;

	@Autowired
	private AccompanimentDao accompanimentDao;
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private OrderItemDao orderItemDao;
	
	@Autowired
	private AttendantDao attendantDao;

	@Autowired
	private NotifyWebClientsService notifyWebClientsService;

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@RequestMapping("/registerOrder")
	public @ResponseBody
	String registerOrder(@RequestParam(required = false) String orderJson,
			Locale locale) {
		try {
			JSONObject fromObject = JSONObject.fromObject(orderJson);
			JSONObject jsonOrder = fromObject.getJSONObject("order");
			Tablet tablet = tabletDao.getByNumber(((Integer) jsonOrder.getInt("tabletNumber")).shortValue());
			String message = "";
			if (tablet == null) {
				message = messageSource.getMessage("error.nonexists.tablet",
						null, locale);
				return JSONWebserviceView.getJsonSuccess(false, message);
			} else {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Long orderServerId;
				Order order;
				try{
					orderServerId = jsonOrder.getLong("orderServerId");
					Order orderFindedById = orderDao.findById(orderServerId, FetchTypeEnum.INNER.getName(), "orderItems");
					if(orderFindedById != null){
						order = orderFindedById; 
					}else{
						order = new Order();
					}
				}catch(Exception e){
					orderServerId = null;
					order = new Order();
				}
				order.setOrderStatus(OrderStatusEnum.fromOrderStatus(OrderStatusEnum.ORDER_OPEN));
				order.setTablet(tablet);
				order.setTabletOrderId(jsonOrder.getLong("orderId"));
				String dateStr = jsonOrder.getString("date");
				Date orderDate = sdf.parse(dateStr);
				order.setOrderHour(orderDate);
				Attendant attendant = attendantDao.findById(jsonOrder.getLong("attendantId"), null);
				order.setAttendant(attendant);
				order.setTotalValue(jsonOrder.getDouble("totalValue"));
				Hibernate.initialize(order.getOrderItems());
				JSONArray fromArray = fromObject.getJSONArray("orderItens");
				Date iniOrderDateHour = new java.sql.Date(new Date().getTime());
				for (int i = 0; i < fromArray.size(); i++) {
					JSONObject jsonLineItem = fromArray.getJSONObject(i);
					
					OrderItem item = new OrderItem();
					item.setOrderItemStatus(OrderItemStatusEnum.fromOrderItemStatus(OrderItemStatusEnum.IN_ATTENDANCE));
					item.setIniOrderDate(iniOrderDateHour);
					item.setIniOrderHour(iniOrderDateHour);
					item.setTabletOrderItemId(jsonLineItem.getLong("orderItemId"));
					item.setQuantity((short) jsonLineItem.getInt("quantity"));
					item.setObservations(jsonLineItem.getString("observations"));
					item.setValue(jsonLineItem.getDouble("value"));
					String productType = jsonLineItem.getString("productType");
					long productId = jsonLineItem.getLong("foodId");
					if (EntityType.isFood(productType)) {
						Food food = foodDao.findById(productId, null);
						item.setFood(food);	
					} else if (EntityType.isPromotion(productType)) {
						Promotion promo = promotionDao.findById(productId, null);
						item.setPromotion(promo);
					}
					try{
						JSONArray accompanimentsArray = JSONArray.fromObject(jsonLineItem.getString("accompaniments"));
						if(!accompanimentsArray.isEmpty()){
							List<Accompaniment> accompaniments = new ArrayList<Accompaniment>();
							for(int y = 0; y < accompanimentsArray.size(); y++){
								JSONObject jsonLineAccompaniment = accompanimentsArray.getJSONObject(y);
								Accompaniment acc = accompanimentDao.findById(jsonLineAccompaniment.getLong("id"), null);
								if(acc != null){
									accompaniments.add(acc);
								}
							}
							item.setAccompaniments(accompaniments);
						}
					}catch(Exception e){
						message = messageSource.getMessage("info.saveorder.noAccompaniments", null, locale);
						LOGGER.info(message, e);
					}
					order.addOrderItem(item);
				}
				Order saved = orderDao.update(order);

				String jsonServerIds = orderTabletService.returnServerIds(saved);
				message = messageSource.getMessage("success.saveorder.tablet",
						null, locale);

				try{
					notifyWebClientsService.sendWebAlertsToNewOrders();
				}catch (Exception e){
					message = messageSource.getMessage("error.list.new.orders", null, locale);
					LOGGER.error(message, e);
				}

				JSONObject response = new JSONObject();
				response.put("success", true);
				response.put("message", message);
				response.put("serverIds", jsonServerIds);
				return response.toString();
			}
		} catch (Exception e) {
			String message = messageSource.getMessage("error.saveorder.tablet", null, locale);
			LOGGER.error(message, e);
			return JSONWebserviceView.getJsonSuccess(false, message);
		}
	}
}
