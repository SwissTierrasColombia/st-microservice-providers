package com.ai.st.microservice.providers.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderCategoryEntity;

public interface ProviderCategoryRepository extends CrudRepository<ProviderCategoryEntity, Long> {

    @Override
    List<ProviderCategoryEntity> findAll();

}
