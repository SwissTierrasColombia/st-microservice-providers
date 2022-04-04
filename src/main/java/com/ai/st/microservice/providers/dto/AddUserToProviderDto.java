package com.ai.st.microservice.providers.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "AddUserToProviderDto", description = "Add User To Provider Dto")
public class AddUserToProviderDto implements Serializable {

    private static final long serialVersionUID = 1453826822762079124L;

    @ApiModelProperty(required = true, notes = "User code")
    private Long userCode;

    @ApiModelProperty(required = true, notes = "Provider ID")
    private Long providerId;

    @ApiModelProperty(required = true, notes = "Provider ID")
    private Long profileId;

    public AddUserToProviderDto() {

    }

    public Long getUserCode() {
        return userCode;
    }

    public void setUserCode(Long userCode) {
        this.userCode = userCode;
    }

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

}
