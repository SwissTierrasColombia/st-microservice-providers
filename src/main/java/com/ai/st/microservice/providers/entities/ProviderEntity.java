package com.ai.st.microservice.providers.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "providers", schema = "providers")
public class ProviderEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "name", nullable = false, length = 255)
	private String name;

	@Column(name = "tax_identification_number", nullable = false, length = 255)
	private String taxIdentificationNumber;

	@Column(name = "created_at", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "provider_category_id", referencedColumnName = "id", nullable = false)
	private ProviderCategoryEntity providerCategory;

	@OneToMany(mappedBy = "provider", cascade = CascadeType.ALL)
	private List<TypeSupplyEntity> typesSupplies = new ArrayList<TypeSupplyEntity>();

	@OneToMany(mappedBy = "provider")
	private List<RequestEntity> requests = new ArrayList<RequestEntity>();

	public ProviderEntity() {

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

	public String getTaxIdentificationNumber() {
		return taxIdentificationNumber;
	}

	public void setTaxIdentificationNumber(String taxIdentificationNumber) {
		this.taxIdentificationNumber = taxIdentificationNumber;
	}

	public ProviderCategoryEntity getProviderCategory() {
		return providerCategory;
	}

	public void setProviderCategory(ProviderCategoryEntity providerCategory) {
		this.providerCategory = providerCategory;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public List<TypeSupplyEntity> getTypesSupplies() {
		return typesSupplies;
	}

	public void setTypesSupplies(List<TypeSupplyEntity> typesSupplies) {
		this.typesSupplies = typesSupplies;
	}

	public List<RequestEntity> getRequests() {
		return requests;
	}

	public void setRequests(List<RequestEntity> requests) {
		this.requests = requests;
	}

}
