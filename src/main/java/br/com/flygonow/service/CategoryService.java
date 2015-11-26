package br.com.flygonow.service;

import br.com.flygonow.enums.MediaTypeEnum;

public interface CategoryService {

	void delete(Long id);

	void removeMedia(Long id, MediaTypeEnum mediaType);

}
