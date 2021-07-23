package com.ai.st.microservice.providers.services;

import java.util.List;

import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderProfileEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderUserEntity;

public interface IProviderUserService {

	public ProviderUserEntity createProviderUser(ProviderUserEntity providerUserEntity);

	public List<ProviderUserEntity> getProvidersUsersByCodeUser(Long userCode);

	public ProviderUserEntity getProviderUserByUserCodeAndProfileAndProvider(Long userCode,
			ProviderProfileEntity providerProfileEntity, ProviderEntity provider);

	public void deleteById(Long id);

	public List<ProviderUserEntity> getProviderUsersByProfile(ProviderProfileEntity providerProfile);

}
