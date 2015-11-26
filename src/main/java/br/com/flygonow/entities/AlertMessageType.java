package br.com.flygonow.entities;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "AlertMessageType")
public class AlertMessageType implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -1739907298483731464L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="alert_message_type_gen")	
	@SequenceGenerator(name = "alert_message_type_gen", sequenceName = "seq_alert_message_type")
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="description")
	private String description;
	
	@OneToMany(mappedBy="type", fetch=FetchType.LAZY)
	@Cascade(CascadeType.SAVE_UPDATE)
	private Collection<AttendantAlert> alerts;

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
	
	public Collection<AttendantAlert> getAlerts() {
		return alerts;
	}

	public void setAlerts(Collection<AttendantAlert> alerts) {
		this.alerts = alerts;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alerts == null) ? 0 : alerts.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		AlertMessageType other = (AlertMessageType) obj;
		if (alerts == null) {
			if (other.alerts != null)
				return false;
		} else if (!alerts.equals(other.alerts))
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
		return true;
	}
}
