package br.com.flygonow.websocket.util;

import br.com.flygonow.entities.AttendantAlert;
import br.com.flygonow.enums.AlertMessageStatusEnum;
import br.com.flygonow.enums.AlertMessageTypeEnum;
import net.sf.json.JSONObject;

public class JSONWebsocketView {

	public static String fromAttendantAlert(AttendantAlert alert) {

		try{
			JSONObject obj = new JSONObject();
			obj.put("id", alert.getId());
			obj.put("tabletNumber", alert.getTabletNumber());
			obj.put("alertHour", alert.getAlertHour());
			obj.put("attendantId", alert.getAttendant().getId());
			obj.put("attendantName", alert.getAttendant().getName());
			obj.put("type", AlertMessageTypeEnum.fromId(alert.getType().getId()));
			obj.put("alertStatus", AlertMessageStatusEnum.fromId(alert.getStatus().getId()));
			return obj.toString();
		}catch(Exception e){
			System.out.println("Persistent Bag ERROR ->> " + e.getMessage());
		}
		return null;
	}
}
