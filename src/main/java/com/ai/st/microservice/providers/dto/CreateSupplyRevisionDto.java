package com.ai.st.microservice.providers.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "CreateSupplyRevisionDto")
public class CreateSupplyRevisionDto implements Serializable {

    private static final long serialVersionUID = 5502646530977747659L;

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

    public CreateSupplyRevisionDto() {

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

}
