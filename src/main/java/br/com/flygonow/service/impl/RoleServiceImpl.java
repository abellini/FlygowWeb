package br.com.flygonow.service.impl;

import br.com.flygonow.dao.*;
import br.com.flygonow.entities.*;
import br.com.flygonow.service.RoleService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

	private static Logger LOGGER = Logger.getLogger(RoleServiceImpl.class);
	
	@Autowired
	private ClientDao clientDAO;
	
	@Autowired
	private RoleDao roleDAO;
	
	@Autowired
	private AttendantDao attendantDAO;
	
	@Autowired
	private ModuleDao moduleDAO;
	
	@Autowired
	private OperationalAreaDao operationalAreaDAO;
	
	@Override
	public void delete(Long id){
		try{
			Role findById = roleDAO.findById(id, null);
			deleteAssociations(findById);
			Role expurge = new Role();
			expurge.setId(id);
			roleDAO.update(expurge);
			roleDAO.delete(expurge);
		}catch(Exception e){
			LOGGER.error("DELETE ASSOCIATIONS ERROR ->> " + e);
		}
	}

	private void deleteAssociations(Role root) {
		deleteClientAssociations(root);
		deleteAttendantAssociations(root);
		deleteModuleAssociations(root);
		deleteOperationalAreaAssociations(root);
	}

	private void deleteClientAssociations(Role root) {
		List<Client> clients = clientDAO.listByRoleId(root.getId());
		for (Client client : clients) {
			client.setRoles(null);
			clientDAO.update(client);
		}
		root.setClients(null);
	}

	private void deleteAttendantAssociations(Role root) {
		List<Attendant> attendants = attendantDAO.listByRoleId(root.getId());
		for (Attendant attendant : attendants) {
			Collection<Role> roles = roleDAO.listByAttendantId(attendant.getId());
			Collection<Role> retainAll = new ArrayList<Role>();
			for (Role role : roles) {
				if(!role.getId().equals(root.getId())){
					retainAll.add(role);
				}
			}
			roles.retainAll(retainAll);
			attendant.setRoles(new HashSet<Role>(roles));
			attendantDAO.update(attendant);
		}
		root.setAttendants(null);
	}

	private void deleteModuleAssociations(Role root) {
		Collection<Module> modules = moduleDAO.listByRoleId(root.getId());
		for (Module module : modules) {
			module.setRoles(null);
			moduleDAO.update(module);
		}
		root.setModules(null);
	}
	
	private void deleteOperationalAreaAssociations(Role root) {
		List<OperationalArea> opAreas = operationalAreaDAO.listByRoleId(root.getId());
		for (OperationalArea opArea : opAreas) {
			opArea.setRoles(null);
			operationalAreaDAO.update(opArea);
		}
		root.setOperationalAreas(null);
	}
}
