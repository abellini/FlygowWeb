package br.com.flygonow.dao;

import br.com.flygonow.entities.AdvertisementMedia;

import java.util.List;

public interface AdvertisementMediaDao extends GenericDao<AdvertisementMedia, Long>{

	byte[] getPhoto(Long id);

	byte[] getVideo(Long id);
	
	AdvertisementMedia findByAdvertisement(Long id, Byte mediaType);

	List<AdvertisementMedia> listByAdvertisement(Long id, Byte mediaType);

}
