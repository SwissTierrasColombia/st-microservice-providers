package com.ai.st.microservice.providers.services;

import com.ai.st.microservice.providers.entities.SupplyRequestedStateEntity;

public interface ISupplyRequestedStateService {

	public Long getCount();

	public SupplyRequestedStateEntity createState(SupplyRequestedStateEntity state);

	public SupplyRequestedStateEntity getStateById(Long id);

}
