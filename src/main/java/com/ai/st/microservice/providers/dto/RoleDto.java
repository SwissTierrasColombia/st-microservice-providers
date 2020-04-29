package com.ai.st.microservice.providers.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "RoleDto", description = "Role Dto")
public class RoleDto implements Serializable {

	private static final long serialVersionUID = -8478165185005520773L;

	@ApiModelProperty(required = true, notes = "Role Id")
	private Long id;

	@ApiModelProperty(required = true, notes = "Role Id")
	private String name;

	public RoleDto() {

	}

	public RoleDto(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
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

}
