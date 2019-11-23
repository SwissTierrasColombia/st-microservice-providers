package com.ai.st.microservice.providers.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "ProviderProfileDto", description = "Profile Profile Dto")
public class ProviderProfileDto implements Serializable {

	private static final long serialVersionUID = -557539358130899883L;

	@ApiModelProperty(required = true, notes = "Provider Profile ID")
	private Long id;

	@ApiModelProperty(required = true, notes = "Description")
	private String description;

	@ApiModelProperty(required = true, notes = "Name")
	private String name;

	@ApiModelProperty(required = true, notes = "Provider")
	private ProviderDto provider;

	public ProviderProfileDto() {

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

}
