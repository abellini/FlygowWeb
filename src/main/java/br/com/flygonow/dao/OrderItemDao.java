package br.com.flygonow.dao;

import br.com.flygonow.entities.OperationalArea;
import br.com.flygonow.entities.OrderItem;
import br.com.flygonow.enums.OrderItemStatusEnum;

import java.util.Date;
import java.util.List;

public interface OrderItemDao  extends GenericDao<OrderItem, Long>{

	List<OrderItem> listByAccompanimentId(Long id);

	List<OrderItem> listByFoodId(Long id);

	List<OrderItem> listByPromotionId(Long id);

	List<OrderItem> listAllFromDateUserStatus(String strSearch, Date date,
			List<OperationalArea> listOperationalAreasFromLoggerUser,
			OrderItemStatusEnum... orderItemStatus);

	List<OrderItem> listByOrder(Long id);

	List<OrderItem> listByOrderAndItemStatus(Long id, OrderItemStatusEnum orderItemStatus);
}
