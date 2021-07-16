package com.ai.st.microservice.providers.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "requests", schema = "providers")
public class RequestEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "deadline", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date deadline;

	@Column(name = "observations")
	private String observations;

	@Column(name = "package", nullable = false)
	private String packageLabel;

	@Column(name = "municipality_code", nullable = false, length = 10)
	private String municipalityCode;

	@Column(name = "closed_by")
	private Long closedBy;

	@Column(name = "created_at", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@Column(name = "closed_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date closedAt;

	@Column(name = "sent_review_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date sentReviewAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "provider_id", referencedColumnName = "id", nullable = false)
	private ProviderEntity provider;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "request_state_id", referencedColumnName = "id", nullable = false)
	private RequestStateEntity requestState;

	@OneToMany(mappedBy = "request", cascade = CascadeType.ALL)
	private List<SupplyRequestedEntity> supplies = new ArrayList<>();

	@OneToMany(mappedBy = "request", cascade = CascadeType.ALL)
	private List<EmitterEntity> emitters = new ArrayList<>();

	public RequestEntity() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public ProviderEntity getProvider() {
		return provider;
	}

	public void setProvider(ProviderEntity provider) {
		this.provider = provider;
	}

	public RequestStateEntity getRequestState() {
		return requestState;
	}

	public void setRequestState(RequestStateEntity requestState) {
		this.requestState = requestState;
	}

	public List<SupplyRequestedEntity> getSupplies() {
		return supplies;
	}

	public void setSupplies(List<SupplyRequestedEntity> supplies) {
		this.supplies = supplies;
	}

	public List<EmitterEntity> getEmitters() {
		return emitters;
	}

	public void setEmitters(List<EmitterEntity> emitters) {
		this.emitters = emitters;
	}

	public String getMunicipalityCode() {
		return municipalityCode;
	}

	public void setMunicipalityCode(String municipalityCode) {
		this.municipalityCode = municipalityCode;
	}

	public String getPackageLabel() {
		return packageLabel;
	}

	public void setPackageLabel(String packageLabel) {
		this.packageLabel = packageLabel;
	}

	public Date getClosedAt() {
		return closedAt;
	}

	public void setClosedAt(Date closedAt) {
		this.closedAt = closedAt;
	}

	public Long getClosedBy() {
		return closedBy;
	}

	public void setClosedBy(Long closedBy) {
		this.closedBy = closedBy;
	}

	public Date getSentReviewAt() {
		return sentReviewAt;
	}

	public void setSentReviewAt(Date sentReviewAt) {
		this.sentReviewAt = sentReviewAt;
	}

}
