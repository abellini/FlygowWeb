package br.com.flygonow.service.impl;

import br.com.flygonow.dao.OrderDao;
import br.com.flygonow.dao.OrderItemDao;
import br.com.flygonow.entities.OperationalArea;
import br.com.flygonow.entities.Order;
import br.com.flygonow.entities.OrderItem;
import br.com.flygonow.entities.OrderItemStatus;
import br.com.flygonow.enums.OrderItemStatusEnum;
import br.com.flygonow.enums.OrderStatusEnum;
import br.com.flygonow.service.OperationalAreaService;
import br.com.flygonow.service.OrderService;
import org.apache.log4j.Logger;
import org.hibernate.LazyInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

	private static Logger LOGGER = Logger.getLogger(OrderServiceImpl.class);
	
	@Autowired
	private OrderItemDao orderItemDAO;
	
	@Autowired
	private OrderDao orderDAO;
	
	@Autowired
	private OperationalAreaService operationalAreaService;
	
	@Override
	public List<OrderItem> listPendentsToday(String strSearch) {
		Date today = new Date();
		return getOrderItems(strSearch, null, today, OrderItemStatusEnum.IN_ATTENDANCE);
	}

	@Override
	public List<OrderItem> selectByParams(
			String strSearch,
			Date dateIni,
			Date dateEnd,
			OrderItemStatusEnum orderItemStatus) {
		return getOrderItems(
				strSearch,
				dateIni,
				dateEnd != null ? dateEnd : new Date(),
				orderItemStatus != null ? orderItemStatus : OrderItemStatusEnum.IN_ATTENDANCE);
	}

	@Override
	public void setItemToReady(Long orderItemId) {
		OrderItem findById = orderItemDAO.findById(orderItemId, null);
		OrderItemStatus fromOrderItemStatus = 
				OrderItemStatusEnum.fromOrderItemStatus(OrderItemStatusEnum.READY);
		findById.setOrderItemStatus(fromOrderItemStatus);
		orderItemDAO.update(findById);
	}

	@Override
	public void setItemToCancel(Long orderItemId) {
		OrderItem findById = orderItemDAO.findById(orderItemId, null);
		OrderItemStatus fromOrderItemStatus = 
				OrderItemStatusEnum.fromOrderItemStatus(OrderItemStatusEnum.CANCELED);
		findById.setOrderItemStatus(fromOrderItemStatus);
		orderItemDAO.update(findById);
	}

	@Override
	public Order getCurrentOrderByTablet(Long tabletId) {
		Order order = null;
		try{
			order = orderDAO.getCurrentOrderByTablet(tabletId);
		}catch(LazyInitializationException ex){
			order = orderDAO.listByTabletIdAndStatus(tabletId, OrderStatusEnum.ORDER_OPEN.getId()).get(0);
		}catch (PersistenceException e) {
			order = orderDAO.listByTabletIdAndStatus(tabletId, OrderStatusEnum.ORDER_OPEN.getId()).get(0);
		}
		return order;
	}

	private List<OrderItem> getOrderItems(String strSearch, Date dateIni, Date dateEnd, OrderItemStatusEnum orderItemStatus) {
		List<OrderItem> orderItems = null;
		try{
			List<OperationalArea> listOperationalAreasFromLoggerUser =
					operationalAreaService.listOperationalAreasFromLoggerUser();
			orderItems = orderItemDAO.listAllFromDateUserStatus(
					strSearch,
					dateIni,
					dateEnd,
					listOperationalAreasFromLoggerUser,
					orderItemStatus
			);
		}catch(Exception e){
			LOGGER.error("LIST PENDENTS TODAY ERROR ->>> " + e.getMessage());
		}
		return orderItems;
	}
}
