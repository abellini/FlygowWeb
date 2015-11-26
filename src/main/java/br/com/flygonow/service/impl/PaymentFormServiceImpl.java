package br.com.flygonow.service.impl;

import br.com.flygonow.dao.OrderDao;
import br.com.flygonow.dao.PaymentFormDao;
import br.com.flygonow.entities.Order;
import br.com.flygonow.entities.PaymentForm;
import br.com.flygonow.service.PaymentFormService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class PaymentFormServiceImpl implements PaymentFormService {

	private static Logger LOGGER = Logger.getLogger(PaymentFormServiceImpl.class);
	
	@Autowired
	private PaymentFormDao paymentFormDAO;
	
	@Autowired
	private OrderDao orderDAO;
	
	@Override
	public void delete(Long id){
		try{
			PaymentForm findById = paymentFormDAO.findById(id, null);
			deleteAssociations(findById);
			PaymentForm expurge = new PaymentForm();
			expurge.setId(id);
			paymentFormDAO.update(expurge);
			paymentFormDAO.delete(expurge);
		}catch(Exception e){
			LOGGER.error("DELETE ASSOCIATIONS ERROR ->> " + e);
		}
	}

	private void deleteAssociations(PaymentForm root) {
		deleteOrderAssociations(root);
	}

	private void deleteOrderAssociations(PaymentForm root) {
		List<Order> orders = orderDAO.listByPaymentFormId(root.getId());
		for (Order order : orders) {
			Collection<PaymentForm> payments = paymentFormDAO.listByOrderId(order.getId());
			Collection<PaymentForm> retainAll = new ArrayList<PaymentForm>();
			for (PaymentForm pay : payments) {
				if(!pay.getId().equals(root.getId())){
					retainAll.add(pay);
				}
			}
			payments.retainAll(retainAll);
			order.setPaymentForms(payments);
			orderDAO.update(order);
		}
		root.setOrders(null);
	}
}
