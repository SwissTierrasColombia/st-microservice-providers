package com.ai.st.microservice.providers.dto;

import java.io.Serializable;
import java.util.Date;

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

	@ApiModelProperty(required = true, notes = "Date creation")
	private Date createdAt;

	@ApiModelProperty(required = true, notes = "Is Delivered?")
	private Boolean delivered;

	@ApiModelProperty(required = true, notes = "Supply Requested State")
	private SupplyRequestedStateDto state;

	@ApiModelProperty(required = true, notes = "Date delivered")
	private Date deliveredAt;

	@ApiModelProperty(required = true, notes = "Justification")
	private String justification;
	
	@ApiModelProperty(required = true, notes = "Model version")
	private String modelVersion;

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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Boolean getDelivered() {
		return delivered;
	}

	public void setDelivered(Boolean delivered) {
		this.delivered = delivered;
	}

	public Date getDeliveredAt() {
		return deliveredAt;
	}

	public void setDeliveredAt(Date deliveredAt) {
		this.deliveredAt = deliveredAt;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	public SupplyRequestedStateDto getState() {
		return state;
	}

	public void setState(SupplyRequestedStateDto state) {
		this.state = state;
	}

	public String getModelVersion() {
		return modelVersion;
	}

	public void setModelVersion(String modelVersion) {
		this.modelVersion = modelVersion;
	}

}
