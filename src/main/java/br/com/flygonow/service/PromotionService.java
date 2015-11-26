package br.com.flygonow.service;

import br.com.flygonow.enums.MediaTypeEnum;

public interface PromotionService {

	void delete(Long id);

	void removeMedia(Long id, MediaTypeEnum mediaType);

}
