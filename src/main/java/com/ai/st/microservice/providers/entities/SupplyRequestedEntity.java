package com.ai.st.microservice.providers.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "supplies_requested", schema = "providers")
public class SupplyRequestedEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "description", nullable = true, length = 500)
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type_supply_id", referencedColumnName = "id", nullable = false)
	private TypeSupplyEntity typeSupply;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "request_id", referencedColumnName = "id", nullable = false)
	private RequestEntity request;

	@Column(name = "delivered", nullable = false)
	private Boolean delivered;

	@Column(name = "created_at", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@Column(name = "delivered_at", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date deliveredAt;

	@Column(name = "justification", nullable = true, length = 500)
	private String justification;

	public SupplyRequestedEntity() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TypeSupplyEntity getTypeSupply() {
		return typeSupply;
	}

	public void setTypeSupply(TypeSupplyEntity typeSupply) {
		this.typeSupply = typeSupply;
	}

	public RequestEntity getRequest() {
		return request;
	}

	public void setRequest(RequestEntity request) {
		this.request = request;
	}

	public Boolean getDelivered() {
		return delivered;
	}

	public void setDelivered(Boolean delivered) {
		this.delivered = delivered;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getDeliveredAt() {
		return deliveredAt;
	}

	public void setDeliveredAt(Date deliveredAt) {
		this.deliveredAt = deliveredAt;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

}
