package br.com.flygonow.dao.impl;

import br.com.flygonow.dao.AttendantAlertDao;
import br.com.flygonow.entities.AttendantAlert;
import br.com.flygonow.enums.AlertMessageStatusEnum;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class AttendantAlertDaoImpl extends GenericDaoImp<AttendantAlert, Long> implements AttendantAlertDao {

	@Override
	public List<AttendantAlert> listByAttendantId(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return this.listByParams("SELECT al FROM AttendantAlert al LEFT JOIN FETCH al.attendant a WHERE a.id = :id", params);
	}

	@Override
	public List<AttendantAlert> listAlertsForStatusByAttendantId(AlertMessageStatusEnum status, Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("status", status.getId());
		return this.listByParams("SELECT al FROM AttendantAlert al JOIN FETCH al.attendant a WHERE a.id = :id AND al.status.id = :status ORDER BY al.alertHour DESC", params);
	}

	@Override
	public List<AttendantAlert> listAlertsForStatus(AlertMessageStatusEnum status) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", status.getId());
		return this.listByParams("SELECT al FROM AttendantAlert al LEFT JOIN FETCH al.attendant a WHERE al.status.id = :status ORDER BY al.alertHour DESC", params);
	}

	@Override
	public List<Object[]> listLastAlertsByTime(Integer size){
		Map<String, Integer> result = new TreeMap<>();
		List resultList = this.getEntityManager().createNativeQuery(
				"SELECT to_char(al.alerthour, 'dd/MM/yyyy') as hr, " +
						"count(al.alerthour) as val " +
						"FROM attendantalert al " +
						"WHERE al.alerthour BETWEEN " +
						"(now() - INTERVAL '" + size + " days') AND now() " +
						"GROUP BY hr " +
						"ORDER BY CAST(to_char(al.alerthour, 'dd/MM/yyyy') as DATE)").getResultList();

		return resultList;
	}
}