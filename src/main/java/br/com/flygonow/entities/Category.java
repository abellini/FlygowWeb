package br.com.flygonow.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name="Category")
public class Category implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4648288329428701252L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="category_gen")	
	@SequenceGenerator(name = "category_gen", sequenceName = "seq_category")
	private Long id;

	@Column(name="name")
	private String name;

	@Column(name="description")
	private String description;
	
	@Column(name="photoName")
	private String photoName;
	
	@OneToMany(mappedBy="category", fetch = LAZY, cascade = ALL)    
	private Collection<Food> foods;

	@OneToMany(mappedBy="category", fetch = LAZY, cascade = ALL)    
	private Collection<Accompaniment> accompaniment;
	
	@OneToMany(mappedBy="category", fetch = LAZY, cascade = ALL)    
	private Collection<Promotion> promotion;
	
	@OneToMany(mappedBy="category", fetch = LAZY, cascade = ALL)    
    private Collection<CategoryMedia> categoryMedia = new ArrayList<CategoryMedia>();
	
	public Category(Long id, String name, String description, String photoName) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.photoName = photoName;
	}
	
	public Category() {
	
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPhotoName() {
		return photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	public Collection<CategoryMedia> getCategoryMedia() {
		return categoryMedia;
	}

	public void setCategoryMedia(Collection<CategoryMedia> categoryMedia) {
		this.categoryMedia = categoryMedia;
	}

	public Collection<Food> getFoods() {
		return foods;
	}

	public void setFoods(Collection<Food> products) {
		this.foods = products;
	}

	public Collection<Accompaniment> getAccompaniment() {
		return accompaniment;
	}

	public void setAccompaniment(Collection<Accompaniment> accompaniment) {
		this.accompaniment = accompaniment;
	}	

	public Collection<Promotion> getPromotion() {
		return promotion;
	}

	public void setPromotion(Collection<Promotion> promotion) {
		this.promotion = promotion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((accompaniment == null) ? 0 : accompaniment.hashCode());
		result = prime * result
				+ ((categoryMedia == null) ? 0 : categoryMedia.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((photoName == null) ? 0 : photoName.hashCode());
		result = prime * result
				+ ((foods == null) ? 0 : foods.hashCode());
		result = prime * result
				+ ((promotion == null) ? 0 : promotion.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		if (accompaniment == null) {
			if (other.accompaniment != null)
				return false;
		} else if (!accompaniment.equals(other.accompaniment))
			return false;
		if (categoryMedia == null) {
			if (other.categoryMedia != null)
				return false;
		} else if (!categoryMedia.equals(other.categoryMedia))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (photoName == null) {
			if (other.photoName != null)
				return false;
		} else if (!photoName.equals(other.photoName))
			return false;
		if (foods == null) {
			if (other.foods != null)
				return false;
		} else if (!foods.equals(other.foods))
			return false;
		if (promotion == null) {
			if (other.promotion != null)
				return false;
		} else if (!promotion.equals(other.promotion))
			return false;
		return true;
	}
}
