package br.com.flygonow.entities;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name="Accompaniment")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Accompaniment extends Product implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1140821382417014457L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="accompaniment_gen")	
	@SequenceGenerator(name = "accompaniment_gen", sequenceName = "seq_accompaniment")
	private Long id;
	
	@ManyToMany(fetch=FetchType.LAZY, mappedBy="accompaniments")
	@Cascade(value={CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	private Collection<Food> foods = new ArrayList<Food>();
	
	@ManyToMany(fetch=FetchType.LAZY, mappedBy="accompaniments")
	@Cascade(value={CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	private Collection<Promotion> promotions = new ArrayList<Promotion>();
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="categoryId", insertable=true, updatable=true)
	@Fetch(FetchMode.JOIN)
	private Category category;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="operationalAreaId", insertable=true, updatable=true)
	@Fetch(FetchMode.JOIN)
	private OperationalArea operationalArea;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="OrderItem_Accompaniment", joinColumns={@JoinColumn(name="accompanimentId")}, 
				inverseJoinColumns={@JoinColumn(name="orderItemId")})
	@Cascade(value={CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	private Collection<OrderItem> orderItems = new ArrayList<OrderItem>();
	
	@OneToMany(mappedBy="accompaniment", fetch = LAZY, cascade = ALL)  
	@OnDelete(action=OnDeleteAction.CASCADE)
    private Collection<AccompanimentMedia> accompanimentMedia = new ArrayList<AccompanimentMedia>();
	
	public Accompaniment(){}
	
	public Accompaniment(String mediaName){
		setVideoName(mediaName);
		setPhotoName(mediaName);
	}
	
	public Accompaniment(
			Long id, String name, Double value, String photoName, String videoName, String description, boolean isActive
	){
		super(name, value, photoName, videoName, description, isActive);
		this.id = id;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}


	public Collection<Food> getFoods() {
		return foods;
	}

	public void setFoods(Collection<Food> foods) {
		this.foods = foods;
	}

	public Collection<Promotion> getPromotions() {
		return promotions;
	}

	public void setPromotions(Collection<Promotion> promotions) {
		this.promotions = promotions;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OperationalArea getOperationalArea() {
		return operationalArea;
	}

	public void setOperationalArea(OperationalArea operationalArea) {
		this.operationalArea = operationalArea;
	}

	public Collection<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(Collection<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public Collection<AccompanimentMedia> getAccompanimentMedia() {
		return accompanimentMedia;
	}


	public void setAccompanimentMedia(
			Collection<AccompanimentMedia> accompanimentMedia) {
		this.accompanimentMedia = accompanimentMedia;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime
				* result
				+ ((accompanimentMedia == null) ? 0 : accompanimentMedia
						.hashCode());
		result = prime * result
				+ ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((foods == null) ? 0 : foods.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((operationalArea == null) ? 0 : operationalArea.hashCode());
		result = prime * result
				+ ((orderItems == null) ? 0 : orderItems.hashCode());
		result = prime * result
				+ ((promotions == null) ? 0 : promotions.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Accompaniment other = (Accompaniment) obj;
		if (accompanimentMedia == null) {
			if (other.accompanimentMedia != null)
				return false;
		} else if (!accompanimentMedia.equals(other.accompanimentMedia))
			return false;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (foods == null) {
			if (other.foods != null)
				return false;
		} else if (!foods.equals(other.foods))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (operationalArea == null) {
			if (other.operationalArea != null)
				return false;
		} else if (!operationalArea.equals(other.operationalArea))
			return false;
		if (orderItems == null) {
			if (other.orderItems != null)
				return false;
		} else if (!orderItems.equals(other.orderItems))
			return false;
		if (promotions == null) {
			if (other.promotions != null)
				return false;
		} else if (!promotions.equals(other.promotions))
			return false;
		return true;
	}
}
