package com.ai.st.microservice.providers.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "ProviderCategoryDto", description = "Provider Category Dto")
public class ProviderCategoryDto implements Serializable {

    private static final long serialVersionUID = -5302157804880208335L;

    @ApiModelProperty(required = true, notes = "Category ID")
    private Long id;

    @ApiModelProperty(required = true, notes = "Category name")
    private String name;

    public ProviderCategoryDto() {

    }

    public ProviderCategoryDto(Long id, String name) {
        super();
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
