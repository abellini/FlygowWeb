package br.com.flygonow.dao;

import br.com.flygonow.entities.Attendant;

import java.util.List;

public interface AttendantDao extends GenericDao<Attendant, Long> {
	
	Attendant findByLogin(String userName);

	List<Attendant> getAllWithRoles();

	List<Attendant> listAllByNameWithRoles(String strSearch);

	String getPhotoNameByAttendant(Long id);

	List<Attendant> listByRoleId(Long id);

}