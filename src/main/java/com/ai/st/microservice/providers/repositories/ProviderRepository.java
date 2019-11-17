package com.ai.st.microservice.providers.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ai.st.microservice.providers.entities.ProviderEntity;

public interface ProviderRepository extends CrudRepository<ProviderEntity, Long> {

}
