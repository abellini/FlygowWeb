package br.com.flygonow.service.impl;

import br.com.flygonow.core.security.service.SecurityService;
import br.com.flygonow.dao.AccompanimentDao;
import br.com.flygonow.dao.FoodDao;
import br.com.flygonow.dao.OperationalAreaDao;
import br.com.flygonow.dao.RoleDao;
import br.com.flygonow.entities.Accompaniment;
import br.com.flygonow.entities.Food;
import br.com.flygonow.entities.OperationalArea;
import br.com.flygonow.entities.Role;
import br.com.flygonow.enums.RoleTypeEnum;
import br.com.flygonow.service.OperationalAreaService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class OperationalAreaServiceImpl implements OperationalAreaService {

	private static Logger LOGGER = Logger.getLogger(OperationalAreaServiceImpl.class);
	
	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private OperationalAreaDao operationalAreaDAO;
	
	@Autowired
	private RoleDao roleDAO;
	
	@Autowired
	private FoodDao foodDAO;
	
	@Autowired
	private AccompanimentDao accompanimentDAO;
	
	@Override
	public void delete(Long id){
		try{
			OperationalArea findById = operationalAreaDAO.findById(id, null);
			deleteAssociations(findById);
			OperationalArea expurge = new OperationalArea();
			expurge.setId(id);
			operationalAreaDAO.update(expurge);
			operationalAreaDAO.delete(expurge);
		}catch(Exception e){
			LOGGER.error("DELETE ASSOCIATIONS ERROR ->> " + e);
		}
	}

	@Override
	public List<OperationalArea> listOperationalAreasFromLoggerUser(){
		List<OperationalArea> allOpArea = new ArrayList<OperationalArea>(); 
		Collection<Role> roles = securityService.getLoggedUserModel().getRoles();
		for (Role role : roles) {
			Long roleId = role.getId();
			if(RoleTypeEnum.ADMIN.getId().equals(roleId)){
				allOpArea.addAll(operationalAreaDAO.getAll(null));
				break;
			} else {
				allOpArea.addAll(operationalAreaDAO.listByRoleId(roleId));
			}
		}
		return allOpArea;
	}	
	
	private void deleteAssociations(OperationalArea root) {
		deleteRoleAssociations(root);
		deleteFoodAssociations(root);
		deleteAccompanimentAssociations(root);
	}

	private void deleteRoleAssociations(OperationalArea root) {
		List<Role> roles = roleDAO.listByOperationalAreaId(root.getId());
		for (Role role : roles) {
			Collection<OperationalArea> areas = operationalAreaDAO.listByRoleId(role.getId());
			Collection<OperationalArea> retainAll = new ArrayList<OperationalArea>();
			for (OperationalArea area : areas) {
				if(!area.getId().equals(root.getId())){
					retainAll.add(area);
				}
			}
			areas.retainAll(retainAll);
			role.setOperationalAreas(areas);
			roleDAO.update(role);
		}
		root.setRoles(null);
	}

	private void deleteFoodAssociations(OperationalArea root) {
		List<Food> foods = foodDAO.listByOperationalAreaId(root.getId());
		for (Food food : foods) {
			food.setOperationalArea(null);
			foodDAO.update(food);
		}
		root.setFoods(null);
	}

	private void deleteAccompanimentAssociations(OperationalArea root) {
		List<Accompaniment> accs = accompanimentDAO.listByOperationalAreaId(root.getId());
		for (Accompaniment acc : accs) {
			acc.setOperationalArea(null);
			accompanimentDAO.update(acc);
		}
		root.setAccompaniments(null);
	}
}
