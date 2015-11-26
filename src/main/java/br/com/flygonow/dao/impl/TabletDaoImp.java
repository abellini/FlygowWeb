package br.com.flygonow.dao.impl;

import br.com.flygonow.dao.TabletDao;
import br.com.flygonow.entities.Tablet;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TabletDaoImp extends GenericDaoImp<Tablet, Long> implements TabletDao{

	@Override
	public Collection<Tablet> listByNumber(String strSearch) {
		Collection<Tablet> tablets = null;
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("number", Short.parseShort(strSearch));
		try{
			tablets = this.listByParams(
				"SELECT tab FROM Tablet tab " +
				"LEFT JOIN FETCH tab.advertisements " +
				"WHERE tab.number = :number", 
			params);
		}catch(Exception e){
			System.out.println("Error ->> " + e);
		}
		return tablets;
	}

	@Override
	public List<Tablet> listByCoinId(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return this.listByParams("SELECT t FROM Tablet t LEFT JOIN t.coin c WHERE c.id = :id", params);
	}

	@Override
	public List<Tablet> listByAttendantId(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return this.listByParams("SELECT t FROM Tablet t LEFT JOIN t.attendant a WHERE a.id = :id", params);
	}

	@Override
	public List<Tablet> listByAdvertisementId(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return this.listByParams("SELECT t FROM Tablet t LEFT JOIN t.advertisements a WHERE a.id = :id", params);
	}

	@Override
	public Tablet getByNumber(Short number) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("number", number);
		Tablet byParams = this.getByParams(params);
		return byParams;
	}


}
