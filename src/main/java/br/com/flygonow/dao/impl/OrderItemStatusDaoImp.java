package br.com.flygonow.dao.impl;

import br.com.flygonow.dao.OrderItemStatusDao;
import br.com.flygonow.entities.OrderItemStatus;
import org.springframework.stereotype.Repository;

@Repository
public class OrderItemStatusDaoImp  extends GenericDaoImp<OrderItemStatus, Long> implements OrderItemStatusDao{


}
