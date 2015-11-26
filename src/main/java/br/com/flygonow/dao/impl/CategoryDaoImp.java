package br.com.flygonow.dao.impl;

import br.com.flygonow.dao.CategoryDao;
import br.com.flygonow.entities.Category;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class CategoryDaoImp extends GenericDaoImp<Category, Long> implements CategoryDao {

	@Override
	public String getPhotoNameByCategory(Long id) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Category> criteria = criteriaBuilder.createQuery(Category.class);
		Root<Category> root = criteria.from( Category.class );
		criteria.multiselect( root.get("photoName"));
		criteria.where( criteriaBuilder.equal( root.get( "id" ), id ) );
		try{
			Category response = getEntityManager().createQuery( criteria ).getSingleResult();
			return response.getPhotoName();
		}catch(Exception e){
			return null;
		}
	}

}
