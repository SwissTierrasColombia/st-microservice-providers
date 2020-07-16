package com.ai.st.microservice.providers.dto;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "AttachmentDto")
public class AttachmentDto implements Serializable {

	private static final long serialVersionUID = 3495734410935760225L;

	@ApiModelProperty(required = true, notes = "Attachament Id")
	private Long id;

	@ApiModelProperty(required = true, notes = "Attachament Id")
	private SupplyRequestedDto supplyRequested;

	@ApiModelProperty(required = true, notes = "File Url")
	private String fileUrl;

	@ApiModelProperty(required = true, notes = "Boundary ID")
	private Long boundaryId;

	@ApiModelProperty(required = true, notes = "Created At")
	private Date createdAt;

	@ApiModelProperty(required = true, notes = "Created by")
	private Long createdBy;

	public AttachmentDto() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SupplyRequestedDto getSupplyRequested() {
		return supplyRequested;
	}

	public void setSupplyRequested(SupplyRequestedDto supplyRequested) {
		this.supplyRequested = supplyRequested;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public Long getBoundaryId() {
		return boundaryId;
	}

	public void setBoundaryId(Long boundaryId) {
		this.boundaryId = boundaryId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

}
