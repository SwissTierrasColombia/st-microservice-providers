package com.ai.st.microservice.providers.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "ProviderUserDto", description = "Provider User Dto")
public class ProviderUserDto implements Serializable {

	private static final long serialVersionUID = 6492877134122226839L;

	@ApiModelProperty(required = true, notes = "User code")
	private Long userCode;

	@ApiModelProperty(required = true, notes = "Profiles")
	private List<ProviderProfileDto> profiles;

	public ProviderUserDto() {
		this.profiles = new ArrayList<ProviderProfileDto>();
	}

	public Long getUserCode() {
		return userCode;
	}

	public void setUserCode(Long userCode) {
		this.userCode = userCode;
	}

	public List<ProviderProfileDto> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<ProviderProfileDto> profiles) {
		this.profiles = profiles;
	}

}
