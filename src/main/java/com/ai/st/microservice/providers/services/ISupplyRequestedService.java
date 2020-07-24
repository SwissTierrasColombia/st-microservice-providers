package com.ai.st.microservice.providers.services;

import java.util.List;

import com.ai.st.microservice.providers.entities.SupplyRequestedEntity;
import com.ai.st.microservice.providers.entities.TypeSupplyEntity;

public interface ISupplyRequestedService {

	public SupplyRequestedEntity updateSupplyRequested(SupplyRequestedEntity supplyRequestedEntity);

	public List<SupplyRequestedEntity> getSuppliesRequestedByTypeSupply(TypeSupplyEntity supplyEntity);

	public List<SupplyRequestedEntity> getSuppliesRequestedByProviderAndStates(Long providerId, List<Long> states);

	public SupplyRequestedEntity getSupplyRequestedById(Long supplyRequestedId);

}
