package com.ai.st.microservice.providers.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ai.st.microservice.providers.entities.SupplyRequestedEntity;

public interface SupplyRequestedRepository extends CrudRepository<SupplyRequestedEntity, Long>{

}
