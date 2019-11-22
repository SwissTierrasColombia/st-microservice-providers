package com.ai.st.microservice.providers.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "TypeSupplyRequestDto", description = "Type Supply Requested Dto")
public class TypeSupplyRequestedDto implements Serializable {

	private static final long serialVersionUID = -5598899972451538583L;

	@ApiModelProperty(required = true, notes = "Deadline")
	private Long typeSupplyId;

	@ApiModelProperty(required = false, notes = "Observation")
	private String observation;

	public TypeSupplyRequestedDto() {

	}

	public Long getTypeSupplyId() {
		return typeSupplyId;
	}

	public void setTypeSupplyId(Long typeSupplyId) {
		this.typeSupplyId = typeSupplyId;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

}
