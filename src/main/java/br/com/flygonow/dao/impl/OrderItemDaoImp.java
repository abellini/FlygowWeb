package br.com.flygonow.dao.impl;

import br.com.flygonow.dao.OrderItemDao;
import br.com.flygonow.entities.OperationalArea;
import br.com.flygonow.entities.OrderItem;
import br.com.flygonow.enums.OrderItemStatusEnum;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OrderItemDaoImp  extends GenericDaoImp<OrderItem, Long> implements OrderItemDao {

	private static Logger LOGGER = Logger.getLogger(OrderItemDaoImp.class);
	
	@Override
	public List<OrderItem> listByAccompanimentId(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return this.listByParams("SELECT oi FROM OrderItem oi LEFT JOIN oi.accompaniments a WHERE a.id = :id", params);
	}

	@Override
	public List<OrderItem> listByFoodId(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return this.listByParams("SELECT oi FROM OrderItem oi LEFT JOIN oi.food f WHERE f.id = :id", params);
	}

	@Override
	public List<OrderItem> listByPromotionId(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return this.listByParams("SELECT oi FROM OrderItem oi LEFT JOIN oi.promotions p WHERE p.id = :id", params);
	}

	@Override
	public List<OrderItem> listAllFromDateUserStatus(String strSearch, Date dateIni, Date dateEnd,
			List<OperationalArea> listOperationalAreasFromLoggedUser,
			OrderItemStatusEnum... orderItemStatus) {
		List<OrderItem> orderItems = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dateEnd", new java.sql.Date(dateEnd.getTime()));
		if(dateIni == null){
			Calendar cal = new GregorianCalendar();
			cal.setTime(dateEnd);
			cal.add(Calendar.DAY_OF_MONTH, -1);
			params.put("dateIni", new java.sql.Date(cal.getTime().getTime()));
		} else {
			params.put("dateIni", new java.sql.Date(dateIni.getTime()));
		}

		
		String searchFragment = "";
		String operationalAreaFragment = "";
		String orderItemStatusFragment = "";
		if(strSearch != null && !"".equals(strSearch)){
			params.put("strSearch", Short.parseShort(strSearch));
			searchFragment = " AND t.number = :strSearch ORDER BY oi.iniOrderDate, oi.iniOrderHour DESC";
		}
		List<Long> operationalAreaIds = new ArrayList<Long>();
		for (OperationalArea operationalArea : listOperationalAreasFromLoggedUser) {
			operationalAreaIds.add(operationalArea.getId());
		}
		if(!operationalAreaIds.isEmpty()){
			params.put("operationalAreaIds", operationalAreaIds);
			operationalAreaFragment = " AND (f.operationalArea.id IN (:operationalAreaIds) OR fs.operationalArea.id IN (:operationalAreaIds))";
		}
		List<Long> orderItemStatusIds = new ArrayList<Long>();
		for(OrderItemStatusEnum orderItemStt : orderItemStatus){
			orderItemStatusIds.add(orderItemStt.getId());
		}
		if(!orderItemStatusIds.isEmpty()){
			params.put("orderItemStatusIds", orderItemStatusIds);
			orderItemStatusFragment = " AND stt.id IN (:orderItemStatusIds)";
		}
		String query = 
				"SELECT oi FROM OrderItem oi " +
				"LEFT JOIN FETCH oi.food f " +
				"LEFT JOIN FETCH oi.promotion p " +
				"LEFT JOIN FETCH p.foods fs " +
				"JOIN FETCH oi.order o " +
				"JOIN FETCH o.tablet t " + 
				"LEFT JOIN FETCH oi.accompaniments acc " + 
				"JOIN FETCH oi.orderItemStatus stt " +
				"WHERE oi.iniOrderDate BETWEEN :dateIni AND :dateEnd " +
				operationalAreaFragment + orderItemStatusFragment + searchFragment;
		try{
			orderItems = this.listByParams(query, params);
		}catch(Exception e){
			LOGGER.error("LIST ALL ORDER ITEMS FROM DATE USER STATUS" + e.getMessage());
		}
		return orderItems;
	}

	@Override
	public List<OrderItem> listByOrder(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return this.listByParams("SELECT oi FROM OrderItem oi INNER JOIN oi.order o WHERE o.id = :id", params);
	}

	@Override
	public List<OrderItem> listByOrderAndItemStatus(Long id, OrderItemStatusEnum orderItemStatus) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("status", orderItemStatus.getId());
		return this.listByParams("SELECT oi FROM OrderItem oi INNER JOIN oi.order o INNER JOIN oi.orderItemStatus ois WHERE o.id = :id AND ois.id = :status", params);
	}
}
