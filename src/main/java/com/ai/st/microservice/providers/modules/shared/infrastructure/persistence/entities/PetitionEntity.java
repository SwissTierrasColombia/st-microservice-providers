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
@Table(name = "petitions", schema = "providers")
public class PetitionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "observations", nullable = false, length = 1000)
    private String observations;

    @Column(name = "justification", nullable = true, length = 1000)
    private String justification;

    @Column(name = "manager_code", nullable = false)
    private Long managerCode;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "petition_state_id", referencedColumnName = "id", nullable = false)
    private PetitionStateEntity petitionState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", referencedColumnName = "id", nullable = false)
    private ProviderEntity provider;

    public PetitionEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public Long getManagerCode() {
        return managerCode;
    }

    public void setManagerCode(Long managerCode) {
        this.managerCode = managerCode;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public PetitionStateEntity getPetitionState() {
        return petitionState;
    }

    public void setPetitionState(PetitionStateEntity petitionState) {
        this.petitionState = petitionState;
    }

    public ProviderEntity getProvider() {
        return provider;
    }

    public void setProvider(ProviderEntity provider) {
        this.provider = provider;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

}
