package br.com.flygonow.enums;

import br.com.flygonow.entities.TabletStatus;


public enum TabletStatusEnum {
	IN_ATTENDANCE(1L, "In Attendance", "In Attendance"),
	AVALIABLE(2L, "Available", "Available"),
	UNAVALIABLE(3L, "Unavailable", "Unavailable")
	;

	private Long id;
	private String name;
	private String description;
	
	TabletStatusEnum(Long id, String name, String description) {
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
	
	public static TabletStatusEnum fromId(Long id){
		if(TabletStatusEnum.IN_ATTENDANCE.getId().equals(id)){
			return TabletStatusEnum.IN_ATTENDANCE;
		}else if (TabletStatusEnum.AVALIABLE.getId().equals(id)){
			return TabletStatusEnum.AVALIABLE;
		}else if (TabletStatusEnum.UNAVALIABLE.getId().equals(id)){
			return TabletStatusEnum.UNAVALIABLE;
		}
		return null;
	}
	
	public static TabletStatus convertFromTabletStatus(Long id){
		TabletStatus tabletStatus = null;
		if(TabletStatusEnum.IN_ATTENDANCE.getId().equals(id)){
			tabletStatus = fromTabletStatus(TabletStatusEnum.IN_ATTENDANCE);
		}else if(TabletStatusEnum.AVALIABLE.getId().equals(id)){
			tabletStatus = fromTabletStatus(TabletStatusEnum.AVALIABLE);
		}else if(TabletStatusEnum.UNAVALIABLE.getId().equals(id)){
			tabletStatus = fromTabletStatus(TabletStatusEnum.UNAVALIABLE);
		}
		return tabletStatus;
	}
	
	public static TabletStatus fromTabletStatus(TabletStatusEnum tabletStatusEnum){
		TabletStatus tabletStatus = new TabletStatus();
		tabletStatus.setId(tabletStatusEnum.getId());
		tabletStatus.setName(tabletStatusEnum.getName());
		tabletStatus.setDescription(tabletStatusEnum.getDescription());
		return tabletStatus;
	}
}
