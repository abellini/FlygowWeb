package br.com.flygonow.enums;

import br.com.flygonow.entities.OrderStatus;


public enum OrderStatusEnum {
	ORDER_OPEN(1L, "Open", "When Order is Open"),
	ORDER_CLOSED(2L, "Closed", "When Order is Closed"),
	ORDER_FINALIZED(3L, "Finalized", "When Order is Finalized"),
	ORDER_CANCELED(4L, "Canceled", "When Order is Canceled"),
	;

	private Long id;
	private String name;
	private String description;
	
	OrderStatusEnum(Long id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
	
	public OrderStatusEnum fromId(Long id){
		if(OrderStatusEnum.ORDER_OPEN.getId().equals(id)){
			return OrderStatusEnum.ORDER_OPEN;
		}else if (OrderStatusEnum.ORDER_CLOSED.getId().equals(id)){
			return OrderStatusEnum.ORDER_CLOSED;
		}else if (OrderStatusEnum.ORDER_FINALIZED.getId().equals(id)){
			return OrderStatusEnum.ORDER_FINALIZED;
		}
		return null;
	}
	
	public static OrderStatus fromOrderStatus(OrderStatusEnum orderStatusEnum){
		OrderStatus orderStatus = new OrderStatus();
		orderStatus.setId(orderStatusEnum.getId());
		orderStatus.setName(orderStatusEnum.getName());
		orderStatus.setDescription(orderStatusEnum.getDescription());
		return orderStatus;
	}
}
