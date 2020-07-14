package com.ai.st.microservice.providers.services;

import com.ai.st.microservice.providers.entities.SupplyRequestedEntity;
import com.ai.st.microservice.providers.entities.SupplyRevisionEntity;

public interface ISupplyRevisionService {

	public SupplyRevisionEntity createSupplyRevision(SupplyRevisionEntity entity);

	public void deleteSupplyRevisionById(Long supplyRevisionId);

	public SupplyRevisionEntity getSupplyRevisionById(Long supplyRevisionId);

	public SupplyRevisionEntity getSupplyRevisionBySupplyRequested(SupplyRequestedEntity supplyRequested);

}
