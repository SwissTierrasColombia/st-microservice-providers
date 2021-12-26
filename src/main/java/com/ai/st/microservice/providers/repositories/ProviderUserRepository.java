package com.ai.st.microservice.providers.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderProfileEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderUserEntity;

public interface ProviderUserRepository extends CrudRepository<ProviderUserEntity, Long> {

	List<ProviderUserEntity> findByUserCode(Long userCode);

	ProviderUserEntity findByProviderProfileAndUserCodeAndProvider(ProviderProfileEntity providerProfile, Long userCode,
			ProviderEntity provider);

	List<ProviderUserEntity> findByProviderProfile(ProviderProfileEntity providerProfile);

}
