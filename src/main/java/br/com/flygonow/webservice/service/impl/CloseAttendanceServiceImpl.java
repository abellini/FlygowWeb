package br.com.flygonow.webservice.service.impl;

import br.com.flygonow.dao.OrderDao;
import br.com.flygonow.dao.OrderItemDao;
import br.com.flygonow.dao.PaymentFormDao;
import br.com.flygonow.dao.TabletDao;
import br.com.flygonow.entities.Order;
import br.com.flygonow.entities.OrderItem;
import br.com.flygonow.entities.PaymentForm;
import br.com.flygonow.entities.Tablet;
import br.com.flygonow.enums.OrderItemStatusEnum;
import br.com.flygonow.enums.OrderStatusEnum;
import br.com.flygonow.enums.TabletStatusEnum;
import br.com.flygonow.service.OrderService;
import br.com.flygonow.service.TabletService;
import br.com.flygonow.webservice.service.CallAttendantService;
import br.com.flygonow.webservice.service.CloseAttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CloseAttendanceServiceImpl implements CloseAttendanceService {

	@Autowired
	private TabletService tabletService;
	
	@Autowired
	private TabletDao tabletDAO;
	
	@Autowired
	private PaymentFormDao paymentFormDAO;
	
	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderItemDao orderItemDao;

	@Autowired
	private OrderDao orderDAO;
	
	@Autowired
	private CallAttendantService callAttendantService;
	
	@Override
	public void closeAttendance(Long tabletNumber, Long alertTypeId,
			List<Long> paymentFormIds) {
		
		Tablet tablet = tabletDAO.getByNumber(tabletNumber.shortValue());
		callAttendantService.callAttendant(
				tablet.getNumber(), 
				tablet.getAttendant().getId(), 
				alertTypeId, 
				null);
		List<PaymentForm> payments = new ArrayList<PaymentForm>();
		for(Long paymentFormId : paymentFormIds){
			PaymentForm paymentForm = paymentFormDAO.findById(paymentFormId, null);
			if(paymentForm != null){
				payments.add(paymentForm);
			}
		}
		Order currentOrderByTablet = orderService.getCurrentOrderByTablet(tablet.getId());
		if(currentOrderByTablet != null){
			List<OrderItem> orderItems = orderItemDao.listByOrderAndItemStatus(currentOrderByTablet.getId(), OrderItemStatusEnum.IN_ATTENDANCE);
			if(orderItems != null){
				for(OrderItem orderItem : orderItems){
					orderItem.setOrderItemStatus(OrderItemStatusEnum.fromOrderItemStatus(OrderItemStatusEnum.CANCELED));
					orderItemDao.update(orderItem);
				}
			}
			currentOrderByTablet.setPaymentForms(payments);
			currentOrderByTablet.setOrderStatus(OrderStatusEnum.fromOrderStatus(OrderStatusEnum.ORDER_CLOSED));
			orderDAO.update(currentOrderByTablet);			
		}
		tabletService.changeTabletStatus(tablet.getNumber(), TabletStatusEnum.AVALIABLE.getId());
	}
}
