package br.com.flygonow.dao.impl;

import br.com.flygonow.dao.OrderDao;
import br.com.flygonow.entities.Order;
import br.com.flygonow.enums.OrderStatusEnum;
import org.hibernate.LazyInitializationException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderDaoImp extends GenericDaoImp<Order, Long> implements OrderDao {

	@Override
	public List<Order> listByAttendantId(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return this.listByParams("SELECT o FROM Order o JOIN o.attendant a WHERE a.id = :id", params);
	}

	@Override
	public List<Order> listByTabletId(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return this.listByParams("SELECT o FROM Order o JOIN o.tablet t WHERE t.id = :id", params);
	}

	@Override
	public List<Order> listByPaymentFormId(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return this.listByParams("SELECT o FROM Order o JOIN o.paymentForms pf WHERE pf.id = :id", params);
	}

	@Override
	public Order getCurrentOrderByTablet(Long tabletId) throws LazyInitializationException{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tabletId", tabletId);
		params.put("statusId", OrderStatusEnum.ORDER_OPEN.getId());
		Order order = this.getByParams("SELECT o FROM Order o JOIN o.tablet t WHERE o.orderStatus.id = :statusId AND t.id = :tabletId", params);
		return order;
	}

	@Override
	public List<Order> listByTabletIdAndStatus(Long tabletId, Long statusId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tabletId", tabletId);
		params.put("statusId", statusId);
		return this.listByParams("SELECT o FROM Order o JOIN o.tablet t WHERE t.id = :tabletId AND o.orderStatus.id = :statusId ORDER BY o.id DESC", params);
	}
}
