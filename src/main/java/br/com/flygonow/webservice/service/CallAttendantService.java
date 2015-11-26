package br.com.flygonow.webservice.service;



public interface CallAttendantService {
	
	void callAttendant(Short tabletNumber, Long attendantId, Long alertTypeId, Long deviceId);
}
