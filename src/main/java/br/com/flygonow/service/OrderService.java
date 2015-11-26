package br.com.flygonow.service;

import br.com.flygonow.entities.Order;
import br.com.flygonow.entities.OrderItem;

import java.util.List;

public interface OrderService {

	List<OrderItem> listPendentsToday(String strSearch);

	void setItemToReady(Long orderItemId);

	void setItemToCancel(Long orderItemId);
	
	Order getCurrentOrderByTablet(Long tabletId);

}
