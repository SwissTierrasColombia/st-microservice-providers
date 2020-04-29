package com.ai.st.microservice.providers.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "ProviderAdministratorDto", description = "Provider Administrator Dto")
public class ProviderAdministratorDto implements Serializable {

	private static final long serialVersionUID = -4177372978182201398L;

	@ApiModelProperty(required = true, notes = "User code")
	private Long userCode;

	@ApiModelProperty(required = true, notes = "Roles")
	private List<RoleDto> roles;

	public ProviderAdministratorDto() {
		this.roles = new ArrayList<RoleDto>();
	}

	public Long getUserCode() {
		return userCode;
	}

	public void setUserCode(Long userCode) {
		this.userCode = userCode;
	}

	public List<RoleDto> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleDto> roles) {
		this.roles = roles;
	}

}
