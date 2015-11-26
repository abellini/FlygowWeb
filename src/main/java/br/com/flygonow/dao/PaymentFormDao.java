package br.com.flygonow.dao;

import br.com.flygonow.entities.PaymentForm;

import java.util.Collection;

public interface PaymentFormDao extends GenericDao<PaymentForm, Long> {

	Collection<PaymentForm> listByOrderId(Long id);

}
