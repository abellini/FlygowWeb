package br.com.flygonow.enums;

import br.com.flygonow.entities.Role;


public enum UserTypeEnum {
	ADMIN(1L, "Admin", "Admin User")
	;

	private Long id;
	private String name;
	private String description;
	
	UserTypeEnum(Long id, String name, String description) {
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
	
	public UserTypeEnum fromId(Long id){
		if(UserTypeEnum.ADMIN.getId().equals(id)){
			return UserTypeEnum.ADMIN;
		}
		return null;
	}
	
	public static Role convertFromRoleType(Long id){
		Role role = null;
		if(UserTypeEnum.ADMIN.getId().equals(id)){
			role = fromRoleType(UserTypeEnum.ADMIN);
		}
		return role;
	}
	
	public static Role fromRoleType(UserTypeEnum roleType){
		Role role = new Role();
		role.setId(roleType.getId());
		role.setName(roleType.getName());
		role.setDescription(roleType.getDescription());
		return role;
	}
}
