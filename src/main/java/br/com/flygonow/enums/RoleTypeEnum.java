package br.com.flygonow.enums;

import br.com.flygonow.entities.Role;


public enum RoleTypeEnum {
	AUTHENTICATED(1L, "Authenticated", "Authenticated Role"),
	ADMIN(2L, "Admin", "Admin Role")
	;

	private Long id;
	private String name;
	private String description;
	
	RoleTypeEnum(Long id, String name, String description) {
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
	
	public RoleTypeEnum fromId(Long id){
		if(RoleTypeEnum.AUTHENTICATED.getId().equals(id)){
			return RoleTypeEnum.AUTHENTICATED;
		}
		if(RoleTypeEnum.ADMIN.getId().equals(id)){
			return RoleTypeEnum.ADMIN;
		}
		return null;
	}
	
	public static Role convertFromRoleType(Long id){
		Role role = null;
		if(RoleTypeEnum.AUTHENTICATED.getId().equals(id)){
			role = fromRoleType(RoleTypeEnum.AUTHENTICATED);
		}
		if(RoleTypeEnum.ADMIN.getId().equals(id)){
			role = fromRoleType(RoleTypeEnum.ADMIN);
		}
		return role;
	}
	
	public static Role fromRoleType(RoleTypeEnum roleType){
		Role role = new Role();
		role.setId(roleType.getId());
		role.setName(roleType.getName());
		role.setDescription(roleType.getDescription());
		return role;
	}
}
