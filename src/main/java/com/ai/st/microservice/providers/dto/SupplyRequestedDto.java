package com.ai.st.microservice.providers.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "SupplyRequestedDto", description = "Supply Requested Dto")
public class SupplyRequestedDto implements Serializable {

	private static final long serialVersionUID = -5639936149692833884L;

	@ApiModelProperty(required = true, notes = "Supply Requested ID")
	private Long id;

	@ApiModelProperty(required = true, notes = "Description")
	private String description;

	@ApiModelProperty(required = true, notes = "Request")
	private RequestDto request;

	@ApiModelProperty(required = true, notes = "Type supply")
	private TypeSupplyDto typeSupply;

	public SupplyRequestedDto() {

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

	public RequestDto getRequest() {
		return request;
	}

	public void setRequest(RequestDto request) {
		this.request = request;
	}

	public TypeSupplyDto getTypeSupply() {
		return typeSupply;
	}

	public void setTypeSupply(TypeSupplyDto typeSupply) {
		this.typeSupply = typeSupply;
	}

}
