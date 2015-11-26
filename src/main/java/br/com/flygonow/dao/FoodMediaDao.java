package br.com.flygonow.dao;

import br.com.flygonow.entities.FoodMedia;

import java.util.List;

public interface FoodMediaDao extends GenericDao<FoodMedia, Long>{

	byte[] getPhoto(Long id);

	byte[] getVideo(Long id);
	
	FoodMedia findByFood(Long id, Byte mediaType);

	List<FoodMedia> listByFood(Long id, Byte id2);

}
