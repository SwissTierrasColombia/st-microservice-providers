package com.ai.st.microservice.providers.business;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.st.microservice.providers.dto.AttachmentDto;
import com.ai.st.microservice.providers.dto.SupplyRequestedDto;
import com.ai.st.microservice.providers.entities.AttachmentEntity;
import com.ai.st.microservice.providers.entities.SupplyRequestedEntity;
import com.ai.st.microservice.providers.exceptions.BusinessException;
import com.ai.st.microservice.providers.services.IAttachmentService;
import com.ai.st.microservice.providers.services.ISupplyRequestedService;

@Component
public class AttachmentBusiness {

	@Autowired
	private IAttachmentService attachmentService;

	@Autowired
	private ISupplyRequestedService supplyRequestedService;

	@Autowired
	private SupplyRequestedBusiness supplyRequestedBusiness;

	public AttachmentDto createAttachment(Long supplyRequestedId, Long boundaryId, String urlFile, Long createdBy)
			throws BusinessException {

		SupplyRequestedEntity supplyRequestedEntity = supplyRequestedService.getSupplyRequestedById(supplyRequestedId);
		if (!(supplyRequestedEntity instanceof SupplyRequestedEntity)) {
			throw new BusinessException("El insumo solicitado no existe.");
		}

		AttachmentEntity attachmentEntity = attachmentService
				.getAttachmentBySupplyRequestedAndBoundaryId(supplyRequestedEntity, boundaryId);

		if (attachmentEntity == null) {
			attachmentEntity = new AttachmentEntity();
			attachmentEntity.setSupplyRequested(supplyRequestedEntity);
		}

		attachmentEntity.setBoundaryId(boundaryId);
		attachmentEntity.setCreatedAt(new Date());
		attachmentEntity.setCreatedBy(createdBy);
		attachmentEntity.setFileUrl(urlFile);

		attachmentEntity = attachmentService.createAttachment(attachmentEntity);

		return entityParseToDto(attachmentEntity);
	}

	public AttachmentDto entityParseToDto(AttachmentEntity attachmentEntity) {

		AttachmentDto attachmentDto = new AttachmentDto();

		attachmentDto.setId(attachmentEntity.getId());
		attachmentDto.setBoundaryId(attachmentEntity.getBoundaryId());
		attachmentDto.setCreatedAt(attachmentEntity.getCreatedAt());
		attachmentDto.setFileUrl(attachmentEntity.getFileUrl());

		SupplyRequestedDto supplyRequested = supplyRequestedBusiness
				.entityParseDto(attachmentEntity.getSupplyRequested());

		attachmentDto.setSupplyRequested(supplyRequested);

		return attachmentDto;
	}

}
