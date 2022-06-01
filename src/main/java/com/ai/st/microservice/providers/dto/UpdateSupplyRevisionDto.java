package com.ai.st.microservice.providers.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "UpdateSupplyRevisionDto")
public class UpdateSupplyRevisionDto implements Serializable {

    private static final long serialVersionUID = 1089574319167589543L;

    @ApiModelProperty(required = true, notes = "Finished by")
    private Long finishedBy;

    public UpdateSupplyRevisionDto() {

    }

    public Long getFinishedBy() {
        return finishedBy;
    }

    public void setFinishedBy(Long finishedBy) {
        this.finishedBy = finishedBy;
    }

    @Override
    public String toString() {
        return "UpdateSupplyRevisionDto{" + "finishedBy=" + finishedBy + '}';
    }
}
