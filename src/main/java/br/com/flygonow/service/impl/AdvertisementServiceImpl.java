package br.com.flygonow.service.impl;

import br.com.flygonow.dao.AdvertisementDao;
import br.com.flygonow.dao.AdvertisementMediaDao;
import br.com.flygonow.dao.TabletDao;
import br.com.flygonow.entities.Advertisement;
import br.com.flygonow.entities.AdvertisementMedia;
import br.com.flygonow.entities.Tablet;
import br.com.flygonow.enums.MediaTypeEnum;
import br.com.flygonow.service.AdvertisementService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {

	private static Logger LOGGER = Logger.getLogger(AdvertisementServiceImpl.class);
	
	@Autowired
	private AdvertisementDao advertisementDAO;
	
	@Autowired
	private AdvertisementMediaDao advertisementMediaDAO;
	
	@Autowired
	private TabletDao tabletDAO;
	
	@Override
	public void delete(Long id){
		try{
			Advertisement findById = advertisementDAO.findById(id, null);
			deleteAssociations(findById);
			Advertisement expurge = new Advertisement();
			expurge.setId(id);
			advertisementDAO.update(expurge);
			advertisementDAO.delete(expurge);
		}catch(Exception e){
			LOGGER.error("DELETE ASSOCIATIONS ERROR ->> " + e);
		}
	}

	private void deleteAssociations(Advertisement root) {
		deleteTabletAssociations(root);
		deleteMediaAssociations(root);
	}

	private void deleteTabletAssociations(Advertisement root) {
		List<Tablet> tablets = tabletDAO.listByAdvertisementId(root.getId());
		for (Tablet tablet : tablets) {
			Collection<Advertisement> advertisements = advertisementDAO.listByTabletId(tablet.getId());
			Collection<Advertisement> retainAll = new ArrayList<Advertisement>();
			for (Advertisement advertisement : advertisements) {
				if(!advertisement.getId().equals(root.getId())){
					retainAll.add(advertisement);
				}
			}
			advertisements.retainAll(retainAll);
			tablet.setAdvertisements(new HashSet<Advertisement>(advertisements));
			tabletDAO.update(tablet);
		}
		root.setTablets(null);
	}
	
	private void deleteMediaAssociations(Advertisement root) {
		if(root.getPhotoName() != null){
			List<AdvertisementMedia> medias = 
					advertisementMediaDAO.listByAdvertisement(root.getId(), MediaTypeEnum.PHOTO.getId());
			for (AdvertisementMedia advMedia : medias) {
				advMedia.setAdvertisement(null);
				advertisementMediaDAO.update(advMedia);
				advertisementMediaDAO.delete(advMedia);
			}
		}
		if(root.getVideoName() != null){
			List<AdvertisementMedia> medias = 
					advertisementMediaDAO.listByAdvertisement(root.getId(), MediaTypeEnum.VIDEO.getId());
			for (AdvertisementMedia advMedia : medias) {
				advMedia.setAdvertisement(null);
				advertisementMediaDAO.update(advMedia);
				advertisementMediaDAO.delete(advMedia);
			}
		}
	}

	@Override
	public void removeMedia(Long id, MediaTypeEnum mediaType) {
		List<AdvertisementMedia> findByAdvertisement = 
				advertisementMediaDAO.listByAdvertisement(id, mediaType.getId());
		for (AdvertisementMedia advertisementMedia : findByAdvertisement) {
			advertisementMedia.setAdvertisement(null);
			advertisementMediaDAO.update(advertisementMedia);
		}
		Advertisement findById = advertisementDAO.findById(id, null);
		if(MediaTypeEnum.PHOTO.equals(mediaType)){
			findById.setPhotoName(null);
			advertisementDAO.update(findById);
		}else if(MediaTypeEnum.VIDEO.equals(mediaType)){
			findById.setVideoName(null);
			advertisementDAO.update(findById);
		}
	}
}
