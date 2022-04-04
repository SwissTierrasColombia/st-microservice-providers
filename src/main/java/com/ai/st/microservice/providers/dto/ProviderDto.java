package com.ai.st.microservice.providers.dto;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "ProviderDto")
public class ProviderDto {

    @ApiModelProperty(required = true, notes = "Provider ID")
    private Long id;

    @ApiModelProperty(required = true, notes = "Provider name")
    private String name;

    @ApiModelProperty(required = false, notes = "Provider alias")
    private String alias;

    @ApiModelProperty(required = true, notes = "Active")
    private Boolean active;

    @ApiModelProperty(required = true, notes = "Provider tax identification number")
    private String taxIdentificationNumber;

    @ApiModelProperty(required = true, notes = "Date creation")
    private Date createdAt;

    @ApiModelProperty(required = true, notes = "Category")
    private ProviderCategoryDto providerCategory;

    public ProviderDto() {

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

    public String getTaxIdentificationNumber() {
        return taxIdentificationNumber;
    }

    public void setTaxIdentificationNumber(String taxIdentificationNumber) {
        this.taxIdentificationNumber = taxIdentificationNumber;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public ProviderCategoryDto getProviderCategory() {
        return providerCategory;
    }

    public void setProviderCategory(ProviderCategoryDto providerCategory) {
        this.providerCategory = providerCategory;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

}
