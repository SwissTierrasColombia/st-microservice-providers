package com.ai.st.microservice.providers.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ai.st.microservice.providers.entities.ProviderCategoryEntity;

public interface ProviderCategoryRepository extends CrudRepository<ProviderCategoryEntity, Long> {

}
