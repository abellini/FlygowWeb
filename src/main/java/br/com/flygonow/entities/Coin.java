package br.com.flygonow.entities;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name="Coin")
public class Coin implements Serializable{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6751257868641712996L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "moeda_gen")
	@SequenceGenerator(name = "moeda_gen", sequenceName = "seq_moeda")
	private Long id;
	
	@Column(name="symbol")
	private String symbol;
	
	@Column(name="name")
	private String name;
	
	@Column(name="conversion")
	private Double conversion;
	
	@OneToMany(mappedBy = "coin", fetch = FetchType.LAZY)
	@Cascade(CascadeType.SAVE_UPDATE)
	private Collection<Tablet> tablets;

	public Coin(){
		
	}
	
	public Coin(Long id, String name, String symbol, Double conversion){
		this.id = id;
		this.name = name;
		this.symbol = symbol;
		this.conversion = conversion;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getConversion() {
		return conversion;
	}

	public void setConversion(Double conversion) {
		this.conversion = conversion;
	}

	public Collection<Tablet> getTablets() {
		return tablets;
	}

	public void setTablets(Collection<Tablet> tablets) {
		this.tablets = tablets;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((conversion == null) ? 0 : conversion.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
		result = prime * result + ((tablets == null) ? 0 : tablets.hashCode());
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
		Coin other = (Coin) obj;
		if (conversion == null) {
			if (other.conversion != null)
				return false;
		} else if (!conversion.equals(other.conversion))
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
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equals(other.symbol))
			return false;
		if (tablets == null) {
			if (other.tablets != null)
				return false;
		} else if (!tablets.equals(other.tablets))
			return false;
		return true;
	}
}
