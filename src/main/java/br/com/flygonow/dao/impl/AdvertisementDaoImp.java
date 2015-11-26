package br.com.flygonow.dao.impl;

import br.com.flygonow.dao.AdvertisementDao;
import br.com.flygonow.entities.Advertisement;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.*;

@Repository
public class AdvertisementDaoImp extends GenericDaoImp<Advertisement, Long>
		implements AdvertisementDao {

	@Override
	public String getVideoNameByAdvertisement(Long id) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Advertisement> criteria = criteriaBuilder.createQuery(Advertisement.class);
		Root<Advertisement> root = criteria.from( Advertisement.class );
		criteria.multiselect( root.get("videoName"));
		criteria.where( criteriaBuilder.equal( root.get( "id" ), id ) );
		try{
			Advertisement resp = getEntityManager().createQuery( criteria ).getSingleResult();
			return resp.getVideoName();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public String getPhotoNameByAdvertisement(Long id) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Advertisement> criteria = criteriaBuilder.createQuery(Advertisement.class);
		Root<Advertisement> root = criteria.from( Advertisement.class );
		criteria.multiselect( root.get("photoName"));
		criteria.where( criteriaBuilder.equal( root.get( "id" ), id ) );
		try{
			Advertisement resp = getEntityManager().createQuery( criteria ).getSingleResult();
			return resp.getVideoName();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Collection<Advertisement> listByTabletId(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return this.listByParams("SELECT a FROM Advertisement a LEFT JOIN a.tablets t WHERE t.id = :id", params);
	}

	@Override
	public List<Advertisement> getAllActives() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("isActive", true);
		params.put("today", new java.sql.Date(new Date().getTime()));
		return this.listByParams("SELECT a FROM Advertisement a WHERE a.isActive = :isActive AND a.inicialDate <= :today AND a.finalDate >= :today", params);
	}

}
