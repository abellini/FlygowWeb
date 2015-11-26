package br.com.flygonow.enums;

import br.com.flygonow.entities.OrderItemStatus;


public enum OrderItemStatusEnum {
	IN_ATTENDANCE(1L, "In Attendance", "In Attendance"),
	READY(2L, "Accept", "Accept"),
	CANCELED(3L, "Cancel", "Cancel")
	;

	private Long id;
	private String name;
	private String description;
	
	OrderItemStatusEnum(Long id, String name, String description) {
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
	
	public OrderItemStatusEnum fromId(Long id){
		if(OrderItemStatusEnum.IN_ATTENDANCE.getId().equals(id)){
			return OrderItemStatusEnum.IN_ATTENDANCE;
		}else if (OrderItemStatusEnum.READY.getId().equals(id)){
			return OrderItemStatusEnum.READY;
		}else if (OrderItemStatusEnum.CANCELED.getId().equals(id)){
			return OrderItemStatusEnum.CANCELED;
		}
		return null;
	}
	
	public static OrderItemStatus fromOrderItemStatus(OrderItemStatusEnum orderItemStatusEnum){
		OrderItemStatus orderItemStatus = new OrderItemStatus();
		orderItemStatus.setId(orderItemStatusEnum.getId());
		orderItemStatus.setName(orderItemStatusEnum.getName());
		orderItemStatus.setDescription(orderItemStatusEnum.getDescription());
		return orderItemStatus;
	}
}
