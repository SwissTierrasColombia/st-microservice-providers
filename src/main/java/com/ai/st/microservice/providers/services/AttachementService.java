package com.ai.st.microservice.providers.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.st.microservice.providers.entities.AttachmentEntity;
import com.ai.st.microservice.providers.entities.SupplyRequestedEntity;
import com.ai.st.microservice.providers.repositories.AttachmentRepository;

@Service
public class AttachementService implements IAttachmentService {

	@Autowired
	private AttachmentRepository attachmentRepository;

	@Override
	public AttachmentEntity getAttachmentBySupplyRequestedAndBoundaryId(SupplyRequestedEntity supplyRequested,
			Long boundaryId) {
		return attachmentRepository.findBySupplyRequestedAndBoundaryId(supplyRequested, boundaryId);
	}

	@Override
	@Transactional
	public AttachmentEntity createAttachment(AttachmentEntity entity) {
		return attachmentRepository.save(entity);
	}

	@Override
	@Transactional
	public AttachmentEntity updateAttachment(AttachmentEntity entity) {
		return attachmentRepository.save(entity);
	}

}
