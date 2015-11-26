package br.com.flygonow.dao.impl;

import br.com.flygonow.dao.CoinDao;
import br.com.flygonow.entities.Coin;
import org.springframework.stereotype.Repository;

@Repository
public class CoinDaoImp extends GenericDaoImp<Coin, Long> implements CoinDao{


}
