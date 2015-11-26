package br.com.flygonow.service.impl;

import br.com.flygonow.dao.CoinDao;
import br.com.flygonow.dao.TabletDao;
import br.com.flygonow.entities.Coin;
import br.com.flygonow.entities.Tablet;
import br.com.flygonow.service.CoinService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoinServiceImpl implements CoinService {

	private static Logger LOGGER = Logger.getLogger(CoinServiceImpl.class);
	
	@Autowired
	private CoinDao coinDAO;
	
	@Autowired
	private TabletDao tabletDAO;
	
	@Override
	public void delete(Long id){
		try{
			Coin findById = coinDAO.findById(id, null);
			deleteAssociations(findById);
			Coin expurge = new Coin();
			expurge.setId(id);
			coinDAO.update(expurge);
			coinDAO.delete(expurge);
		}catch(Exception e){
			LOGGER.error("DELETE ASSOCIATIONS ERROR ->> " + e);
		}
	}

	private void deleteAssociations(Coin root) {
		deleteTabletAssociations(root);
	}

	private void deleteTabletAssociations(Coin root) {
		List<Tablet> tablets = tabletDAO.listByCoinId(root.getId());
		for (Tablet tablet : tablets) {
			tablet.setCoin(null);
			tabletDAO.update(tablet);
		}
		root.setTablets(null);
	}
}
