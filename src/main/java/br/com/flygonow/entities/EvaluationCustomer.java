package br.com.flygonow.entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="EvaluationCustomer")
public class EvaluationCustomer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4841512658979573033L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "evaluation_customer_gen")
	@SequenceGenerator(name = "evaluation_customer_gen", sequenceName = "seq_evaluation_customer")
	private Long id;
	
	@Column(name="score")
	private Double score;
	
	@Column(name="evaluationDate")
	@Temporal(TemporalType.DATE)
	private Date evaluationDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="clientId", insertable=false, updatable=false)
	@Fetch(FetchMode.JOIN)
	private Client client;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Date getEvaluationDate() {
		return evaluationDate;
	}

	public void setEvaluationDate(Date evaluationDate) {
		this.evaluationDate = evaluationDate;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((client == null) ? 0 : client.hashCode());
		result = prime * result
				+ ((evaluationDate == null) ? 0 : evaluationDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((score == null) ? 0 : score.hashCode());
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
		EvaluationCustomer other = (EvaluationCustomer) obj;
		if (client == null) {
			if (other.client != null)
				return false;
		} else if (!client.equals(other.client))
			return false;
		if (evaluationDate == null) {
			if (other.evaluationDate != null)
				return false;
		} else if (!evaluationDate.equals(other.evaluationDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (score == null) {
			if (other.score != null)
				return false;
		} else if (!score.equals(other.score))
			return false;
		return true;
	}
}
