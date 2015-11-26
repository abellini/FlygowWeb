package br.com.flygonow.webservice.service.impl;

import br.com.flygonow.dao.AttendantAlertDao;
import br.com.flygonow.dao.AttendantDao;
import br.com.flygonow.dao.DeviceDao;
import br.com.flygonow.entities.*;
import br.com.flygonow.enums.AlertMessageStatusEnum;
import br.com.flygonow.enums.AlertMessageTypeEnum;
import br.com.flygonow.webservice.service.CallAttendantService;
import br.com.flygonow.websocket.service.NotifyWebClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CallAttendantServiceImpl implements CallAttendantService{

	@Autowired
	private AttendantAlertDao attendantAlertDao;
	
	@Autowired
	private AttendantDao attendantDao;
	
	@Autowired
	private DeviceDao deviceDao;

	@Autowired
	private NotifyWebClientsService notifyWebClientsService;

	@Override
	public void callAttendant(Short tabletNumber, Long attendantId, Long alertTypeId, Long deviceId) {
		AlertMessageStatusEnum alertMessageStatus = AlertMessageStatusEnum.OPENED; 
		AlertMessageTypeEnum alertMessageType = AlertMessageTypeEnum.fromId(alertTypeId);
		if(alertMessageType == null){
			alertMessageType = AlertMessageTypeEnum.TO_ATTENDANCE; 
		}
		Attendant attendant = attendantDao.findById(attendantId, null);
		Device device = deviceDao.findById(deviceId, null);
		
		AttendantAlert alert = new AttendantAlert();		
		alert.setAttendant(attendant);
		alert.setDevice(device);
		alert.setTabletNumber(tabletNumber);
		
		AlertMessageType type = new AlertMessageType();
		type.setId(alertMessageType.getId());
		alert.setType(type);
		
		AlertMessageStatus status = new AlertMessageStatus();
		status.setId(alertMessageStatus.getId());
		alert.setStatus(status);
		
		alert.setAlertHour(new Date());
		attendantAlertDao.save(alert);
		notifyWebClientsService.sendWebAlertToAttendant(alert);
	}
}
