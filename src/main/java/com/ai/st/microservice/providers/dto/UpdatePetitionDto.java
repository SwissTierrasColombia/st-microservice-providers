package com.ai.st.microservice.providers.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "UpdatePetitionDto")
public class UpdatePetitionDto implements Serializable {

	private static final long serialVersionUID = -3550075435340827828L;

	@ApiModelProperty(required = true, notes = "Petition State ID")
	private Long petitionStateId;

	@ApiModelProperty(required = true, notes = "Justification")
	private String justitication;

	public UpdatePetitionDto() {

	}

	public Long getPetitionStateId() {
		return petitionStateId;
	}

	public void setPetitionStateId(Long petitionStateId) {
		this.petitionStateId = petitionStateId;
	}

	public String getJustitication() {
		return justitication;
	}

	public void setJustitication(String justitication) {
		this.justitication = justitication;
	}

}
