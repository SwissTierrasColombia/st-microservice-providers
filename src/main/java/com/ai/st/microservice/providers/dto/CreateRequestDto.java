package com.ai.st.microservice.providers.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "CreateRequestDto", description = "Create Request Dto")
public class CreateRequestDto implements Serializable {

    private static final long serialVersionUID = 1848871808401415553L;

    @ApiModelProperty(required = true, notes = "Deadline")
    private String deadline;

    @ApiModelProperty(required = true, notes = "Provider ID")
    private Long providerId;

    @ApiModelProperty(required = true, notes = "Municipality Code")
    private String municipalityCode;

    @ApiModelProperty(required = true, notes = "Package")
    private String packageLabel;

    @ApiModelProperty(required = true, notes = "Emitters")
    private List<RequestEmitterDto> emitters;

    @ApiModelProperty(required = true, notes = "Supplies requested")
    private List<TypeSupplyRequestedDto> supplies;

    public CreateRequestDto() {
        supplies = new ArrayList<TypeSupplyRequestedDto>();
        emitters = new ArrayList<RequestEmitterDto>();
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public List<TypeSupplyRequestedDto> getSupplies() {
        return supplies;
    }

    public void setSupplies(List<TypeSupplyRequestedDto> supplies) {
        this.supplies = supplies;
    }

    public List<RequestEmitterDto> getEmitters() {
        return emitters;
    }

    public void setEmitters(List<RequestEmitterDto> emitters) {
        this.emitters = emitters;
    }

    public String getMunicipalityCode() {
        return municipalityCode;
    }

    public void setMunicipalityCode(String municipalityCode) {
        this.municipalityCode = municipalityCode;
    }

    public String getPackageLabel() {
        return packageLabel;
    }

    public void setPackageLabel(String packageLabel) {
        this.packageLabel = packageLabel;
    }

}
