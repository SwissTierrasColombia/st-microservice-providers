package com.ai.st.microservice.providers.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "supplies_requested", schema = "providers")
public class SupplyRequestedEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "description", nullable = true, length = 255)
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type_supply_id", referencedColumnName = "id", nullable = false)
	private TypeSupplyEntity typeSupply;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "request_id", referencedColumnName = "id", nullable = false)
	private RequestEntity request;

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

}
