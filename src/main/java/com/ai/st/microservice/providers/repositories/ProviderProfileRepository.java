package com.ai.st.microservice.providers.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderProfileEntity;

public interface ProviderProfileRepository extends CrudRepository<ProviderProfileEntity, Long> {
	
	ProviderProfileEntity findByName(String name);

}
