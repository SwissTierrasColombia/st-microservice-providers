package com.ai.st.microservice.providers.dto;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "RevisionDto")
public class SupplyRevisionDto implements Serializable {

    private static final long serialVersionUID = 6236269390887662607L;

    @ApiModelProperty(required = true, notes = "ID")
    private Long id;

    @ApiModelProperty(required = true, notes = "Database")
    private String database;

    @ApiModelProperty(required = true, notes = "Hostname")
    private String hostname;

    @ApiModelProperty(required = true, notes = "Port")
    private String port;

    @ApiModelProperty(required = true, notes = "Schema			")
    private String schema;

    @ApiModelProperty(required = true, notes = "Username")
    private String username;

    @ApiModelProperty(required = true, notes = "Password")
    private String password;

    @ApiModelProperty(required = true, notes = "Start by")
    private Long startBy;

    @ApiModelProperty(required = true, notes = "Start at")
    private Date startAt;

    @ApiModelProperty(required = true, notes = "Finished at")
    private Date finishedAt;

    @ApiModelProperty(required = true, notes = "Finished by")
    private Long finishedBy;

    @ApiModelProperty(required = true, notes = "Supply Requested")
    private SupplyRequestedDto supplyRequested;

    public SupplyRevisionDto() {

    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getStartBy() {
        return startBy;
    }

    public void setStartBy(Long startBy) {
        this.startBy = startBy;
    }

    public Date getStartAt() {
        return startAt;
    }

    public void setStartAt(Date startAt) {
        this.startAt = startAt;
    }

    public Date getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(Date finishedAt) {
        this.finishedAt = finishedAt;
    }

    public Long getFinishedBy() {
        return finishedBy;
    }

    public void setFinishedBy(Long finishedBy) {
        this.finishedBy = finishedBy;
    }

    public SupplyRequestedDto getSupplyRequested() {
        return supplyRequested;
    }

    public void setSupplyRequested(SupplyRequestedDto supplyRequested) {
        this.supplyRequested = supplyRequested;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
