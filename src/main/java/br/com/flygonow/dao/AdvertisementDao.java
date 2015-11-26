package br.com.flygonow.dao;

import br.com.flygonow.entities.Advertisement;

import java.util.Collection;
import java.util.List;

public interface AdvertisementDao extends GenericDao<Advertisement, Long>{

	String getVideoNameByAdvertisement(Long videoId);

	String getPhotoNameByAdvertisement(Long id);

	Collection<Advertisement> listByTabletId(Long id);

	List<Advertisement> getAllActives();


}
