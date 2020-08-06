package com.ai.st.microservice.providers.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "PetitionStateDto")
public class PetitionStateDto implements Serializable {

	private static final long serialVersionUID = -945178753218052140L;

	@ApiModelProperty(required = true, notes = "Petition State ID")
	private Long id;

	@ApiModelProperty(required = true, notes = "Name")
	private String name;

	public PetitionStateDto() {

	}

	public PetitionStateDto(Long id, String name) {
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
