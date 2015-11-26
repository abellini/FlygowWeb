package br.com.flygonow.entities;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "Promotion")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Promotion extends Product implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8330772420138550483L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="promotion_gen")	
	@SequenceGenerator(name = "promotion_gen", sequenceName = "seq_promotion")
	private Long id;
	
	@Column(name = "inicialDate")
	@Temporal(TemporalType.DATE)
	private Date inicialDate;

	@Column(name = "finalDate")
	@Temporal(TemporalType.DATE)
	private Date finalDate;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="categoryId", insertable=true, updatable=true)
	@Fetch(FetchMode.JOIN)
	private Category category;
	
	@OneToMany(mappedBy = "promotion", fetch = FetchType.LAZY)
	@Cascade(value={CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	private Collection<OrderItem> orderItems = new ArrayList<OrderItem>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "Promotion_Food", joinColumns = { @JoinColumn(name = "promotionId") }, 
		inverseJoinColumns = { @JoinColumn(name = "foodId") })
	@Cascade(value={CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	private Set<Food> foods = new HashSet<Food>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "Promotion_Accompaniment", joinColumns = { @JoinColumn(name = "promotionId") }, 
		inverseJoinColumns = { @JoinColumn(name = "accompanimentId") })
	@Cascade(value={CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	private Collection<Accompaniment> accompaniments = new ArrayList<Accompaniment>();

	@OneToMany(mappedBy="promotion", fetch = LAZY, cascade = ALL)    
    private Collection<PromotionMedia> promotionMedia = new ArrayList<PromotionMedia>();

	public Promotion(){}
	
	public Promotion(String mediaName){
		setVideoName(mediaName);
		setPhotoName(mediaName);
	}
	
	public Promotion(Long id, String name, Double value, String photoName, String videoName, String description, Date iniDate, Date endDate, boolean isActive){
		super(name, value, photoName, videoName, description, isActive);
		this.id = id;
		this.inicialDate = iniDate;
		this.finalDate = endDate;
	}
	
	public Date getInicialDate() {
		return inicialDate;
	}

	public void setInicialDate(Date inicialDate) {
		this.inicialDate = inicialDate;
	}

	public Date getFinalDate() {
		return finalDate;
	}

	public void setFinalDate(Date finalDate) {
		this.finalDate = finalDate;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Collection<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(Collection<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public Set<Food> getFoods() {
		return foods;
	}

	public void setFoods(Set<Food> foods) {
		this.foods = foods;
	}

	public Collection<Accompaniment> getAccompaniments() {
		return accompaniments;
	}

	public void setAccompaniments(Collection<Accompaniment> accompaniment) {
		this.accompaniments = accompaniment;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Collection<PromotionMedia> getPromotionMedia() {
		return promotionMedia;
	}

	public void setPromotionMedia(Collection<PromotionMedia> promotionMedia) {
		this.promotionMedia = promotionMedia;
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
				+ ((finalDate == null) ? 0 : finalDate.hashCode());
		result = prime * result + ((foods == null) ? 0 : foods.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((inicialDate == null) ? 0 : inicialDate.hashCode());
		result = prime * result
				+ ((orderItems == null) ? 0 : orderItems.hashCode());
		result = prime * result
				+ ((promotionMedia == null) ? 0 : promotionMedia.hashCode());
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
		Promotion other = (Promotion) obj;
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
		if (finalDate == null) {
			if (other.finalDate != null)
				return false;
		} else if (!finalDate.equals(other.finalDate))
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
		if (inicialDate == null) {
			if (other.inicialDate != null)
				return false;
		} else if (!inicialDate.equals(other.inicialDate))
			return false;
		if (orderItems == null) {
			if (other.orderItems != null)
				return false;
		} else if (!orderItems.equals(other.orderItems))
			return false;
		if (promotionMedia == null) {
			if (other.promotionMedia != null)
				return false;
		} else if (!promotionMedia.equals(other.promotionMedia))
			return false;
		return true;
	}
}
