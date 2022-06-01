package com.ai.st.microservice.providers.dto;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "EmitterDto", description = "Emitter Dto")
public class EmitterDto implements Serializable {

    private static final long serialVersionUID = 2722894403417650386L;

    @ApiModelProperty(required = true, notes = "Emitter ID")
    private Long id;

    @ApiModelProperty(required = true, notes = "Date creation")
    private Date createdAt;

    @ApiModelProperty(required = true, notes = "Emitter code")
    private Long emitterCode;

    @ApiModelProperty(required = true, notes = "Emitter type")
    private String emitterType;

    public EmitterDto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getEmitterCode() {
        return emitterCode;
    }

    public void setEmitterCode(Long emitterCode) {
        this.emitterCode = emitterCode;
    }

    public String getEmitterType() {
        return emitterType;
    }

    public void setEmitterType(String emitterType) {
        this.emitterType = emitterType;
    }

}
