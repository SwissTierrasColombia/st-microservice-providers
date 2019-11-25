package com.ai.st.microservice.providers.services;

import java.util.List;

import com.ai.st.microservice.providers.entities.ProviderUserEntity;

public interface IProviderUserService {

	public ProviderUserEntity createProviderUser(ProviderUserEntity providerUserEntity);

	public List<ProviderUserEntity> getProvidersUsersByCodeUser(Long userCode);

}
