package br.com.flygonow.entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "AttendantAlert")
public class AttendantAlert implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4749351483482730241L;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="attendant_alert_gen")	
	@SequenceGenerator(name = "attendant_alert_gen", sequenceName = "seq_attendant_alert")
	private Long id;
	
	@Column(name="tabletNumber")
	private int tabletNumber;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="attendantId", insertable=true, updatable=false)
	@Fetch(FetchMode.JOIN)
	private Attendant attendant;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="deviceId", insertable=true, updatable=false)
	@Fetch(FetchMode.JOIN)
	private Device device;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="alerts", insertable=true, updatable=false)
	@Fetch(FetchMode.JOIN)
	private AlertMessageType type;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="alertStatus", insertable=true, updatable=true)
	@Fetch(FetchMode.JOIN)
	private AlertMessageStatus status;

	@Column(name="alertHour")
	@Temporal(TemporalType.TIMESTAMP)
	private Date alertHour;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getTabletNumber() {
		return tabletNumber;
	}

	public void setTabletNumber(int tabletNumber) {
		this.tabletNumber = tabletNumber;
	}

	public Attendant getAttendant() {
		return attendant;
	}

	public void setAttendant(Attendant attendant) {
		this.attendant = attendant;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public AlertMessageType getType() {
		return type;
	}

	public void setType(AlertMessageType type) {
		this.type = type;
	}
	
	public AlertMessageStatus getStatus() {
		return status;
	}

	public void setStatus(AlertMessageStatus status) {
		this.status = status;
	}

	public Date getAlertHour() {
		return alertHour;
	}

	public void setAlertHour(Date alertHour) {
		this.alertHour = alertHour;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((alertHour == null) ? 0 : alertHour.hashCode());
		result = prime * result
				+ ((attendant == null) ? 0 : attendant.hashCode());
		result = prime * result + ((device == null) ? 0 : device.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + tabletNumber;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		AttendantAlert other = (AttendantAlert) obj;
		if (alertHour == null) {
			if (other.alertHour != null)
				return false;
		} else if (!alertHour.equals(other.alertHour))
			return false;
		if (attendant == null) {
			if (other.attendant != null)
				return false;
		} else if (!attendant.equals(other.attendant))
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
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (tabletNumber != other.tabletNumber)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
}
