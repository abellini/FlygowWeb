package br.com.flygonow.entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;

@Entity
@Table(name="PromotionMedia")
public class PromotionMedia implements Serializable{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6751257868641712996L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="promotionMedia_gen")	
	@SequenceGenerator(name = "promotionMedia_gen", sequenceName = "seq_promotionMedia")
	private Long id;
	
	@Column(name="media")
	@Lob
	private byte[] media;
	
	@Column(name="type")
	private byte type;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="promotionId", insertable=true, updatable=true, nullable = true)
	@Fetch(FetchMode.JOIN)
    private Promotion promotion;
	
	
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


	public Promotion getPromotion() {
		return promotion;
	}

	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}

	public PromotionMedia(){}
	
	public PromotionMedia(byte[] media){
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + Arrays.hashCode(media);
		result = prime * result
				+ ((promotion == null) ? 0 : promotion.hashCode());
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
		PromotionMedia other = (PromotionMedia) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (!Arrays.equals(media, other.media))
			return false;
		if (promotion == null) {
			if (other.promotion != null)
				return false;
		} else if (!promotion.equals(other.promotion))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
}
