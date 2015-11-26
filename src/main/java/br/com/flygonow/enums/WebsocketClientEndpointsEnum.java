package br.com.flygonow.enums;


public enum WebsocketClientEndpointsEnum {
	ATTENDANT_ALERTS("/clients/receiveNewAlert"),
	NEW_ORDER("/clients/newOrder")
	;

	private String path;

	WebsocketClientEndpointsEnum(String path) {
		this.path = path;
	}


	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
