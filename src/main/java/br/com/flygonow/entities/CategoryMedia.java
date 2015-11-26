package br.com.flygonow.entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;

@Entity
@Table(name="CategoryMedia")
public class CategoryMedia implements Serializable{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6751257868641712996L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="categoryMedia_gen")	
	@SequenceGenerator(name = "categoryMedia_gen", sequenceName = "seq_categoryMedia")
	private Long id;
	
	@Column(name="media")
	@Lob
	private byte[] media;
	
	@Column(name="type")
	private byte type;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="categoryId", insertable=true, updatable=true, nullable = true)
	@Fetch(FetchMode.JOIN)
    private Category category;
	
	
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public CategoryMedia(){}
	
	public CategoryMedia(byte[] media){
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
				+ ((category == null) ? 0 : category.hashCode());
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
		CategoryMedia other = (CategoryMedia) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
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
