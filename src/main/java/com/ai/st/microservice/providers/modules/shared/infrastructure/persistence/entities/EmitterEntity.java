package com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "emitters", schema = "providers")
public class EmitterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "emitter_code", nullable = false)
    private Long emitterCode;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id", referencedColumnName = "id", nullable = false)
    private RequestEntity request;

    @Column(name = "emitter_type", nullable = false, length = 50)
    @Enumerated(value = EnumType.STRING)
    private EmitterTypeEnum emitterType;

    public EmitterEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmitterCode() {
        return emitterCode;
    }

    public void setEmitterCode(Long emitterCode) {
        this.emitterCode = emitterCode;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public RequestEntity getRequest() {
        return request;
    }

    public void setRequest(RequestEntity request) {
        this.request = request;
    }

    public EmitterTypeEnum getEmitterType() {
        return emitterType;
    }

    public void setEmitterType(EmitterTypeEnum emitterType) {
        this.emitterType = emitterType;
    }

}
