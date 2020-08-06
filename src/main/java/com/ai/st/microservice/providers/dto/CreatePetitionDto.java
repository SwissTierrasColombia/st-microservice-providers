package com.ai.st.microservice.providers.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "CreatePetitionDto")
public class CreatePetitionDto implements Serializable {

	private static final long serialVersionUID = -1257173806267524291L;

	@ApiModelProperty(required = true, notes = "Observations")
	private String observations;

	@ApiModelProperty(required = true, notes = "Manager Code")
	private Long managerCode;

	public CreatePetitionDto() {

	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public Long getManagerCode() {
		return managerCode;
	}

	public void setManagerCode(Long managerCode) {
		this.managerCode = managerCode;
	}

}
