package com.ai.st.microservice.providers.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ExtensionEntity;

public interface ExtensionRepository extends CrudRepository<ExtensionEntity, Long> {

}
