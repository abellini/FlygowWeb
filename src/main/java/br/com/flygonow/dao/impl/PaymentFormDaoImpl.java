package br.com.flygonow.dao.impl;

import br.com.flygonow.dao.PaymentFormDao;
import br.com.flygonow.entities.PaymentForm;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class PaymentFormDaoImpl extends GenericDaoImp<PaymentForm, Long> implements PaymentFormDao{

	@Override
	public Collection<PaymentForm> listByOrderId(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return this.listByParams("SELECT pf FROM PaymentForm pf LEFT JOIN pf.orders o WHERE o.id = :id", params);
	}
}

