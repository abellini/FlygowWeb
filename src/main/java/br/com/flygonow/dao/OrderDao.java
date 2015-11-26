package br.com.flygonow.dao;

import br.com.flygonow.entities.Order;
import org.hibernate.LazyInitializationException;

import java.util.List;

public interface OrderDao  extends GenericDao<Order, Long>{

	List<Order> listByAttendantId(Long id);

	List<Order> listByTabletId(Long id);
	
	List<Order> listByTabletIdAndStatus(Long id, Long statusId);

	List<Order> listByPaymentFormId(Long id);
	
	Order getCurrentOrderByTablet(Long tabletId) throws LazyInitializationException;

}
