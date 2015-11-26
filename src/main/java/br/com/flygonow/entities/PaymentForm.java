package br.com.flygonow.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;


@Entity
@Table(name = "PaymentForm")
public class PaymentForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6772655537601034223L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="paymentform_gen")	
	@SequenceGenerator(name = "paymentform_gen", sequenceName = "seq_paymentform")
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="description")
	private String description;

	@ManyToMany(fetch=FetchType.LAZY, mappedBy="paymentForms")
	private Collection<Order> orders = new ArrayList<Order>();
	
	public PaymentForm(Long id, String name, String description){
		this.id = id;
		this.name = name;
		this.description = description;
	}
	
	public PaymentForm(){
		
	}
	
	public Long getId() {
		return id;
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

	public Collection<Order> getOrders() {
		return orders;
	}

	public void setOrders(Collection<Order> orders) {
		this.orders = orders;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((orders == null) ? 0 : orders.hashCode());
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
		PaymentForm other = (PaymentForm) obj;
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
		if (orders == null) {
			if (other.orders != null)
				return false;
		} else if (!orders.equals(other.orders))
			return false;
		return true;
	}

}
