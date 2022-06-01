package com.ai.st.microservice.providers.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "RequestStateDto", description = "Request State Dto")
public class RequestStateDto implements Serializable {

    private static final long serialVersionUID = -2792776775360315658L;

    @ApiModelProperty(required = true, notes = "Request State ID")
    private Long id;

    @ApiModelProperty(required = true, notes = "Request State name")
    private String name;

    public RequestStateDto() {

    }

    public RequestStateDto(Long id, String name) {
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
