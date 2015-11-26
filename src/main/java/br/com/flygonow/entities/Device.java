package br.com.flygonow.entities;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "Device")
public class Device implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4440922080396033984L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "role_gen")
	@SequenceGenerator(name = "role_gen", sequenceName = "seq_role")
	private Long id;
	
	@Column(name="ip")
	private String ip;
	
	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private Attendant attendant;
	
	@OneToMany(mappedBy = "device", fetch = FetchType.LAZY)
	@Cascade(CascadeType.SAVE_UPDATE)
	private Collection<AttendantAlert> attendantAlerts;
	
	public Attendant getAttendant() {
		return attendant;
	}

	public void setAttendant(Attendant attendant) {
		this.attendant = attendant;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Collection<AttendantAlert> getAttendantAlerts() {
		return attendantAlerts;
	}

	public void setAttendantAlerts(Collection<AttendantAlert> attendantAlerts) {
		this.attendantAlerts = attendantAlerts;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((attendant == null) ? 0 : attendant.hashCode());
		result = prime * result
				+ ((attendantAlerts == null) ? 0 : attendantAlerts.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
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
		Device other = (Device) obj;
		if (attendant == null) {
			if (other.attendant != null)
				return false;
		} else if (!attendant.equals(other.attendant))
			return false;
		if (attendantAlerts == null) {
			if (other.attendantAlerts != null)
				return false;
		} else if (!attendantAlerts.equals(other.attendantAlerts))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		return true;
	}
}
