package com.ai.st.microservice.providers.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "RequestEmittertDto", description = "Request Emitter Dto")
public class RequestEmitterDto implements Serializable {

    private static final long serialVersionUID = 5475513453227858937L;

    @ApiModelProperty(required = true, notes = "Emmiter Type (ENTITY, USER)")
    private String emitterType;

    @ApiModelProperty(required = true, notes = "Emmiter code")
    private Long emitterCode;

    public RequestEmitterDto() {

    }

    public String getEmitterType() {
        return emitterType;
    }

    public void setEmitterType(String emitterType) {
        this.emitterType = emitterType;
    }

    public Long getEmitterCode() {
        return emitterCode;
    }

    public void setEmitterCode(Long emitterCode) {
        this.emitterCode = emitterCode;
    }

    @Override
    public String toString() {
        return "RequestEmitterDto{" + "emitterType='" + emitterType + '\'' + ", emitterCode=" + emitterCode + '}';
    }
}
