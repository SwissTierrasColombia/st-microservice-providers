package com.ai.st.microservice.providers.services;

import com.ai.st.microservice.providers.entities.AttachmentEntity;
import com.ai.st.microservice.providers.entities.SupplyRequestedEntity;

public interface IAttachmentService {

	public AttachmentEntity getAttachmentBySupplyRequestedAndBoundaryId(SupplyRequestedEntity supplyRequested,
			Long boundaryId);

	public AttachmentEntity createAttachment(AttachmentEntity entity);

	public AttachmentEntity updateAttachment(AttachmentEntity entity);

}
