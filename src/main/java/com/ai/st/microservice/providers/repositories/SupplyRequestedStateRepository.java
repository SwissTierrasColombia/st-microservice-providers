package com.ai.st.microservice.providers.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ai.st.microservice.providers.entities.SupplyRequestedStateEntity;

public interface SupplyRequestedStateRepository extends CrudRepository<SupplyRequestedStateEntity, Long> {

}
