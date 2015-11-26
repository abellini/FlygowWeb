package br.com.flygonow.dao;

import br.com.flygonow.entities.AttendantAlert;
import br.com.flygonow.enums.AlertMessageStatusEnum;

import java.util.List;
import java.util.Map;

public interface AttendantAlertDao  extends GenericDao<AttendantAlert, Long>{

	List<AttendantAlert> listByAttendantId(Long id);

	List<AttendantAlert> listAlertsForStatusByAttendantId(AlertMessageStatusEnum status, Long id);

	List<AttendantAlert> listAlertsForStatus(AlertMessageStatusEnum status);

	Map<String, Integer> listLastAlertsByTime(Integer size);
}
