package com.ai.st.microservice.providers.dto;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "TypeSupplyDto", description = "Type Supply Dto")
public class TypeSupplyDto implements Serializable {

	private static final long serialVersionUID = 3977770440374512592L;

	@ApiModelProperty(required = true, notes = "Type Supply ID")
	private Long id;

	@ApiModelProperty(required = true, notes = "Date creation")
	private Date createdAt;

	@ApiModelProperty(required = true, notes = "Description")
	private String description;

	@ApiModelProperty(required = true, notes = "Metadata is required ?")
	private Boolean metadataRequired;

	@ApiModelProperty(required = true, notes = "Type supply name")
	private String name;

	@ApiModelProperty(required = true, notes = "Provider")
	private ProviderDto provider;

	@ApiModelProperty(required = true, notes = "Provider profile")
	private ProviderProfileDto providerProfile;

	public TypeSupplyDto() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getMetadataRequired() {
		return metadataRequired;
	}

	public void setMetadataRequired(Boolean metadataRequired) {
		this.metadataRequired = metadataRequired;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProviderDto getProvider() {
		return provider;
	}

	public void setProvider(ProviderDto provider) {
		this.provider = provider;
	}

	public ProviderProfileDto getProviderProfile() {
		return providerProfile;
	}

	public void setProviderProfile(ProviderProfileDto providerProfile) {
		this.providerProfile = providerProfile;
	}

}
