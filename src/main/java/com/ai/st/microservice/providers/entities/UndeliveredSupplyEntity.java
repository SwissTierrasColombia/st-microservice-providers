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
@Table(name = "undelivered_supplies", schema = "providers")
public class UndeliveredSupplyEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "reason", nullable = false, length = 255)
	private String reason;

	@Column(name = "created_at", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "request_id", referencedColumnName = "id", nullable = false)
	private RequestEntity request;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type_supply_id", referencedColumnName = "id", nullable = false)
	private TypeSupplyEntity typeSupply;

	public UndeliveredSupplyEntity() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public RequestEntity getRequest() {
		return request;
	}

	public void setRequest(RequestEntity request) {
		this.request = request;
	}

	public TypeSupplyEntity getTypeSupply() {
		return typeSupply;
	}

	public void setTypeSupply(TypeSupplyEntity typeSupply) {
		this.typeSupply = typeSupply;
	}

}
