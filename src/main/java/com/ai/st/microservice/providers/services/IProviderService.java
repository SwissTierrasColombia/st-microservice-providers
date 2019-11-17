package com.ai.st.microservice.providers.services;

import com.ai.st.microservice.providers.entities.ProviderEntity;

public interface IProviderService {

	public ProviderEntity createProvider(ProviderEntity provider);

	public Long getCount();

}
