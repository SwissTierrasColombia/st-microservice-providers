package com.ai.st.microservice.providers.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ai.st.microservice.providers.entities.ProviderProfileEntity;

public interface ProviderProfileRepository extends CrudRepository<ProviderProfileEntity, Long> {

}
