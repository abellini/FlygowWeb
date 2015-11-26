package br.com.flygonow.enums;



public enum FetchTypeEnum {
	INNER("INNER"),
	LEFT("LEFT")
	;

	private String name;
	
	FetchTypeEnum(String name) {
		this.name = name;
	}
	
	
	public String getName() {
		return name;
	}

	public static FetchTypeEnum fromName(String name){
		if(FetchTypeEnum.INNER.getName().equalsIgnoreCase((name))){
			return FetchTypeEnum.INNER;
		}
		if(FetchTypeEnum.LEFT.getName().equalsIgnoreCase((name))){
			return FetchTypeEnum.LEFT;
		}
		return null;
	}
}
