package com.ai.st.microservice.providers.business;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.st.microservice.providers.dto.SupplyRequestedDto;
import com.ai.st.microservice.providers.dto.SupplyRevisionDto;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.SupplyRequestedEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.SupplyRevisionEntity;
import com.ai.st.microservice.providers.exceptions.BusinessException;
import com.ai.st.microservice.providers.services.ISupplyRequestedService;
import com.ai.st.microservice.providers.services.ISupplyRevisionService;

@Component
public class SupplyRevisionBusiness {

	@Autowired
	private ISupplyRevisionService supplyRevisionService;

	@Autowired
	private ISupplyRequestedService supplyRequestedService;

	@Autowired
	private SupplyRequestedBusiness supplyRequestedBusiness;

	public SupplyRevisionDto createSupplyRevision(Long supplyRequestedId, String database, String hostname,
			String schema, String port, String username, String password, Long startedBy) throws BusinessException {

		SupplyRequestedEntity supplyRequestedEntity = supplyRequestedService.getSupplyRequestedById(supplyRequestedId);
		if (!(supplyRequestedEntity instanceof SupplyRequestedEntity)) {
			throw new BusinessException("El insumo solicitado no existe.");
		}

		SupplyRevisionEntity foundSupplyRevisionEntity = supplyRevisionService
				.getSupplyRevisionBySupplyRequested(supplyRequestedEntity);
		if (foundSupplyRevisionEntity instanceof SupplyRevisionEntity) {
			throw new BusinessException("El insumo solicitado ya cuenta con una revisión.");
		}

		SupplyRevisionEntity supplyRevisionEntity = new SupplyRevisionEntity();
		supplyRevisionEntity.setDatabase(database);
		supplyRevisionEntity.setHostname(hostname);
		supplyRevisionEntity.setPassword(password);
		supplyRevisionEntity.setPort(port);
		supplyRevisionEntity.setSchema(schema);
		supplyRevisionEntity.setUsername(username);
		supplyRevisionEntity.setStartedAt(new Date());
		supplyRevisionEntity.setStartedBy(startedBy);
		supplyRevisionEntity.setSupplyRequested(supplyRequestedEntity);

		supplyRevisionEntity = supplyRevisionService.createSupplyRevision(supplyRevisionEntity);

		SupplyRevisionDto supplyRevisionDto = entityParseToDto(supplyRevisionEntity);
		return supplyRevisionDto;
	}

	public SupplyRevisionDto updateSupplyRevision(Long supplyRequestedId, Long revisionId, Long finishedBy)
			throws BusinessException {

		SupplyRequestedEntity supplyRequestedEntity = supplyRequestedService.getSupplyRequestedById(supplyRequestedId);
		if (!(supplyRequestedEntity instanceof SupplyRequestedEntity)) {
			throw new BusinessException("El insumo solicitado no existe.");
		}

		SupplyRevisionEntity supplyRevisionEntity = supplyRevisionService.getSupplyRevisionById(revisionId);
		if (!(supplyRevisionEntity instanceof SupplyRevisionEntity)) {
			throw new BusinessException("La revisión no existe.");
		}

		if (!supplyRevisionEntity.getSupplyRequested().getId().equals(supplyRequestedId)) {
			throw new BusinessException("La revisión no pertenece al insumo solicitado.");
		}

		if (finishedBy != null && finishedBy > 0) {
			supplyRevisionEntity.setFinishedAt(new Date());
			supplyRevisionEntity.setFinishedBy(finishedBy);
		}

		supplyRevisionEntity = supplyRevisionService.createSupplyRevision(supplyRevisionEntity);

		SupplyRevisionDto supplyRevisionDto = entityParseToDto(supplyRevisionEntity);
		return supplyRevisionDto;
	}

	public void deleteRevision(Long supplyRequestedId, Long supplyRevisionId) throws BusinessException {

		SupplyRequestedEntity supplyRequestedEntity = supplyRequestedService.getSupplyRequestedById(supplyRequestedId);
		if (!(supplyRequestedEntity instanceof SupplyRequestedEntity)) {
			throw new BusinessException("El insumo solicitado no existe.");
		}

		SupplyRevisionEntity supplyRevisionEntity = supplyRevisionService.getSupplyRevisionById(supplyRevisionId);
		if (!(supplyRevisionEntity instanceof SupplyRevisionEntity)) {
			throw new BusinessException("La revisión no existe.");
		}

		if (!supplyRevisionEntity.getSupplyRequested().getId().equals(supplyRequestedEntity.getId())) {
			throw new BusinessException("La revisión no pertenece al insumo solicitado.");
		}

		supplyRevisionService.deleteSupplyRevisionById(supplyRevisionId);

	}

	public SupplyRevisionDto getSupplyRevisionFromSupplyRequested(Long supplyRequestedId) throws BusinessException {

		SupplyRequestedEntity supplyRequestedEntity = supplyRequestedService.getSupplyRequestedById(supplyRequestedId);
		if (!(supplyRequestedEntity instanceof SupplyRequestedEntity)) {
			throw new BusinessException("El insumo solicitado no existe.");
		}

		SupplyRevisionEntity foundSupplyRevisionEntity = supplyRevisionService
				.getSupplyRevisionBySupplyRequested(supplyRequestedEntity);
		if (!(foundSupplyRevisionEntity instanceof SupplyRevisionEntity)) {
			throw new BusinessException("El insumo solicitado no cuenta con una revisión.");
		}

		return entityParseToDto(foundSupplyRevisionEntity);

	}

	public SupplyRevisionDto entityParseToDto(SupplyRevisionEntity supplyRevisionEntity) {

		SupplyRevisionDto supplyRevisionDto = new SupplyRevisionDto();
		supplyRevisionDto.setId(supplyRevisionEntity.getId());
		supplyRevisionDto.setDatabase(supplyRevisionEntity.getDatabase());
		supplyRevisionDto.setHostname(supplyRevisionEntity.getHostname());
		supplyRevisionDto.setPort(supplyRevisionEntity.getPort());
		supplyRevisionDto.setSchema(supplyRevisionEntity.getSchema());
		supplyRevisionDto.setPassword(supplyRevisionEntity.getPassword());
		supplyRevisionDto.setUsername(supplyRevisionEntity.getUsername());
		supplyRevisionDto.setFinishedAt(supplyRevisionEntity.getFinishedAt());
		supplyRevisionDto.setFinishedBy(supplyRevisionEntity.getFinishedBy());
		supplyRevisionDto.setStartAt(supplyRevisionEntity.getStartedAt());
		supplyRevisionDto.setStartBy(supplyRevisionEntity.getStartedBy());

		SupplyRequestedDto supplyRequestedDto = supplyRequestedBusiness
				.entityParseDto(supplyRevisionEntity.getSupplyRequested());
		supplyRevisionDto.setSupplyRequested(supplyRequestedDto);

		return supplyRevisionDto;
	}

}
