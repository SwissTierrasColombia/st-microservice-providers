package com.ai.st.microservice.providers.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.ai.st.microservice.providers.entities.SupplyRequestedEntity;
import com.ai.st.microservice.providers.entities.SupplyRevisionEntity;

public interface SupplyRevisionRepository extends PagingAndSortingRepository<SupplyRevisionEntity, Long> {

	SupplyRevisionEntity findBySupplyRequested(SupplyRequestedEntity supplyRequested);

}
