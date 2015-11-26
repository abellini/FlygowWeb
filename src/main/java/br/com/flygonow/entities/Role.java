package br.com.flygonow.entities;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "Role")
public class Role implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8636905146948296822L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_gen")
	@SequenceGenerator(name = "role_gen", sequenceName = "seq_role")
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="description")
	private String description;
	
	@ManyToMany(fetch=FetchType.LAZY, mappedBy="roles")
	@Cascade(value={CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	private Collection<Attendant> attendants = new ArrayList<Attendant>();
	
	@ManyToMany(fetch=FetchType.LAZY, mappedBy="roles")
	@Cascade(value={CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	private Collection<Client> clients = new ArrayList<Client>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "Role_Module", joinColumns = { @JoinColumn(name = "roleId") }, inverseJoinColumns = { @JoinColumn(name = "moduleId") })
	@Cascade(value={CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	private Collection<Module> modules = new ArrayList<Module>();
	
	@ManyToMany(fetch=FetchType.LAZY, mappedBy="roles")
	private Collection<OperationalArea> operationalAreas = new ArrayList<OperationalArea>();

	public Role() {}

	public Role(Long id, String name, String description) {
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
	
	public Collection<Attendant> getAttendants() {
		return attendants;
	}

	public void setAttendants(Collection<Attendant> attendants) {
		this.attendants = attendants;
	}
	
	public Collection<OperationalArea> getOperationalAreas() {
		return operationalAreas;
	}

	public void setOperationalAreas(Collection<OperationalArea> operationalAreas) {
		this.operationalAreas = operationalAreas;
	}

	public Collection<Client> getClients() {
		return clients;
	}

	public void setClients(Collection<Client> clients) {
		this.clients = clients;
	}

	public Collection<Module> getModules() {
		return modules;
	}

	public void setModules(Collection<Module> modules) {
		this.modules = modules;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((attendants == null) ? 0 : attendants.hashCode());
		result = prime * result + ((clients == null) ? 0 : clients.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((modules == null) ? 0 : modules.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime
				* result
				+ ((operationalAreas == null) ? 0 : operationalAreas.hashCode());
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
		Role other = (Role) obj;
		if (attendants == null) {
			if (other.attendants != null)
				return false;
		} else if (!attendants.equals(other.attendants))
			return false;
		if (clients == null) {
			if (other.clients != null)
				return false;
		} else if (!clients.equals(other.clients))
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
		if (modules == null) {
			if (other.modules != null)
				return false;
		} else if (!modules.equals(other.modules))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (operationalAreas == null) {
			if (other.operationalAreas != null)
				return false;
		} else if (!operationalAreas.equals(other.operationalAreas))
			return false;
		return true;
	}

}
