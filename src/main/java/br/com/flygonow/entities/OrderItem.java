package br.com.flygonow.entities;


import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name="OrderItem")
public class OrderItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7446694672671163562L;

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="order_item_gen")	
	@SequenceGenerator(name = "order_item_gen", sequenceName = "seq_order_item")
	private Long id;
	
	@Column(name="quantity")
	private Short quantity;
	
	@Column(name="observations")
	private String observations;
	
	@Column(name="value")
	private Double value;
	
	@Column(name="iniOrderHour")
	@Temporal(TemporalType.TIME)
	private Date iniOrderHour;
	
	@Column(name="finalOrderHour")
	@Temporal(TemporalType.TIME)
	private Date finalOrderHour;
	
	@Column(name="iniOrderDate")
	@Temporal(TemporalType.DATE)
	private Date iniOrderDate;
	
	@Column(name="finalOrderDate")
	@Temporal(TemporalType.DATE)
	private Date finalOrderDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="orderId", insertable=true, updatable=true)
	@Fetch(FetchMode.JOIN)
	private Order order;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="foodId", insertable=true, updatable=false)
	@Fetch(FetchMode.JOIN)
	private Food food;

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="OrderItem_Accompaniment", joinColumns={@JoinColumn(name="orderItemId")}, 
				inverseJoinColumns={@JoinColumn(name="accompanimentId")})
	@Cascade(CascadeType.ALL)
	private Collection<Accompaniment> accompaniments;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="statusId", insertable=true, updatable=true)
	@Fetch(FetchMode.JOIN)
	private OrderItemStatus orderItemStatus;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="promotionId", insertable=true, updatable=false)
	@Fetch(FetchMode.JOIN)
	private Promotion promotion;
	
	private Long tabletOrderItemId;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Short getQuantity() {
		return quantity;
	}

	public void setQuantity(Short quantity) {
		this.quantity = quantity;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public Date getIniOrderHour() {
		return iniOrderHour;
	}

	public void setIniOrderHour(Date iniOrderHour) {
		this.iniOrderHour = iniOrderHour;
	}

	public Date getFinalOrderHour() {
		return finalOrderHour;
	}

	public void setFinalOrderHour(Date finalOrderHour) {
		this.finalOrderHour = finalOrderHour;
	}

	public Date getIniOrderDate() {
		return iniOrderDate;
	}

	public void setIniOrderDate(Date iniOrderDate) {
		this.iniOrderDate = iniOrderDate;
	}

	public Date getFinalOrderDate() {
		return finalOrderDate;
	}

	public void setFinalOrderDate(Date finalOrderDate) {
		this.finalOrderDate = finalOrderDate;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}

	public Collection<Accompaniment> getAccompaniments() {
		return accompaniments;
	}

	public void setAccompaniments(Collection<Accompaniment> accompaniments) {
		this.accompaniments = accompaniments;
	}

	public Promotion getPromotion() {
		return promotion;
	}

	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}

	public OrderItemStatus getOrderItemStatus() {
		return orderItemStatus;
	}

	public void setOrderItemStatus(OrderItemStatus orderItemStatus) {
		this.orderItemStatus = orderItemStatus;
	}

	public Long getTabletOrderItemId() {
		return tabletOrderItemId;
	}

	public void setTabletOrderItemId(Long tabletOrderItemId) {
		this.tabletOrderItemId = tabletOrderItemId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((accompaniments == null) ? 0 : accompaniments.hashCode());
		result = prime * result
				+ ((finalOrderDate == null) ? 0 : finalOrderDate.hashCode());
		result = prime * result
				+ ((finalOrderHour == null) ? 0 : finalOrderHour.hashCode());
		result = prime * result + ((food == null) ? 0 : food.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((iniOrderDate == null) ? 0 : iniOrderDate.hashCode());
		result = prime * result
				+ ((iniOrderHour == null) ? 0 : iniOrderHour.hashCode());
		result = prime * result
				+ ((observations == null) ? 0 : observations.hashCode());
		result = prime * result + ((order == null) ? 0 : order.hashCode());
		result = prime * result
				+ ((orderItemStatus == null) ? 0 : orderItemStatus.hashCode());
		result = prime * result
				+ ((promotion == null) ? 0 : promotion.hashCode());
		result = prime * result
				+ ((quantity == null) ? 0 : quantity.hashCode());
		result = prime
				* result
				+ ((tabletOrderItemId == null) ? 0 : tabletOrderItemId
						.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		OrderItem other = (OrderItem) obj;
		if (accompaniments == null) {
			if (other.accompaniments != null)
				return false;
		} else if (!accompaniments.equals(other.accompaniments))
			return false;
		if (finalOrderDate == null) {
			if (other.finalOrderDate != null)
				return false;
		} else if (!finalOrderDate.equals(other.finalOrderDate))
			return false;
		if (finalOrderHour == null) {
			if (other.finalOrderHour != null)
				return false;
		} else if (!finalOrderHour.equals(other.finalOrderHour))
			return false;
		if (food == null) {
			if (other.food != null)
				return false;
		} else if (!food.equals(other.food))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (iniOrderDate == null) {
			if (other.iniOrderDate != null)
				return false;
		} else if (!iniOrderDate.equals(other.iniOrderDate))
			return false;
		if (iniOrderHour == null) {
			if (other.iniOrderHour != null)
				return false;
		} else if (!iniOrderHour.equals(other.iniOrderHour))
			return false;
		if (observations == null) {
			if (other.observations != null)
				return false;
		} else if (!observations.equals(other.observations))
			return false;
		if (order == null) {
			if (other.order != null)
				return false;
		} else if (!order.equals(other.order))
			return false;
		if (orderItemStatus == null) {
			if (other.orderItemStatus != null)
				return false;
		} else if (!orderItemStatus.equals(other.orderItemStatus))
			return false;
		if (promotion == null) {
			if (other.promotion != null)
				return false;
		} else if (!promotion.equals(other.promotion))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		if (tabletOrderItemId == null) {
			if (other.tabletOrderItemId != null)
				return false;
		} else if (!tabletOrderItemId.equals(other.tabletOrderItemId))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
}
