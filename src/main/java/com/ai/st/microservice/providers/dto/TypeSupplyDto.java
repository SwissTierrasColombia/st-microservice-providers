package com.ai.st.microservice.providers.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "TypeSupplyDto", description = "Type Supply Dto")
public class TypeSupplyDto implements Serializable {

    private static final long serialVersionUID = 3977770440374512592L;

    @ApiModelProperty(required = true, notes = "Type Supply ID")
    private Long id;

    @ApiModelProperty(required = true, notes = "Date creation")
    private Date createdAt;

    @ApiModelProperty(required = true, notes = "Active")
    private Boolean active;

    @ApiModelProperty(required = true, notes = "Description")
    private String description;

    @ApiModelProperty(required = true, notes = "Metadata is required ?")
    private Boolean metadataRequired;

    @ApiModelProperty(required = true, notes = "Model is required ?")
    private Boolean modelRequired;

    @ApiModelProperty(required = true, notes = "Type supply name")
    private String name;

    @ApiModelProperty(required = true, notes = "Provider")
    private ProviderDto provider;

    @ApiModelProperty(required = true, notes = "Provider profile")
    private ProviderProfileDto providerProfile;

    private List<ExtensionDto> extensions;

    public TypeSupplyDto() {
        this.extensions = new ArrayList<ExtensionDto>();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getMetadataRequired() {
        return metadataRequired;
    }

    public void setMetadataRequired(Boolean metadataRequired) {
        this.metadataRequired = metadataRequired;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProviderDto getProvider() {
        return provider;
    }

    public void setProvider(ProviderDto provider) {
        this.provider = provider;
    }

    public ProviderProfileDto getProviderProfile() {
        return providerProfile;
    }

    public void setProviderProfile(ProviderProfileDto providerProfile) {
        this.providerProfile = providerProfile;
    }

    public List<ExtensionDto> getExtensions() {
        return extensions;
    }

    public void setExtensions(List<ExtensionDto> extensions) {
        this.extensions = extensions;
    }

    public Boolean getModelRequired() {
        return modelRequired;
    }

    public void setModelRequired(Boolean modelRequired) {
        this.modelRequired = modelRequired;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

}
