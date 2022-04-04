package com.ai.st.microservice.providers.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "CreateProviderProfileDto", description = "Create Provider Profile Dto")
public class CreateProviderProfileDto implements Serializable {

    private static final long serialVersionUID = 6515735489433109151L;

    @ApiModelProperty(required = true, notes = "Provider name")
    private String name;

    @ApiModelProperty(notes = "Provider description")
    private String description;

    public CreateProviderProfileDto() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "CreateProviderProfileDto{" + "name='" + name + '\'' + ", description='" + description + '\'' + '}';
    }
}
