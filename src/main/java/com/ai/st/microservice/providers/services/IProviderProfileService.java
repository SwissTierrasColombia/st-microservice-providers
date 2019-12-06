package com.ai.st.microservice.providers.services;

import com.ai.st.microservice.providers.entities.ProviderProfileEntity;

public interface IProviderProfileService {

	public ProviderProfileEntity createProviderProfile(ProviderProfileEntity providerProfile);

	public ProviderProfileEntity getProviderProfileById(Long id);
	
	public ProviderProfileEntity getProviderProfileByName(String name);

}
