package br.com.flygonow.service;

public interface TabletService {

	void delete(Long id);
	void changeTabletStatus(Short tabletNumber, Long statusId);

}
