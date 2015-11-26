package br.com.flygonow.service.impl;

import br.com.flygonow.dao.AdvertisementDao;
import br.com.flygonow.dao.OrderDao;
import br.com.flygonow.dao.OrderItemDao;
import br.com.flygonow.dao.TabletDao;
import br.com.flygonow.entities.Advertisement;
import br.com.flygonow.entities.Order;
import br.com.flygonow.entities.Tablet;
import br.com.flygonow.enums.TabletStatusEnum;
import br.com.flygonow.service.TabletService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class TabletServiceImpl implements TabletService {

	private static Logger LOGGER = Logger.getLogger(TabletServiceImpl.class);
	
	@Autowired
	private TabletDao tabletDAO;
	
	@Autowired
	private OrderDao orderDAO;
	
	@Autowired
	private OrderItemDao orderItemDAO;
	
	@Autowired
	private AdvertisementDao advertisementDAO;
	
	@Override
	public void delete(Long id){
		try{
			Tablet findById = tabletDAO.findById(id, null);
			deleteAssociations(findById);
			Tablet expurge = new Tablet();
			expurge.setId(id);
			tabletDAO.update(expurge);
			tabletDAO.delete(expurge);
		}catch(Exception e){
			LOGGER.error("DELETE ASSOCIATIONS ERROR ->> " + e);
		}
	}
	
	@Override
	public void changeTabletStatus(Short tabletNumber, Long statusId) {
		Tablet tablet = tabletDAO.getByNumber(tabletNumber.shortValue());
		TabletStatusEnum status = TabletStatusEnum.fromId(statusId);
		tablet.setServiceStatus(TabletStatusEnum.convertFromTabletStatus(status.getId()));
		tabletDAO.update(tablet);
	}

	private void deleteAssociations(Tablet root) {
		deleteAdvertisementAssociations(root);
		deleteOrderAssociations(root);
	}

	private void deleteAdvertisementAssociations(Tablet root) {
		Collection<Advertisement> advertisements = advertisementDAO.listByTabletId(root.getId());
		for (Advertisement advertisement : advertisements) {
			advertisement.setTablets(null);
			advertisementDAO.update(advertisement);
		}
		root.setAdvertisements(null);
	}

	private void deleteOrderAssociations(Tablet root) {
		List<Order> orders = orderDAO.listByTabletId(root.getId());
		for (Order order : orders) {
			order.setTablet(null);
			orderDAO.update(order);
		}
		root.setOrders(null);
	}
}
