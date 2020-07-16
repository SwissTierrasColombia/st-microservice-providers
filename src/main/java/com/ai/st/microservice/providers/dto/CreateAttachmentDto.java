package com.ai.st.microservice.providers.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "CreateAttachmentDto")
public class CreateAttachmentDto implements Serializable {

	private static final long serialVersionUID = -5683375812731682537L;

	@ApiModelProperty(required = true, notes = "Boundary Id")
	private Long boundaryId;

	@ApiModelProperty(required = true, notes = "File url")
	private String fileUrl;

	@ApiModelProperty(required = true, notes = "Created by")
	private Long createdBy;

	public CreateAttachmentDto() {
	}

	public Long getBoundaryId() {
		return boundaryId;
	}

	public void setBoundaryId(Long boundaryId) {
		this.boundaryId = boundaryId;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

}
