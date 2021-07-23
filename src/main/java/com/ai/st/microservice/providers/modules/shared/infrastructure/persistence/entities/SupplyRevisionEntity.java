package com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "supplies_revisions", schema = "providers")
public class SupplyRevisionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "supply_requested_id", referencedColumnName = "id", nullable = false)
	private SupplyRequestedEntity supplyRequested;

	@Column(name = "started_at", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date startedAt;

	@Column(name = "started_by", nullable = false)
	private Long startedBy;

	@Column(name = "finished_at", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date finishedAt;

	@Column(name = "finished_by", nullable = true)
	private Long finishedBy;

	@Column(name = "hostname", nullable = false)
	private String hostname;

	@Column(name = "port", nullable = false)
	private String port;

	@Column(name = "database", nullable = false)
	private String database;

	@Column(name = "schema", nullable = false)
	private String schema;

	@Column(name = "username", nullable = false)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	public SupplyRevisionEntity() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SupplyRequestedEntity getSupplyRequested() {
		return supplyRequested;
	}

	public void setSupplyRequested(SupplyRequestedEntity supplyRequested) {
		this.supplyRequested = supplyRequested;
	}

	public Date getStartedAt() {
		return startedAt;
	}

	public void setStartedAt(Date startedAt) {
		this.startedAt = startedAt;
	}

	public Date getFinishedAt() {
		return finishedAt;
	}

	public void setFinishedAt(Date finishedAt) {
		this.finishedAt = finishedAt;
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

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
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

	public Long getStartedBy() {
		return startedBy;
	}

	public void setStartedBy(Long startedBy) {
		this.startedBy = startedBy;
	}

	public Long getFinishedBy() {
		return finishedBy;
	}

	public void setFinishedBy(Long finishedBy) {
		this.finishedBy = finishedBy;
	}

}
