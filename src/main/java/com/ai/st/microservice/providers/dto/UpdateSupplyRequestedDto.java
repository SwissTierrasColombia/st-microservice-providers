package com.ai.st.microservice.providers.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "UpdateSupplyRequestedDto")
public class UpdateSupplyRequestedDto implements Serializable {

    private static final long serialVersionUID = -1302858811309199472L;

    @ApiModelProperty(notes = "Delivered")
    private Boolean delivered;

    @ApiModelProperty(notes = "Justification")
    private String justification;

    @ApiModelProperty(notes = "Supply Requested State ID")
    private Long supplyRequestedStateId;

    @ApiModelProperty(notes = "User Code")
    private Long deliveryBy;

    @ApiModelProperty(notes = "Url file")
    private String url;

    @ApiModelProperty(notes = "Observations")
    private String observations;

    @ApiModelProperty(notes = "Errors")
    private String errors;

    @ApiModelProperty(notes = "Ftp")
    private String ftp;

    @ApiModelProperty(notes = "is validated?")
    private Boolean validated;

    @ApiModelProperty(notes = "log file path")
    private String log;

    @ApiModelProperty(notes = "Extra file")
    private String extraFile;

    public Boolean getDelivered() {
        return delivered;
    }

    public void setDelivered(Boolean delivered) {
        this.delivered = delivered;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public Long getSupplyRequestedStateId() {
        return supplyRequestedStateId;
    }

    public void setSupplyRequestedStateId(Long supplyRequestedStateId) {
        this.supplyRequestedStateId = supplyRequestedStateId;
    }

    public Long getDeliveryBy() {
        return deliveryBy;
    }

    public void setDeliveryBy(Long deliveryBy) {
        this.deliveryBy = deliveryBy;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getFtp() {
        return ftp;
    }

    public void setFtp(String ftp) {
        this.ftp = ftp;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    public Boolean isValidated() {
        return validated;
    }

    public void setValidated(Boolean validated) {
        this.validated = validated;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getExtraFile() {
        return extraFile;
    }

    public void setExtraFile(String extraFile) {
        this.extraFile = extraFile;
    }

    @Override
    public String toString() {
        return "UpdateSupplyRequestedDto{" + "delivered=" + delivered + ", justification='" + justification + '\''
                + ", supplyRequestedStateId=" + supplyRequestedStateId + ", deliveryBy=" + deliveryBy + ", url='" + url
                + '\'' + ", observations='" + observations + '\'' + ", errors='" + errors + '\'' + ", ftp='" + ftp
                + '\'' + ", validated=" + validated + ", log='" + log + '\'' + ", extraFile='" + extraFile + '\'' + '}';
    }
}
