package br.com.flygonow.entities;

import net.sf.json.JSONObject;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "Attendant")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Attendant extends People implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5740891438660772009L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attendant_gen")
	@SequenceGenerator(name = "attendant_gen", sequenceName = "seq_attendant")
	private Long id;
	
	@OneToMany(mappedBy = "attendant", fetch = FetchType.LAZY)
	@Cascade(value={CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	private Set<Tablet> tablets;

	@OneToOne(mappedBy = "attendant", fetch = FetchType.LAZY)
	private Device device;
	
	@OneToMany(mappedBy = "attendant", fetch = FetchType.LAZY)
	@Cascade(value={CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	private Set<Order> orders;
	
	@OneToMany(mappedBy = "attendant", fetch = FetchType.LAZY)
	@Cascade(value={CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	private Set<AttendantAlert> attendantAlerts;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="Attendant_Role", joinColumns={@JoinColumn(name="attendant_id")}, 
				inverseJoinColumns={@JoinColumn(name="role_id")})
	@Cascade(value={CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	private Collection<Role> roles = new ArrayList<Role>();
	
	@OneToMany(mappedBy="attendant", fetch = LAZY, cascade = ALL)    
    private Collection<AttendantMedia> attendantMedia = new ArrayList<AttendantMedia>();
	
	public Attendant() {}
	
	public Attendant(String mediaName) {
		setPhotoName(mediaName);
	}
	
	public Attendant(
			Long id, String name, String lastName, String address, Date birthDate, String photoName, 
			String login, String password, String email) {
		super(name, lastName, address, birthDate, photoName, login, password, email);
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	public Set<Tablet> getTablets() {
		return tablets;
	}

	public void setTablets(Set<Tablet> tablets) {
		this.tablets = tablets;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public Set<AttendantAlert> getAttendantAlerts() {
		return attendantAlerts;
	}

	public void setAttendantAlerts(Set<AttendantAlert> attendantAlerts) {
		this.attendantAlerts = attendantAlerts;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}
	
	public Collection<AttendantMedia> getAttendantMedia() {
		return attendantMedia;
	}

	public void setAttendantMedia(Collection<AttendantMedia> attendantMedia) {
		this.attendantMedia = attendantMedia;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((attendantAlerts == null) ? 0 : attendantAlerts.hashCode());
		result = prime * result
				+ ((attendantMedia == null) ? 0 : attendantMedia.hashCode());
		result = prime * result + ((device == null) ? 0 : device.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((orders == null) ? 0 : orders.hashCode());
		result = prime * result + ((roles == null) ? 0 : roles.hashCode());
		result = prime * result + ((tablets == null) ? 0 : tablets.hashCode());
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
		Attendant other = (Attendant) obj;
		if (attendantAlerts == null) {
			if (other.attendantAlerts != null)
				return false;
		} else if (!attendantAlerts.equals(other.attendantAlerts))
			return false;
		if (attendantMedia == null) {
			if (other.attendantMedia != null)
				return false;
		} else if (!attendantMedia.equals(other.attendantMedia))
			return false;
		if (device == null) {
			if (other.device != null)
				return false;
		} else if (!device.equals(other.device))
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
		if (tablets == null) {
			if (other.tablets != null)
				return false;
		} else if (!tablets.equals(other.tablets))
			return false;
		return true;
	}
}
