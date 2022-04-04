package com.ai.st.microservice.providers.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "UpdateProviderDto")
public class UpdateProviderDto implements Serializable {

    private static final long serialVersionUID = 6515735489433109160L;

    @ApiModelProperty(required = true, notes = "Provider ID")
    private Long id;

    @ApiModelProperty(required = true, notes = "Provider name")
    private String name;

    @ApiModelProperty(required = false, notes = "Provider alias")
    private String alias;

    @ApiModelProperty(required = true, notes = "Provider tax identification number")
    private String taxIdentificationNumber;

    @ApiModelProperty(required = true, notes = "Provider Category ID")
    private Long providerCategoryId;

    public UpdateProviderDto() {

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

    public Long getProviderCategoryId() {
        return providerCategoryId;
    }

    public void setProviderCategoryId(Long providerCategoryId) {
        this.providerCategoryId = providerCategoryId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String toString() {
        return "UpdateProviderDto{" + "id=" + id + ", name='" + name + '\'' + ", alias='" + alias + '\''
                + ", taxIdentificationNumber='" + taxIdentificationNumber + '\'' + ", providerCategoryId="
                + providerCategoryId + '}';
    }
}
