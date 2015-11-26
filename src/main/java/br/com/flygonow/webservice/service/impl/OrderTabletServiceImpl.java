package br.com.flygonow.webservice.service.impl;

import br.com.flygonow.entities.Order;
import br.com.flygonow.entities.OrderItem;
import br.com.flygonow.webservice.service.OrderTabletService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class OrderTabletServiceImpl implements OrderTabletService {

	@Override
	public String returnServerIds(Order saved) {
		JSONObject orderWithServerIds = new JSONObject();
		orderWithServerIds.put("tabletOrderId", saved.getTabletOrderId());
		orderWithServerIds.put("serverOrderId", saved.getId());
		Collection<OrderItem> orderItems = saved.getOrderItems();
		if(orderItems != null && !orderItems.isEmpty()){
			JSONArray orderItemsArr = new JSONArray();
			for(OrderItem oi : orderItems){
				JSONObject orderItemWithServerIds = new JSONObject();
				orderItemWithServerIds.put("tabletOrderItemId", oi.getTabletOrderItemId());
				orderItemWithServerIds.put("serverOrderItemId", oi.getId());
				orderItemsArr.add(orderItemWithServerIds);
			}
			orderWithServerIds.put("orderItemIds", orderItemsArr);
		}
		return orderWithServerIds.toString();
	}
}
