package com.ai.st.microservice.providers.services;

import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.SupplyRequestedEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.SupplyRevisionEntity;

public interface ISupplyRevisionService {

    public SupplyRevisionEntity createSupplyRevision(SupplyRevisionEntity entity);

    public void deleteSupplyRevisionById(Long supplyRevisionId);

    public SupplyRevisionEntity getSupplyRevisionById(Long supplyRevisionId);

    public SupplyRevisionEntity getSupplyRevisionBySupplyRequested(SupplyRequestedEntity supplyRequested);

}
