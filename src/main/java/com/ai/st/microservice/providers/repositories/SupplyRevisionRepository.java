package com.ai.st.microservice.providers.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.SupplyRequestedEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.SupplyRevisionEntity;

public interface SupplyRevisionRepository extends PagingAndSortingRepository<SupplyRevisionEntity, Long> {

	SupplyRevisionEntity findBySupplyRequested(SupplyRequestedEntity supplyRequested);

}
