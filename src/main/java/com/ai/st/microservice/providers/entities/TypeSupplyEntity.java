package com.ai.st.microservice.providers.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
@Table(name = "types_supplies", schema = "providers")
public class TypeSupplyEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "name", nullable = false, length = 255)
	private String name;

	@Column(name = "description", nullable = true, length = 255)
	private String description;

	@Column(name = "metadata_required", nullable = false)
	private Boolean isMetadataRequired;

	@Column(name = "model_required", nullable = false)
	private Boolean isModelRequired;
	
	@Column(name = "active", nullable = false)
	private Boolean active;

	@Column(name = "created_at", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "provider_id", referencedColumnName = "id", nullable = false)
	private ProviderEntity provider;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "provider_profile_id", referencedColumnName = "id", nullable = false)
	private ProviderProfileEntity providerProfile;

	@OneToMany(mappedBy = "typeSupply")
	private List<ExtensionEntity> extensions = new ArrayList<ExtensionEntity>();

	public TypeSupplyEntity() {

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsMetadataRequired() {
		return isMetadataRequired;
	}

	public void setIsMetadataRequired(Boolean isMetadataRequired) {
		this.isMetadataRequired = isMetadataRequired;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public ProviderEntity getProvider() {
		return provider;
	}

	public void setProvider(ProviderEntity provider) {
		this.provider = provider;
	}

	public ProviderProfileEntity getProviderProfile() {
		return providerProfile;
	}

	public void setProviderProfile(ProviderProfileEntity providerProfile) {
		this.providerProfile = providerProfile;
	}

	public List<ExtensionEntity> getExtensions() {
		return extensions;
	}

	public void setExtensions(List<ExtensionEntity> extensions) {
		this.extensions = extensions;
	}

	public Boolean getIsModelRequired() {
		return isModelRequired;
	}

	public void setIsModelRequired(Boolean isModelRequired) {
		this.isModelRequired = isModelRequired;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}
