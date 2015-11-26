package br.com.flygonow.dao.impl;

import br.com.flygonow.dao.AttendantMediaDao;
import br.com.flygonow.entities.Attendant;
import br.com.flygonow.entities.AttendantMedia;
import br.com.flygonow.enums.MediaTypeEnum;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AttendantMediaDaoImp extends GenericDaoImp<AttendantMedia, Long>
		implements AttendantMediaDao {
	
	
	@Override
	public byte[] getPhoto(Long promoId) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<AttendantMedia> criteria = criteriaBuilder.createQuery(AttendantMedia.class);
		Root<AttendantMedia> root = criteria.from( AttendantMedia.class );
		criteria.multiselect( root.get("media"));
		Join<AttendantMedia, Attendant> join = root.join("attendant");
		Predicate predicate = criteriaBuilder.disjunction();
	    predicate = criteriaBuilder.or(predicate, criteriaBuilder.equal(join.get( "id" ), promoId) );
	    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get( "type" ), MediaTypeEnum.PHOTO.getId()) );
		criteria.where(predicate);
		try{
			List<AttendantMedia> list = getEntityManager().createQuery( criteria ).getResultList();
			return list.get(list.size()-1).getMedia();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public AttendantMedia findByAttendant(Long attId, Byte mediaType) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", attId);
		params.put("type", mediaType);
		List<AttendantMedia> list = this.listByParams("SELECT pm from AttendantMedia pm JOIN FETCH pm.attendant " +
				"WHERE pm.attendant.id = :id and pm.type = :type", params);
		return list.get(list.size()-1);
	}

	@Override
	public List<AttendantMedia> listByAttendantId(Long attId, Byte mediaType) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("attId", attId);
		params.put("type", mediaType);
		List<AttendantMedia> medias = this.listByParams("SELECT am from AttendantMedia am JOIN FETCH am.attendant " +
				"WHERE am.attendant.id = :attId and am.type = :type", params);
		return medias;
	}

}
