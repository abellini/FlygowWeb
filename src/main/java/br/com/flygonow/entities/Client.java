package br.com.flygonow.entities;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name="Client")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Client extends People implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4861052448902243816L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="client_gen")	
	@SequenceGenerator(name = "client_gen", sequenceName = "seq_client")
	private Long id;

	@OneToMany(mappedBy="client", fetch = LAZY)
	private Collection<Order> orders = new ArrayList<Order>();

	@OneToMany(mappedBy="client", fetch = LAZY)
	private Collection<EvaluationCustomer> evaluationCustomers = new ArrayList<EvaluationCustomer>();
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="Client_Role", joinColumns={@JoinColumn(name="client_id")}, 
				inverseJoinColumns={@JoinColumn(name="role_id")})
	@Cascade(CascadeType.ALL)
	private Collection<Role> roles = new ArrayList<Role>();
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Collection<Order> getOrders() {
		return orders;
	}

	public void setOrders(Collection<Order> orders) {
		this.orders = orders;
	}

	public Collection<EvaluationCustomer> getEvaluationCustomers() {
		return evaluationCustomers;
	}

	public void setEvaluationCustomers(Collection<EvaluationCustomer> evaluationCustomers) {
		this.evaluationCustomers = evaluationCustomers;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime
				* result
				+ ((evaluationCustomers == null) ? 0 : evaluationCustomers
						.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((orders == null) ? 0 : orders.hashCode());
		result = prime * result + ((roles == null) ? 0 : roles.hashCode());
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
		Client other = (Client) obj;
		if (evaluationCustomers == null) {
			if (other.evaluationCustomers != null)
				return false;
		} else if (!evaluationCustomers.equals(other.evaluationCustomers))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (orders == null) {
			if (other.orders != null)
				return false;
		} else if (!orders.equals(other.orders))
			return false;
		if (roles == null) {
			if (other.roles != null)
				return false;
		} else if (!roles.equals(other.roles))
			return false;
		return true;
	}
}