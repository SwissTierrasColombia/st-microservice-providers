package com.ai.st.microservice.providers.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ai.st.microservice.providers.entities.AttachmentEntity;
import com.ai.st.microservice.providers.entities.SupplyRequestedEntity;

public interface AttachmentRepository extends CrudRepository<AttachmentEntity, Long> {

	AttachmentEntity findBySupplyRequestedAndBoundaryId(SupplyRequestedEntity supplyRequested, Long boundaryId);

}
