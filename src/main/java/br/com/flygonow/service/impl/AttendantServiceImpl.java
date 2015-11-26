package br.com.flygonow.service.impl;

import br.com.flygonow.core.security.service.SecurityService;
import br.com.flygonow.dao.*;
import br.com.flygonow.entities.*;
import br.com.flygonow.enums.AlertMessageStatusEnum;
import br.com.flygonow.enums.MediaTypeEnum;
import br.com.flygonow.enums.UserTypeEnum;
import br.com.flygonow.model.AttendantChartAlertByTimeModel;
import br.com.flygonow.model.AttendantChartLastAlertsModel;
import br.com.flygonow.service.AttendantService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AttendantServiceImpl implements AttendantService {

	private static Logger LOGGER = Logger.getLogger(AttendantServiceImpl.class);

	@Autowired
	private SecurityService securityService;

	@Autowired
	private AttendantDao attendantDAO;
	
	@Autowired
	private RoleDao roleDAO;
	
	@Autowired
	private TabletDao tabletDAO;

	@Autowired
	private OrderDao orderDAO;
	
	@Autowired
	private AttendantAlertDao attendantAlertDao;
	
	@Autowired
	private AttendantMediaDao attendantMediaDAO;
	
	@Override
	public void delete(Long id){
		try{
			Attendant findById = attendantDAO.findById(id, null);
			deleteAssociations(findById);
			Attendant expurge = new Attendant();
			expurge.setId(id);
			attendantDAO.update(expurge);
			attendantDAO.delete(expurge);
		}catch(Exception e){
			LOGGER.error("DELETE ASSOCIATIONS ERROR ->> " + e);
		}
	}

	private void deleteAssociations(Attendant root) {
		deleteTabletAssociations(root);
		deleteOrderAssociations(root);
		deleteAttendantAlertAssociations(root);
		deleteRoleAssociations(root);
		deleteMediaAssociations(root);
	}

	private void deleteTabletAssociations(Attendant root) {
		List<Tablet> tablets = tabletDAO.listByAttendantId(root.getId());
		for (Tablet tablet : tablets) {
			tablet.setAttendant(null);
			tabletDAO.update(tablet);
		}
		root.setTablets(null);
	}

	private void deleteOrderAssociations(Attendant root) {
		List<Order> orders = orderDAO.listByAttendantId(root.getId());
		for (Order order : orders) {
			order.setAttendant(null);
			orderDAO.update(order);
		}
		root.setOrders(null);
	}

	private void deleteMediaAssociations(Attendant root) {
		if(root.getPhotoName() != null){
			List<AttendantMedia> medias = 
					attendantMediaDAO.listByAttendantId(root.getId(), MediaTypeEnum.PHOTO.getId());
			for (AttendantMedia attendantMedia : medias) {
				attendantMedia.setAttendant(null);
				attendantMediaDAO.update(attendantMedia);
				attendantMediaDAO.delete(attendantMedia);
			}
		}
	}

	private void deleteAttendantAlertAssociations(Attendant root) {
		List<AttendantAlert> alerts = attendantAlertDao.listByAttendantId(root.getId());
		for (AttendantAlert alert : alerts) {
			alert.setAttendant(null);
			attendantAlertDao.update(alert);
		}
		root.setAttendantAlerts(null);
	}
	
	private void deleteRoleAssociations(Attendant root) {
		List<Role> roles = roleDAO.listByAttendantId(root.getId());
		for (Role role : roles) {
			role.setAttendants(null);
			roleDAO.update(role);
		}
		root.setRoles(null);
	}
	
	@Override
	public void removeMedia(Long id, MediaTypeEnum mediaType) {
		List<AttendantMedia> findBy = 
				attendantMediaDAO.listByAttendantId(id, mediaType.getId());
		for (AttendantMedia attendantMedia : findBy) {
			attendantMedia.setAttendant(null);
			attendantMediaDAO.update(attendantMedia);			
		}
		Attendant findById = attendantDAO.findById(id, null);
		if(MediaTypeEnum.PHOTO.equals(mediaType)){
			findById.setPhotoName(null);
			attendantDAO.update(findById);
		}
	}

	@Override
	public List<AttendantAlert> listPendentAlerts() {
		Attendant loggedUserModel = securityService.getLoggedUserModel();
		List<AttendantAlert> alerts = null;
		if(UserTypeEnum.ADMIN.getId().equals(loggedUserModel.getId())){
			alerts = attendantAlertDao.listAlertsForStatus(AlertMessageStatusEnum.OPENED);
		}else{
			alerts = attendantAlertDao.listAlertsForStatusByAttendantId(AlertMessageStatusEnum.OPENED, loggedUserModel.getId());
		}
		return alerts;
	}

	@Override
	public List<AttendantAlert> listLastAlerts(Integer size) {
		return getLastAlerts(size);
	}

	@Override
	public List<AttendantChartAlertByTimeModel> listLastAlertsByTime(Integer size) {
		List<AttendantChartAlertByTimeModel> timeModelList = new ArrayList<>();
		Map<String, Integer> listLastAlertsByTime = attendantAlertDao.listLastAlertsByTime(size);
		if(listLastAlertsByTime != null && !listLastAlertsByTime.isEmpty()){
			for(String time : listLastAlertsByTime.keySet()){
				AttendantChartAlertByTimeModel byTimeModel = new AttendantChartAlertByTimeModel();
				byTimeModel.setTime(time);
				byTimeModel.setValue(listLastAlertsByTime.get(time));

				timeModelList.add(byTimeModel);
			}
		}
		return timeModelList;
	}

	@Override
	public List<AttendantChartLastAlertsModel> listLastAlertsForChart(Integer size) {
		List<AttendantChartLastAlertsModel> attendantChartLastAlertsModels = new ArrayList<>();
		List<AttendantAlert> lastAlerts = getLastAlerts(size);
		if(lastAlerts != null){
			Map<String, Integer> separateByStatus = new HashMap<>();
			int opened = 0, attended = 0;
			for(AttendantAlert alert : lastAlerts){
				if(AlertMessageStatusEnum.ATTENDED.equals(
						AlertMessageStatusEnum.fromId(
								alert.getStatus().getId()
						)
				)){
					attended++;
				} else if(AlertMessageStatusEnum.OPENED.equals(
						AlertMessageStatusEnum.fromId(
								alert.getStatus().getId()
						)
				)){
					opened++;
				}
			}

			separateByStatus.put(AlertMessageStatusEnum.ATTENDED.getName(), attended);
			separateByStatus.put(AlertMessageStatusEnum.OPENED.getName(), opened);

			for(String status : separateByStatus.keySet()){
				AttendantChartLastAlertsModel attendantChartLastAlertsModel = new AttendantChartLastAlertsModel();
				attendantChartLastAlertsModel.setAlertType(status);
				if(AlertMessageStatusEnum.ATTENDED.getName().equals(status)){
					attendantChartLastAlertsModel.setColor(AlertMessageStatusEnum.ATTENDED.getColor());
				} else {
					attendantChartLastAlertsModel.setColor(AlertMessageStatusEnum.OPENED.getColor());
				}
				attendantChartLastAlertsModel.setValue(separateByStatus.get(status));

				attendantChartLastAlertsModels.add(attendantChartLastAlertsModel);
			}
		}
		return attendantChartLastAlertsModels;
	}

	@Override
	public List<AttendantAlert> readNotification(Long alertId){
		AttendantAlert alert = attendantAlertDao.findById(alertId, null);
		alert.setStatus(
				AlertMessageStatusEnum.fromAlertMessageStatus(
						AlertMessageStatusEnum.ATTENDED
				)
		);
		attendantAlertDao.update(alert);
		return listPendentAlerts();
	}

	@Override
	public List<Attendant> listAll(String strSearch){
		List<Attendant> attendants = new ArrayList<>();
		if(strSearch != null && !"".equals(strSearch)){
			attendants = attendantDAO.listAllByNameWithRoles(strSearch);
		}else{
			attendants = attendantDAO.getAllWithRoles();
		}
		return removeAdminUserFromList(attendants);
	}

	private List<Attendant> removeAdminUserFromList(List<Attendant> attendants){
		List<Attendant> attendantsFiltred = new ArrayList<Attendant>();
		if(attendants != null){
			for(Attendant attendant : attendants){
				if(!UserTypeEnum.ADMIN.getId().equals(attendant.getId())){
					attendantsFiltred.add(attendant);
				}
			}
		}
		return attendantsFiltred;
	}

	private List<AttendantAlert> getLastAlerts(Integer size){
		List<AttendantAlert> openedAlerts = attendantAlertDao.listAlertsForStatus(AlertMessageStatusEnum.OPENED);
		if(openedAlerts != null){
			if(openedAlerts.size() < size){
				List<AttendantAlert> allAlerts = attendantAlertDao.listByParamsPaginated("SELECT a FROM AttendantAlert a ORDER BY a.id DESC", null, size, 0);
				return allAlerts;
			}
		}
		return openedAlerts;
	}
}
