package br.com.flygonow.dao;

import br.com.flygonow.entities.AttendantMedia;

import java.util.List;

public interface AttendantMediaDao extends GenericDao<AttendantMedia, Long>{

	byte[] getPhoto(Long promoId);

	AttendantMedia findByAttendant(Long attId, Byte mediaType);

	List<AttendantMedia> listByAttendantId(Long attId, Byte mediaType);

}
