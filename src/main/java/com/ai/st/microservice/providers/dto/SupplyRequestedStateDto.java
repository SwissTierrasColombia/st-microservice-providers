package com.ai.st.microservice.providers.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "SupplyRequestedStateDto", description = "Supply Requested State Dto")
public class SupplyRequestedStateDto implements Serializable {

    private static final long serialVersionUID = 1008543219005393218L;

    @ApiModelProperty(required = true, notes = "Supply Requested State ID")
    private Long id;

    @ApiModelProperty(required = true, notes = "Name")
    private String name;

    public SupplyRequestedStateDto() {

    }

    public SupplyRequestedStateDto(Long id, String name) {
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
