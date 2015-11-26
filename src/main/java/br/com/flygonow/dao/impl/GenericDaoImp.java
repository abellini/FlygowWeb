package br.com.flygonow.dao.impl;

import br.com.flygonow.dao.GenericDao;
import br.com.flygonow.enums.FetchTypeEnum;
import org.hibernate.LazyInitializationException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional(noRollbackFor = LazyInitializationException.class, propagation = Propagation.REQUIRED)
public class GenericDaoImp<T, ID extends Serializable> implements GenericDao<T, ID> {

	
	private EntityManager entityManager; 

	private final Class<T> oClass;//object class

	public Class<T> getObjectClass() {
		return this.oClass;
	}

	@SuppressWarnings("unchecked")
	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.entityManager = em;
	}

	protected EntityManager getEntityManager() {
		if (entityManager == null)
			throw new IllegalStateException("Erro");
		return entityManager;
	}

	@SuppressWarnings("unchecked")
	public GenericDaoImp() {
		this.oClass = (Class<T>)
		((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}


	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public T update(T object) {
		return getEntityManager().merge(object);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(T object) {
		object = getEntityManager().merge(object);
		getEntityManager().remove(object);
	}

	@Override
	public void remove(T object) {
		getEntityManager().remove(object);
	}
	
	@Override
	public T findById(ID id, String fetchMode, String... fetchs) {
		String queryS = "SELECT obj FROM "+oClass.getSimpleName()+" obj " +
				makeJoins(fetchMode, fetchs) +
				" WHERE obj.id = :id";
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return this.getByParams(queryS, params);
	}
	
	@Override
	public List<T> listByName(String strSearch, String fetchMode, String... fetchs) {
		String queryS = "SELECT obj FROM "+oClass.getSimpleName()+" obj " +
				makeJoins(fetchMode, fetchs) +
				" WHERE upper(obj.name) LIKE upper(:strSearch1) OR upper(obj.name) LIKE upper(:strSearch2) OR upper(obj.name) LIKE upper(:strSearch3)";
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("strSearch1", "%" + strSearch);
		params.put("strSearch2", strSearch + "%");
		params.put("strSearch3", "%" + strSearch + "%");
		return this.listByParams(queryS, params);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public T save(T object) {
		getEntityManager().clear();
		getEntityManager().persist(object);
		return object;
	}

	@SuppressWarnings("unchecked")
	public List<T> getAll(String fetchMode, String... fetchs){
		String queryS = "SELECT obj FROM "+oClass.getSimpleName()+" obj" + makeJoins(fetchMode, fetchs) + " ORDER BY obj.id";
		Query query = getEntityManager().createQuery(queryS);
		return query.getResultList();

	}

	@SuppressWarnings("unchecked")
	public List<T> listByParams(String query, Map<String, Object> params){
		Query q = getEntityManager().createQuery(query);
		if(params != null){
			for(String chave : params.keySet()){
				q.setParameter(chave, params.get(chave));

			}
		}
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> listByParamsPaginated(String query, Map<String, Object> params,
			int maximo, int atual){
		Query q = getEntityManager().
					createQuery(query).
					setMaxResults(maximo).
					setFirstResult(atual);
		if(params != null){
			for(String chave : params.keySet()){
				q.setParameter(chave, params.get(chave));
			}
		}
		return q.getResultList();
	}	
	
	@SuppressWarnings("unchecked")
	public List<T> listByQuery(String query){
		Query q = getEntityManager().createQuery(query);
		return q.getResultList();
	}	
	
	@SuppressWarnings("unchecked")
	public T getByParams(String query, Map<String, Object> params){
		Query q = getEntityManager().createQuery(query);
		for(String chave : params.keySet()){
			q.setParameter(chave, params.get(chave));

		}
		try{
			return (T) q.getSingleResult();
		}catch(NoResultException nre){
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public T getByParams(Map<String, Object> params){
		String WHERE = "";
		if(params.size() > 0){
			WHERE = " WHERE";
			int i = 0;
			for(String paramName : params.keySet()){
				if(i < params.keySet().size() - 1){
					WHERE += " " + paramName + " = " + ":" + paramName + "AND ";
				}else{
					WHERE += " " + paramName + " = " + ":" + paramName; 
				}
				i++;
			}
		}
		String queryS = "SELECT obj FROM " + oClass.getSimpleName() + " obj" + WHERE;
		Query q = getEntityManager().createQuery(queryS);
		for(String chave : params.keySet()){
			q.setParameter(chave, params.get(chave));

		}
		try{
			return (T) q.getSingleResult();
		}catch(NoResultException nre){
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<T> listByParams(Map<String, Object> params){
		String WHERE = "";
		if(params!= null && !params.isEmpty()){
			WHERE = " WHERE";
			int i = 0;
			for(String paramName : params.keySet()){
				if(i < params.keySet().size() - 1){
					WHERE += " " + paramName + " = " + ":" + paramName + "AND ";
				}else{
					WHERE += " " + paramName + " = " + ":" + paramName; 
				}
				i++;
			}
		}
		String queryS = "SELECT obj FROM " + oClass.getSimpleName() + " obj" + WHERE;
		Query q = getEntityManager().createQuery(queryS);
		for(String chave : params.keySet()){
			q.setParameter(chave, params.get(chave));

		}
		try{
			return q.getResultList();
		}catch(NoResultException nre){
			return null;
		}
	}
	
	private String makeJoins(String joinMode, String... fetchs){
		FetchTypeEnum join = FetchTypeEnum.fromName(joinMode);
		String qry = "";
		if(join == null){
			join = FetchTypeEnum.INNER;
		}
		if(fetchs != null && fetchs.length > 0){
			for (String fetch : fetchs) {
				qry += " " + join.getName() + " JOIN FETCH obj." + fetch; 
			}
		}
		return qry;
	}

}
