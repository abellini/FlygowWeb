package br.com.flygonow.webservice.service;

import java.util.List;

public interface CloseAttendanceService {

	void closeAttendance(Long tabletNumber, Long alertTypeId,
			List<Long> paymentFormIds);

}
