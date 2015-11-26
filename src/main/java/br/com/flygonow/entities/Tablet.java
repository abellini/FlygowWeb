package br.com.flygonow.entities;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Tablet")
public class Tablet implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 565191016621989298L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="tablet_gen")	
	@SequenceGenerator(name = "tablet_gen", sequenceName = "seq_tablet")
	private Long id;

	@Column(name="serverIP")
	private String serverIP;
	
	@Column(name="serverPort")
	private int serverPort;
	
	@Column(name="ip")
	private String ip;
	
	@Column(name="port")
	private int port;
	
	@Column(name="number")
	private Short number;
	
	@OneToMany(mappedBy = "tablet", fetch = FetchType.LAZY)
	@Cascade(value={CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	private Set<Order> orders;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="coinId", insertable=true, updatable=true)
	@Fetch(FetchMode.JOIN)
	private Coin coin;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="statusId", insertable=true, updatable=true)
	@Fetch(FetchMode.JOIN)
	private TabletStatus serviceStatus;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="attendantId", insertable=true, updatable=true)
	@Fetch(FetchMode.JOIN)
	private Attendant attendant;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="Tablet_Advertisement", joinColumns={@JoinColumn(name="tabletId")}, 
				inverseJoinColumns={@JoinColumn(name="advertisementId")})
	@Cascade(value={CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	private Set<Advertisement> advertisements = new HashSet<Advertisement>();
	
	public Tablet(){}

	public Tablet(Long id, Short number, String ip, int port, String serverIP, int serverPort) {
		this.id = id;
		this.serverIP = serverIP;
		this.serverPort = serverPort;
		this.ip = ip;
		this.port = port;
		this.number = number;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getServerIP() {
		return serverIP;
	}

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Short getNumber() {
		return number;
	}

	public void setNumber(Short number) {
		this.number = number;
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	public Attendant getAttendant() {
		return attendant;
	}

	public void setAttendant(Attendant attendant) {
		this.attendant = attendant;
	}

	public Coin getCoin() {
		return coin;
	}

	public void setCoin(Coin coin) {
		this.coin = coin;
	}

	public Set<Advertisement> getAdvertisements() {
		return advertisements;
	}

	public void setAdvertisements(Set<Advertisement> advertisements) {
		this.advertisements = advertisements;
	}

	public TabletStatus getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(TabletStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((advertisements == null) ? 0 : advertisements.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + number;
		result = prime * result + ((orders == null) ? 0 : orders.hashCode());
		result = prime * result + port;
		result = prime * result
				+ ((serverIP == null) ? 0 : serverIP.hashCode());
		result = prime * result + serverPort;
		result = prime * result
				+ ((serviceStatus == null) ? 0 : serviceStatus.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Tablet)) {
			return false;
		}
		Tablet other = (Tablet) obj;
		if (advertisements == null) {
			if (other.advertisements != null) {
				return false;
			}
		} else if (!advertisements.equals(other.advertisements)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (ip == null) {
			if (other.ip != null) {
				return false;
			}
		} else if (!ip.equals(other.ip)) {
			return false;
		}
		if (number != other.number) {
			return false;
		}
		if (orders == null) {
			if (other.orders != null) {
				return false;
			}
		} else if (!orders.equals(other.orders)) {
			return false;
		}
		if (port != other.port) {
			return false;
		}
		if (serverIP == null) {
			if (other.serverIP != null) {
				return false;
			}
		} else if (!serverIP.equals(other.serverIP)) {
			return false;
		}
		if (serverPort != other.serverPort) {
			return false;
		}
		if (serviceStatus == null) {
			if (other.serviceStatus != null) {
				return false;
			}
		} else if (!serviceStatus.equals(other.serviceStatus)) {
			return false;
		}
		return true;
	}
}
