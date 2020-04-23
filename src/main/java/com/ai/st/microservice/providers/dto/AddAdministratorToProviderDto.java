package com.ai.st.microservice.providers.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "AddAdministratorToProviderDto", description = "Add Administrator To Provider Dto")
public class AddAdministratorToProviderDto implements Serializable {

	private static final long serialVersionUID = -5241995478281401583L;

	@ApiModelProperty(required = true, notes = "User code")
	private Long userCode;

	@ApiModelProperty(required = true, notes = "Provider ID")
	private Long providerId;

	@ApiModelProperty(required = true, notes = "Role ID")
	private Long roleId;

	public AddAdministratorToProviderDto() {

	}

	public Long getUserCode() {
		return userCode;
	}

	public void setUserCode(Long userCode) {
		this.userCode = userCode;
	}

	public Long getProviderId() {
		return providerId;
	}

	public void setProviderId(Long providerId) {
		this.providerId = providerId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

}
