package com.ai.st.microservice.providers.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "RequestDto", description = "Request Dto")
public class RequestDto implements Serializable {

	private static final long serialVersionUID = 18861947628131335L;

	@ApiModelProperty(required = true, notes = "Request ID")
	private Long id;

	@ApiModelProperty(required = true, notes = "Date creation")
	private Date createdAt;

	@ApiModelProperty(required = true, notes = "Deadline")
	private Date deadline;

	@ApiModelProperty(required = true, notes = "Observations")
	private String observations;

	@ApiModelProperty(required = true, notes = "Package")
	private String packageLabel;

	@ApiModelProperty(required = true, notes = "Provider")
	private ProviderDto provider;

	@ApiModelProperty(required = true, notes = "Request state")
	private RequestStateDto requestState;

	@ApiModelProperty(required = true, notes = "List supplies requested")
	private List<SupplyRequestedDto> suppliesRequested;

	@ApiModelProperty(required = true, notes = "List emitters")
	private List<EmitterDto> emitters;

	@ApiModelProperty(required = true, notes = "Municipality code")
	private String municipalityCode;

	@ApiModelProperty(required = true, notes = "Date closed")
	private Date closedAt;

	@ApiModelProperty(required = true, notes = "User who closed the request")
	private Long closedBy;

	public RequestDto() {
		suppliesRequested = new ArrayList<SupplyRequestedDto>();
		emitters = new ArrayList<EmitterDto>();
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

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public ProviderDto getProvider() {
		return provider;
	}

	public void setProvider(ProviderDto provider) {
		this.provider = provider;
	}

	public RequestStateDto getRequestState() {
		return requestState;
	}

	public void setRequestState(RequestStateDto requestState) {
		this.requestState = requestState;
	}

	public List<SupplyRequestedDto> getSuppliesRequested() {
		return suppliesRequested;
	}

	public void setSuppliesRequested(List<SupplyRequestedDto> suppliesRequested) {
		this.suppliesRequested = suppliesRequested;
	}

	public List<EmitterDto> getEmitters() {
		return emitters;
	}

	public void setEmitters(List<EmitterDto> emitters) {
		this.emitters = emitters;
	}

	public String getMunicipalityCode() {
		return municipalityCode;
	}

	public void setMunicipalityCode(String municipalityCode) {
		this.municipalityCode = municipalityCode;
	}

	public String getPackageLabel() {
		return packageLabel;
	}

	public void setPackageLabel(String packageLabel) {
		this.packageLabel = packageLabel;
	}

	public Date getClosedAt() {
		return closedAt;
	}

	public void setClosedAt(Date closedAt) {
		this.closedAt = closedAt;
	}

	public Long getClosedBy() {
		return closedBy;
	}

	public void setClosedBy(Long closedBy) {
		this.closedBy = closedBy;
	}

}
