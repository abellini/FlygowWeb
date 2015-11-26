package br.com.flygonow.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "Advertisement")
public class Advertisement implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6071807400163006238L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="advertisement_gen")	
	@SequenceGenerator(name = "advertisement_gen", sequenceName = "seq_advertisement")
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="inicialDate")
	@Temporal(TemporalType.DATE)
	private Date inicialDate;
	
	@Column(name="finalDate")
	@Temporal(TemporalType.DATE)
	private Date finalDate;
	
	@Column(name="isActive")
	private boolean isActive;
	
	@Column(name="photoName")
	private String photoName;
	
	@Column(name="videoName")
	private String videoName;
	
	@ManyToMany(fetch=FetchType.LAZY, mappedBy="advertisements")
	private Collection<Tablet> tablets = new ArrayList<Tablet>();

	@OneToMany(mappedBy="advertisement", fetch = LAZY, cascade = ALL)    
    private Collection<AdvertisementMedia> advertisementMedia = new ArrayList<AdvertisementMedia>();
	
	public Advertisement(Long id, String name, Date inicialDate, Date finalDate, boolean active, String photoName, String videoName){
		this.id = id;
		this.name = name;
		this.inicialDate = inicialDate;
		this.finalDate = finalDate;
		this.isActive = active;
		this.photoName = photoName;
		this.videoName = videoName;
	}
	
	public Advertisement(){
	}
	
	public Advertisement(String mediaName){
		setVideoName(mediaName);
		setPhotoName(mediaName);
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

	public Collection<Tablet> getTablets() {
		return tablets;
	}

	public void setTablets(Collection<Tablet> tablets) {
		this.tablets = tablets;
	}

	public Date getInicialDate() {
		return inicialDate;
	}

	public void setInicialDate(Timestamp inicialDate) {
		this.inicialDate = inicialDate;
	}

	public Date getFinalDate() {
		return finalDate;
	}

	public void setFinalDate(Timestamp finalDate) {
		this.finalDate = finalDate;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getPhotoName() {
		return photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public Collection<AdvertisementMedia> getAdvertisementMedia() {
		return advertisementMedia;
	}

	public void setAdvertisementMedia(
			Collection<AdvertisementMedia> advertisementMedia) {
		this.advertisementMedia = advertisementMedia;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((advertisementMedia == null) ? 0 : advertisementMedia
						.hashCode());
		result = prime * result
				+ ((finalDate == null) ? 0 : finalDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((inicialDate == null) ? 0 : inicialDate.hashCode());
		result = prime * result + (isActive ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((photoName == null) ? 0 : photoName.hashCode());
		result = prime * result + ((tablets == null) ? 0 : tablets.hashCode());
		result = prime * result
				+ ((videoName == null) ? 0 : videoName.hashCode());
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
		Advertisement other = (Advertisement) obj;
		if (advertisementMedia == null) {
			if (other.advertisementMedia != null)
				return false;
		} else if (!advertisementMedia.equals(other.advertisementMedia))
			return false;
		if (finalDate == null) {
			if (other.finalDate != null)
				return false;
		} else if (!finalDate.equals(other.finalDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (inicialDate == null) {
			if (other.inicialDate != null)
				return false;
		} else if (!inicialDate.equals(other.inicialDate))
			return false;
		if (isActive != other.isActive)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (photoName == null) {
			if (other.photoName != null)
				return false;
		} else if (!photoName.equals(other.photoName))
			return false;
		if (tablets == null) {
			if (other.tablets != null)
				return false;
		} else if (!tablets.equals(other.tablets))
			return false;
		if (videoName == null) {
			if (other.videoName != null)
				return false;
		} else if (!videoName.equals(other.videoName))
			return false;
		return true;
	}
}