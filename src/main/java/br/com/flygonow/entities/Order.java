package br.com.flygonow.entities;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "Ordered")
public class Order implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2573787378480064996L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="order_gen")	
	@SequenceGenerator(name = "order_gen", sequenceName = "seq_order")
	private Long id;

	@Column(name="totalValue")
	private Double totalValue;
	
	@Column(name="orderHour")
	@Temporal(TemporalType.DATE)
	private Date orderHour;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="clientId", insertable=false, updatable=false)
	@Fetch(FetchMode.JOIN)
	private Client client;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="tabletId", insertable=true, updatable=false)
	@Fetch(FetchMode.JOIN)
	private Tablet tablet;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="attendantId", insertable=true, updatable=false)
	@Fetch(FetchMode.JOIN)
	private Attendant attendant;
	
	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
	@Cascade(CascadeType.ALL)
	private Collection<OrderItem> orderItems;
		
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="Order_PaymentForm", joinColumns={@JoinColumn(name="orderId")}, 
				inverseJoinColumns={@JoinColumn(name="paymentFormId")})
	@Cascade(CascadeType.ALL)
	private Collection<PaymentForm> paymentForms = new ArrayList<PaymentForm>();
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="statusId", insertable=true, updatable=true)
	@Fetch(FetchMode.JOIN)
	private OrderStatus orderStatus;
	
	private Long tabletOrderId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(Double totalValue) {
		this.totalValue = totalValue;
	}

	public Date getOrderHour() {
		return orderHour;
	}

	public void setOrderHour(Date orderHour) {
		this.orderHour = orderHour;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Tablet getTablet() {
		return tablet;
	}

	public void setTablet(Tablet tablet) {
		this.tablet = tablet;
	}

	public Collection<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(Collection<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public Attendant getAttendant() {
		return attendant;
	}

	public void setAttendant(Attendant attendant) {
		this.attendant = attendant;
	}

	public Collection<PaymentForm> getPaymentForms() {
		return paymentForms;
	}

	public void setPaymentForms(Collection<PaymentForm> paymentForm) {
		this.paymentForms = paymentForm;
	}
	
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	 public void addOrderItem(OrderItem orderItem) {
	     if (orderItem != null) {
	        if (orderItems == null) {
	            orderItems = new ArrayList<OrderItem>();          
	        }
	        orderItems.add(orderItem);
	        orderItem.setOrder(this);
	     }
	  }

	public Long getTabletOrderId() {
		return tabletOrderId;
	}

	public void setTabletOrderId(Long tabletOrderId) {
		this.tabletOrderId = tabletOrderId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((attendant == null) ? 0 : attendant.hashCode());
		result = prime * result + ((client == null) ? 0 : client.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((orderHour == null) ? 0 : orderHour.hashCode());
		result = prime * result
				+ ((orderItems == null) ? 0 : orderItems.hashCode());
		result = prime * result
				+ ((orderStatus == null) ? 0 : orderStatus.hashCode());
		result = prime * result
				+ ((paymentForms == null) ? 0 : paymentForms.hashCode());
		result = prime * result + ((tablet == null) ? 0 : tablet.hashCode());
		result = prime * result
				+ ((tabletOrderId == null) ? 0 : tabletOrderId.hashCode());
		result = prime * result
				+ ((totalValue == null) ? 0 : totalValue.hashCode());
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
		Order other = (Order) obj;
		if (attendant == null) {
			if (other.attendant != null)
				return false;
		} else if (!attendant.equals(other.attendant))
			return false;
		if (client == null) {
			if (other.client != null)
				return false;
		} else if (!client.equals(other.client))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (orderHour == null) {
			if (other.orderHour != null)
				return false;
		} else if (!orderHour.equals(other.orderHour))
			return false;
		if (orderItems == null) {
			if (other.orderItems != null)
				return false;
		} else if (!orderItems.equals(other.orderItems))
			return false;
		if (orderStatus == null) {
			if (other.orderStatus != null)
				return false;
		} else if (!orderStatus.equals(other.orderStatus))
			return false;
		if (paymentForms == null) {
			if (other.paymentForms != null)
				return false;
		} else if (!paymentForms.equals(other.paymentForms))
			return false;
		if (tablet == null) {
			if (other.tablet != null)
				return false;
		} else if (!tablet.equals(other.tablet))
			return false;
		if (tabletOrderId == null) {
			if (other.tabletOrderId != null)
				return false;
		} else if (!tabletOrderId.equals(other.tabletOrderId))
			return false;
		if (totalValue == null) {
			if (other.totalValue != null)
				return false;
		} else if (!totalValue.equals(other.totalValue))
			return false;
		return true;
	}
}
