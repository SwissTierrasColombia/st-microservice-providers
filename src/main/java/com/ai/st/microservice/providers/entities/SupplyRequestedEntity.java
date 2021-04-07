package com.ai.st.microservice.providers.entities;

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
@Table(name = "supplies_requested", schema = "providers")
public class SupplyRequestedEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "description", nullable = true, length = 500)
	private String description;

	@Column(name = "errors", nullable = true, length = 1000)
	private String errors;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type_supply_id", referencedColumnName = "id", nullable = false)
	private TypeSupplyEntity typeSupply;

	@Column(name = "model_version", nullable = true, length = 20)
	private String modelVersion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "request_id", referencedColumnName = "id", nullable = false)
	private RequestEntity request;

	@Column(name = "delivered", nullable = false)
	private Boolean delivered;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "supply_requested_state_id", referencedColumnName = "id", nullable = false)
	private SupplyRequestedStateEntity state;

	@Column(name = "created_at", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@Column(name = "delivered_at", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date deliveredAt;

	@Column(name = "delivered_by", nullable = true)
	private Long deliveredBy;

	@Column(name = "justification", nullable = true, length = 500)
	private String justification;

	@Column(name = "url", nullable = true, length = 1000)
	private String url;

	@Column(name = "observations", nullable = true, length = 500)
	private String observations;

	@Column(name = "ftp", nullable = true, length = 500)
	private String ftp;

	public SupplyRequestedEntity() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TypeSupplyEntity getTypeSupply() {
		return typeSupply;
	}

	public void setTypeSupply(TypeSupplyEntity typeSupply) {
		this.typeSupply = typeSupply;
	}

	public RequestEntity getRequest() {
		return request;
	}

	public void setRequest(RequestEntity request) {
		this.request = request;
	}

	public Boolean getDelivered() {
		return delivered;
	}

	public void setDelivered(Boolean delivered) {
		this.delivered = delivered;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getDeliveredAt() {
		return deliveredAt;
	}

	public void setDeliveredAt(Date deliveredAt) {
		this.deliveredAt = deliveredAt;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	public SupplyRequestedStateEntity getState() {
		return state;
	}

	public void setState(SupplyRequestedStateEntity state) {
		this.state = state;
	}

	public String getModelVersion() {
		return modelVersion;
	}

	public void setModelVersion(String modelVersion) {
		this.modelVersion = modelVersion;
	}

	public Long getDeliveredBy() {
		return deliveredBy;
	}

	public void setDeliveredBy(Long deliveredBy) {
		this.deliveredBy = deliveredBy;
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
}
