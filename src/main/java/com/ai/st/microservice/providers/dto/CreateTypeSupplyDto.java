package com.ai.st.microservice.providers.dto;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "CreateTypeSupplyDto", description = "Create Type Supply Dto")
public class CreateTypeSupplyDto implements Serializable {

	private static final long serialVersionUID = 6515735489433109152L;

	@ApiModelProperty(required = true, notes = "Type supply name")
	private String name;
	
	@ApiModelProperty(required = false, notes = "Type supply description")
	private String description;
	
	@ApiModelProperty(required = false, notes = "Type supply description")
	private Date createdAt;
	
	@ApiModelProperty(required = true, notes = "Metadata required description")
	private Boolean metadataRequired;

	@ApiModelProperty(required = false, notes = "Provider ID")
	private Long providerId;
	
	@ApiModelProperty(required = false, notes = "Provider profile ID")
	private Long providerProfileId;
	

	public CreateTypeSupplyDto() {

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


	public Date getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}


	public Long getProviderId() {
		return providerId;
	}


	public void setProviderId(Long providerId) {
		this.providerId = providerId;
	}


	public Long getProviderProfileId() {
		return providerProfileId;
	}


	public void setProviderProfileId(Long providerProfileId) {
		this.providerProfileId = providerProfileId;
	}


	public Boolean getMetadataRequired() {
		return metadataRequired;
	}


	public void setMetadataRequired(Boolean metadataRequired) {
		this.metadataRequired = metadataRequired;
	}
	

}
