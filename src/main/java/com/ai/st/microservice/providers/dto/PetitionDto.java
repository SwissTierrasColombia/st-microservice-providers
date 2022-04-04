package com.ai.st.microservice.providers.dto;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "PetitionDto")
public class PetitionDto implements Serializable {

    private static final long serialVersionUID = 6001978070953258795L;

    @ApiModelProperty(required = true, notes = "Petition ID")
    private Long id;

    @ApiModelProperty(required = true, notes = "Date creation")
    private Date createdAt;

    @ApiModelProperty(required = true, notes = "Manager Code")
    private Long managerCode;

    @ApiModelProperty(required = true, notes = "Observations")
    private String observations;

    @ApiModelProperty(required = false, notes = "Justification")
    private String justification;

    @ApiModelProperty(required = true, notes = "Petition State")
    private PetitionStateDto petitionState;

    @ApiModelProperty(required = true, notes = "Provider")
    private ProviderDto provider;

    public PetitionDto() {

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

    public Long getManagerCode() {
        return managerCode;
    }

    public void setManagerCode(Long managerCode) {
        this.managerCode = managerCode;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public PetitionStateDto getPetitionState() {
        return petitionState;
    }

    public void setPetitionState(PetitionStateDto petitionState) {
        this.petitionState = petitionState;
    }

    public ProviderDto getProvider() {
        return provider;
    }

    public void setProvider(ProviderDto provider) {
        this.provider = provider;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

}
