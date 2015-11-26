package br.com.flygonow.entities;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name="Food")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Food extends Product implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1090863478083662164L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="food_gen")	
	@SequenceGenerator(name = "food_gen", sequenceName = "seq_food")
	private Long id;
	
	@Column(name="nutritionalInfo")
	private String nutritionalInfo;
	
	@Column(name="maxQtdAccompaniments", nullable=true)
	private Integer maxQtdAccompaniments;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="categoryId", insertable=true, updatable=true)
	@Fetch(FetchMode.JOIN)
	private Category category;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="operationalAreaId", insertable=true, updatable=true)
	@Fetch(FetchMode.JOIN)
	private OperationalArea operationalArea;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="Food_Accompaniment", 
		joinColumns=
			{@JoinColumn(name="foodId")}, 
		inverseJoinColumns=
			{@JoinColumn(name="accompanimentId")})
	@Cascade(value={CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	private Collection<Accompaniment> accompaniments = new ArrayList<Accompaniment>();

	@ManyToMany(fetch=FetchType.LAZY, mappedBy="foods")
	@Cascade(value={CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	private Collection<Promotion> promotions = new HashSet<Promotion>();
	
	@OneToMany(mappedBy = "food", fetch = FetchType.LAZY)
	@Cascade(value={CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	private Collection<OrderItem> orderItems;
	
	@OneToMany(mappedBy="food", fetch = LAZY, cascade = ALL)    
    private Collection<FoodMedia> foodMedia = new ArrayList<FoodMedia>();
	
	public Food(){}
	
	public Food(String mediaName){
		setPhotoName(mediaName);
		setVideoName(mediaName);
	}
	
	public Food(
			Long id, String name, String description, Double value, String photoName, String videoName, boolean isActive, String nutritionalInfo, Integer maxQtdAccompaniments
	){
		super(name, value, photoName, videoName, description, isActive);
		this.id = id;
		this.nutritionalInfo = nutritionalInfo;
		this.maxQtdAccompaniments = maxQtdAccompaniments;
	}
	
	public String getNutritionalInfo() {
		return nutritionalInfo;
	}

	public void setNutritionalInfo(String nutritionalInfo) {
		this.nutritionalInfo = nutritionalInfo;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Collection<Accompaniment> getAccompaniments() {
		return accompaniments;
	}

	public void setAccompaniments(Collection<Accompaniment> accompaniments) {
		this.accompaniments = accompaniments;
	}
	
	public OperationalArea getOperationalArea() {
		return operationalArea;
	}

	public void setOperationalArea(OperationalArea operationalArea) {
		this.operationalArea = operationalArea;
	}

	public Collection<Promotion> getPromotions() {
		return promotions;
	}

	public void setPromotions(Collection<Promotion> promotions) {
		this.promotions = promotions;
	}

	public Collection<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(Collection<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Collection<FoodMedia> getFoodMedia() {
		return foodMedia;
	}

	public void setFoodMedia(Collection<FoodMedia> foodMedia) {
		this.foodMedia = foodMedia;
	}
	
	public Integer getMaxQtdAccompaniments() {
		return maxQtdAccompaniments;
	}

	public void setMaxQtdAccompaniments(Integer maxQtdAccompaniments) {
		this.maxQtdAccompaniments = maxQtdAccompaniments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((accompaniments == null) ? 0 : accompaniments.hashCode());
		result = prime * result
				+ ((category == null) ? 0 : category.hashCode());
		result = prime * result
				+ ((foodMedia == null) ? 0 : foodMedia.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime
				* result
				+ ((maxQtdAccompaniments == null) ? 0 : maxQtdAccompaniments
						.hashCode());
		result = prime * result
				+ ((nutritionalInfo == null) ? 0 : nutritionalInfo.hashCode());
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
		Food other = (Food) obj;
		if (accompaniments == null) {
			if (other.accompaniments != null)
				return false;
		} else if (!accompaniments.equals(other.accompaniments))
			return false;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (foodMedia == null) {
			if (other.foodMedia != null)
				return false;
		} else if (!foodMedia.equals(other.foodMedia))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (maxQtdAccompaniments == null) {
			if (other.maxQtdAccompaniments != null)
				return false;
		} else if (!maxQtdAccompaniments.equals(other.maxQtdAccompaniments))
			return false;
		if (nutritionalInfo == null) {
			if (other.nutritionalInfo != null)
				return false;
		} else if (!nutritionalInfo.equals(other.nutritionalInfo))
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
