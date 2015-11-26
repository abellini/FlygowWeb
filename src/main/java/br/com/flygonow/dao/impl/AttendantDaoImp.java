package br.com.flygonow.dao.impl;

import br.com.flygonow.dao.AttendantDao;
import br.com.flygonow.dao.RoleDao;
import br.com.flygonow.entities.Attendant;
import br.com.flygonow.enums.FetchTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AttendantDaoImp extends GenericDaoImp<Attendant, Long> implements AttendantDao {

	@Autowired
	private RoleDao roleDao;
	
	@Override
	public Attendant findByLogin(String userName) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("login", userName);
		Attendant user = null;
		try{
			user = this.getByParams("SELECT obj FROM Attendant obj " +
					"JOIN FETCH obj.roles " +
					"WHERE obj.login = :login", params);
		}catch(Exception e){
			System.out.println("Error ->> " + e);
		}
		return user;
	}

	@Override
	public List<Attendant> getAllWithRoles() {
		List<Attendant> attendants = null;
		try{
			attendants = this.getAll(FetchTypeEnum.LEFT.getName(), "roles");
		}catch(Exception e){
			System.out.println("Error ->> " + e);
		}
		return attendants;
	}

	@Override
	public List<Attendant> listAllByNameWithRoles(String strSearch) {
		List<Attendant> attendants = null;
		try{
			attendants = this.listByName(strSearch, FetchTypeEnum.LEFT.getName(), "roles");
		}catch(Exception e){
			System.out.println("Error ->> " + e);
		}
		return attendants;
	}

	@Override
	public String getPhotoNameByAttendant(Long id) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Attendant> criteria = criteriaBuilder.createQuery(Attendant.class);
		Root<Attendant> attendantRoot = criteria.from( Attendant.class );
		criteria.multiselect( attendantRoot.get("photoName"));
		criteria.where( criteriaBuilder.equal( attendantRoot.get( "id" ), id ) );
		try{
			Attendant att = getEntityManager().createQuery( criteria ).getSingleResult();
			return att.getPhotoName();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<Attendant> listByRoleId(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return this.listByParams("SELECT a FROM Attendant a LEFT JOIN a.roles r WHERE r.id = :id", params);
	}
}
