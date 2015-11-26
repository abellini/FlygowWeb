package br.com.flygonow.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface GenericDao<T, ID extends Serializable> {
	public Class<?> getObjectClass();
	public T save(T object);
	public T findById(ID id, String fetchMode, String... fetchs);
	public T update(T object);
	public void delete(T object);
	public List<T> getAll(String fetchMode, String... fetchs);
	public List<T> listByParams(String query, Map<String, Object> params);
	public List<T> listByParamsPaginated(String query, Map<String, Object> params, 
			int maximo, int atual);
	public List<T> listByQuery(String query);
	public T getByParams(String query, Map<String, Object> params);
	public T getByParams(Map<String, Object> params);
	public List<T> listByName(String strSearch, String fetchMode, String... fetchs);
	void remove(T object);
	public List<T> listByParams(Map<String, Object> params);
	
}
