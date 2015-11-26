package br.com.flygonow.webservice.service.impl;

import br.com.flygonow.dao.OrderDao;
import br.com.flygonow.dao.OrderItemDao;
import br.com.flygonow.dao.TabletDao;
import br.com.flygonow.entities.Order;
import br.com.flygonow.entities.OrderItem;
import br.com.flygonow.entities.Tablet;
import br.com.flygonow.enums.OrderItemStatusEnum;
import br.com.flygonow.enums.OrderStatusEnum;
import br.com.flygonow.webservice.service.ExitApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExitApplicationServiceImpl implements ExitApplicationService{

	@Autowired
	private TabletDao tabletDAO;
	
	@Autowired
	private OrderDao orderDAO;
	
	@Autowired
	private OrderItemDao orderItemDAO;
	
	@Override
	public void onExitApplicationService(Short tabletNumber) {
		Tablet tablet = tabletDAO.getByNumber(tabletNumber);
		List<Order> listOpenedOrders = 
				orderDAO.listByTabletIdAndStatus(tablet.getId(), OrderStatusEnum.ORDER_OPEN.getId());
		if(listOpenedOrders != null && !listOpenedOrders.isEmpty()){
			for(Order order : listOpenedOrders){
				List<OrderItem> listByOrder = orderItemDAO.listByOrder(order.getId());
				if(listByOrder != null && !listByOrder.isEmpty()){
					for(OrderItem item : listByOrder){
						item.setOrderItemStatus(OrderItemStatusEnum.fromOrderItemStatus(OrderItemStatusEnum.CANCELED));
						orderItemDAO.update(item);
					}
				}
				order.setOrderStatus(OrderStatusEnum.fromOrderStatus(OrderStatusEnum.ORDER_CANCELED));
				orderDAO.update(order);
			}
		}
	}

}
