package com.ai.st.microservice.providers.services;

import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderProfileEntity;

public interface IProviderProfileService {

	public ProviderProfileEntity createProviderProfile(ProviderProfileEntity providerProfile);

	public ProviderProfileEntity getProviderProfileById(Long id);

	public ProviderProfileEntity getProviderProfileByName(String name);

	public void deleteProviderProfileById(Long id);

}
