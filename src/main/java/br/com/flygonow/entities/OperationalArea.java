package br.com.flygonow.entities;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name="OperationalArea")
public class OperationalArea implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3908445889887256989L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="oparea_gen")	
	@SequenceGenerator(name = "oparea_gen", sequenceName = "seq_oparea")
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="description")
	private String description;
	
	@OneToMany(mappedBy = "operationalArea", fetch = FetchType.LAZY)
	@Cascade(CascadeType.ALL)
	private Collection<Food> foods;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="OperationalArea_Role", 
		joinColumns=
			{@JoinColumn(name="operationalAreaId")}, 
		inverseJoinColumns=
			{@JoinColumn(name="roleId")})
	@Cascade(value={CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	private Collection<Role> roles = new ArrayList<Role>();
	
	@OneToMany(mappedBy = "operationalArea", fetch = FetchType.LAZY)
	@Cascade(CascadeType.ALL)
	private Collection<Accompaniment> accompaniments;

	public OperationalArea() {}

	public OperationalArea(Long id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
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

	public Collection<Food> getFoods() {
		return foods;
	}

	public void setFoods(Collection<Food> foods) {
		this.foods = foods;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public Collection<Accompaniment> getAccompaniments() {
		return accompaniments;
	}

	public void setAccompaniments(Collection<Accompaniment> accompaniments) {
		this.accompaniments = accompaniments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((accompaniments == null) ? 0 : accompaniments.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((foods == null) ? 0 : foods.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((roles == null) ? 0 : roles.hashCode());
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
		OperationalArea other = (OperationalArea) obj;
		if (accompaniments == null) {
			if (other.accompaniments != null)
				return false;
		} else if (!accompaniments.equals(other.accompaniments))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
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
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (roles == null) {
			if (other.roles != null)
				return false;
		} else if (!roles.equals(other.roles))
			return false;
		return true;
	}
}
