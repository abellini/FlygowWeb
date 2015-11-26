package br.com.flygonow.entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;

@Entity
@Table(name="AdvertisementMedia")
public class AdvertisementMedia implements Serializable{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6751257868641712996L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="advertisementMedia_gen")	
	@SequenceGenerator(name = "advertisementMedia_gen", sequenceName = "seq_advertisementMedia")
	private Long id;
	
	@Column(name="media")
	@Lob
	private byte[] media;
	
	@Column(name="type")
	private byte type;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="advertisementId", insertable=true, updatable=true, nullable = true)
	@Fetch(FetchMode.JOIN)
    private Advertisement advertisement;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public byte[] getMedia() {
		return media;
	}

	public void setMedia(byte[] media) {
		this.media = media;
	}

	public Advertisement getAdvertisement() {
		return advertisement;
	}

	public void setAdvertisement(Advertisement advertisement) {
		this.advertisement = advertisement;
	}

	public AdvertisementMedia(){}
	
	public AdvertisementMedia(byte[] media){
		setMedia(media);
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((advertisement == null) ? 0 : advertisement.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + Arrays.hashCode(media);
		result = prime * result + type;
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
		AdvertisementMedia other = (AdvertisementMedia) obj;
		if (advertisement == null) {
			if (other.advertisement != null)
				return false;
		} else if (!advertisement.equals(other.advertisement))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (!Arrays.equals(media, other.media))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
}
