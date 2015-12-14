package br.com.flygonow.service;

import br.com.flygonow.entities.Order;
import br.com.flygonow.entities.OrderItem;
import br.com.flygonow.enums.OrderItemStatusEnum;

import java.util.Date;
import java.util.List;

public interface OrderService {

	List<OrderItem> listPendentsToday(String strSearch);

	List<OrderItem> selectByParams(
			String strSearch,
			Date dateIni,
			Date dateEnd,
			OrderItemStatusEnum orderItemStatus);

	void setItemToReady(Long orderItemId);

	void setItemToCancel(Long orderItemId);
	
	Order getCurrentOrderByTablet(Long tabletId);

}
