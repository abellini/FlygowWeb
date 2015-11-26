package br.com.flygonow.enums;


import br.com.flygonow.entities.AlertMessageStatus;
import br.com.flygonow.entities.OrderItemStatus;

public enum AlertMessageStatusEnum {
	OPENED(1L, "OPENED", "Alert opened", "#E2231A"),
	ATTENDED(2L, "ATTENDED", "Alert attended", "#179a47")
	;

	private Long id;
	private String name;
	private String description;
	private String color;
	
	AlertMessageStatusEnum(Long id, String name, String description, String color) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.color = color;
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

	public String getColor() {
		return color;
	}

	public static AlertMessageStatusEnum fromId(Long id){
		for(AlertMessageStatusEnum type : values()){
			if(type.getId().equals(id)){
				return type;
			}
		}
		return null;
	}

	public static AlertMessageStatus fromAlertMessageStatus(AlertMessageStatusEnum alertMessageStatusEnum){
		AlertMessageStatus alertMessageStatus = new AlertMessageStatus();
		alertMessageStatus.setId(alertMessageStatusEnum.getId());
		alertMessageStatus.setName(alertMessageStatusEnum.getName());
		alertMessageStatus.setDescription(alertMessageStatusEnum.getDescription());
		return alertMessageStatus;
	}
}
