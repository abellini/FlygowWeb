package br.com.flygonow.service;

import br.com.flygonow.entities.Attendant;
import br.com.flygonow.entities.AttendantAlert;
import br.com.flygonow.enums.MediaTypeEnum;
import br.com.flygonow.model.AttendantChartAlertByTimeModel;
import br.com.flygonow.model.AttendantChartLastAlertsModel;

import java.util.List;

public interface AttendantService {

	void delete(Long id);

	void removeMedia(Long id, MediaTypeEnum mediaType);

	List<AttendantAlert> listPendentAlerts();

	List<AttendantAlert> listLastAlerts(Integer size);

	List<AttendantChartAlertByTimeModel> listLastAlertsByTime(Integer size);

	List<AttendantChartLastAlertsModel> listLastAlertsForChart(Integer size);

	List<AttendantAlert> readNotification(Long alertId);

	List<Attendant> listAll(String strSearch);
}
