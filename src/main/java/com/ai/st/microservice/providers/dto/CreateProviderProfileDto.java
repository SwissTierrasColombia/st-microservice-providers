package com.ai.st.microservice.providers.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "CreateProviderProfileDto", description = "Create Provider Profile Dto")
public class CreateProviderProfileDto implements Serializable {

	private static final long serialVersionUID = 6515735489433109151L;

	@ApiModelProperty(required = true, notes = "Provider name")
	private String name;
	
	@ApiModelProperty(required = false, notes = "Provider description")
	private String description;

	@ApiModelProperty(required = false, notes = "Provider ID")
	private Long providerId;

	public CreateProviderProfileDto() {

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

	public Long getProviderId() {
		return providerId;
	}

	public void setProviderId(Long providerId) {
		this.providerId = providerId;
	}

}
