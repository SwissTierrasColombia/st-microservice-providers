package com.ai.st.microservice.providers.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.RequestStateEntity;

public interface RequestStateRepository extends CrudRepository<RequestStateEntity, Long> {

}
