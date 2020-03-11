package com.ai.st.microservice.providers.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.st.microservice.providers.dto.EmitterDto;
import com.ai.st.microservice.providers.dto.ExtensionDto;
import com.ai.st.microservice.providers.dto.ProviderCategoryDto;
import com.ai.st.microservice.providers.dto.ProviderDto;
import com.ai.st.microservice.providers.dto.ProviderProfileDto;
import com.ai.st.microservice.providers.dto.RequestDto;
import com.ai.st.microservice.providers.dto.RequestEmitterDto;
import com.ai.st.microservice.providers.dto.RequestStateDto;
import com.ai.st.microservice.providers.dto.SupplyRequestedDto;
import com.ai.st.microservice.providers.dto.SupplyRequestedStateDto;
import com.ai.st.microservice.providers.dto.TypeSupplyDto;
import com.ai.st.microservice.providers.dto.TypeSupplyRequestedDto;
import com.ai.st.microservice.providers.entities.EmitterEntity;
import com.ai.st.microservice.providers.entities.EmitterTypeEnum;
import com.ai.st.microservice.providers.entities.ExtensionEntity;
import com.ai.st.microservice.providers.entities.ProviderEntity;
import com.ai.st.microservice.providers.entities.RequestEntity;
import com.ai.st.microservice.providers.entities.RequestStateEntity;
import com.ai.st.microservice.providers.entities.SupplyRequestedEntity;
import com.ai.st.microservice.providers.entities.SupplyRequestedStateEntity;
import com.ai.st.microservice.providers.entities.TypeSupplyEntity;
import com.ai.st.microservice.providers.exceptions.BusinessException;
import com.ai.st.microservice.providers.services.IProviderService;
import com.ai.st.microservice.providers.services.IRequestService;
import com.ai.st.microservice.providers.services.IRequestStateService;
import com.ai.st.microservice.providers.services.ISupplyRequestedService;
import com.ai.st.microservice.providers.services.ISupplyRequestedStateService;
import com.ai.st.microservice.providers.services.ITypeSupplyService;

@Component
public class RequestBusiness {

	@Autowired
	private IProviderService providerService;

	@Autowired
	private ITypeSupplyService typeSupplyService;

	@Autowired
	private IRequestStateService requestStateService;

	@Autowired
	private IRequestService requestService;

	@Autowired
	private ISupplyRequestedService supplyRequestedService;

	@Autowired
	private ISupplyRequestedStateService supplyRequestedStateService;

	public RequestDto createRequest(Date deadline, Long providerId, String municipalityCode,
			List<RequestEmitterDto> requestEmmiters, List<TypeSupplyRequestedDto> supplies) throws BusinessException {

		// verify that the sea deadline greater than the current date
		if (!deadline.after(new Date())) {
			throw new BusinessException("La fecha límite debe ser mayor a la fecha actual.");
		}

		// verify provider exists
		ProviderEntity providerEntity = providerService.getProviderById(providerId);
		if (!(providerEntity instanceof ProviderEntity)) {
			throw new BusinessException("El proveedor de insumo no existe.");
		}

		// verify supplies
		List<Long> suppliesId = new ArrayList<Long>();
		List<TypeSupplyRequestedDto> listSupplies = new ArrayList<TypeSupplyRequestedDto>();
		for (TypeSupplyRequestedDto typeSupplyDto : supplies) {

			// verify the type of input exists
			TypeSupplyEntity typeSupplyEntity = typeSupplyService.getTypeSupplyById(typeSupplyDto.getTypeSupplyId());
			if (!(typeSupplyEntity instanceof TypeSupplyEntity)) {
				throw new BusinessException("El tipo de insumo no existe.");
			}

			// verify if the type of input belongs to the entity
			if (typeSupplyEntity.getProvider().getId() != providerEntity.getId()) {
				throw new BusinessException("El tipo de insumo no pertenece al proveedor.");
			}

			// verify if the type supply is repeated in the request
			if (!suppliesId.contains(typeSupplyDto.getTypeSupplyId())) {
				listSupplies.add(typeSupplyDto);
				suppliesId.add(typeSupplyDto.getTypeSupplyId());
			}

			// verify if the supply required version model
			if (typeSupplyEntity.getIsModelRequired()) {
				if (typeSupplyDto.getModelVersion() == null || typeSupplyDto.getModelVersion().isEmpty()) {
					throw new BusinessException("El tipo de insumo requiere especificar la versión del modelo.");
				}
			}

		}

		// verify emitters
		for (RequestEmitterDto requestEmitterDto : requestEmmiters) {

			// verify type emitter
			if (!requestEmitterDto.getEmitterType().equals(EmitterTypeEnum.ENTITY.name())
					&& !requestEmitterDto.getEmitterType().equals(EmitterTypeEnum.USER.name())) {
				throw new BusinessException("El el tipo de emisor es inválido.");
			}

		}

		RequestStateEntity requestStateEntity = requestStateService
				.getRequestStateById(RequestStateBusiness.REQUEST_STATE_REQUESTED);

		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setCreatedAt(new Date());
		requestEntity.setDeadline(deadline);
		requestEntity.setProvider(providerEntity);
		requestEntity.setRequestState(requestStateEntity);
		requestEntity.setMunicipalityCode(municipalityCode);

		SupplyRequestedStateEntity statePending = supplyRequestedStateService
				.getStateById(SupplyRequestedStateBusiness.SUPPLY_REQUESTED_STATE_PENDING);

		// supplies
		List<SupplyRequestedEntity> suppliesEntities = new ArrayList<SupplyRequestedEntity>();
		for (TypeSupplyRequestedDto typeSupplyDto : listSupplies) {
			TypeSupplyEntity typeSupplyEntity = typeSupplyService.getTypeSupplyById(typeSupplyDto.getTypeSupplyId());
			SupplyRequestedEntity supplyEntity = new SupplyRequestedEntity();
			supplyEntity.setDescription(typeSupplyDto.getObservation());
			supplyEntity.setRequest(requestEntity);
			supplyEntity.setTypeSupply(typeSupplyEntity);
			if (typeSupplyEntity.getIsModelRequired()) {
				supplyEntity.setModelVersion(typeSupplyDto.getModelVersion());
			}
			supplyEntity.setCreatedAt(new Date());
			supplyEntity.setDelivered(false);
			supplyEntity.setState(statePending);
			suppliesEntities.add(supplyEntity);
		}
		requestEntity.setSupplies(suppliesEntities);

		// emitters
		List<EmitterEntity> emitterEntities = new ArrayList<EmitterEntity>();

		for (RequestEmitterDto requestEmitterDto : requestEmmiters) {
			EmitterEntity emitterEntity = new EmitterEntity();
			emitterEntity.setCreatedAt(new Date());
			emitterEntity.setEmitterCode(requestEmitterDto.getEmitterCode());

			EmitterTypeEnum emmitterTypeEnum = (requestEmitterDto.getEmitterType()
					.equals(EmitterTypeEnum.ENTITY.name())) ? EmitterTypeEnum.ENTITY : EmitterTypeEnum.USER;

			emitterEntity.setEmitterType(emmitterTypeEnum);
			emitterEntity.setRequest(requestEntity);
			emitterEntities.add(emitterEntity);
		}

		requestEntity.setEmitters(emitterEntities);

		requestEntity = requestService.createRequest(requestEntity);

		return this.entityParseDto(requestEntity);
	}

	public RequestDto getRequestById(Long requestId) throws BusinessException {

		RequestDto requestDto = null;

		RequestEntity requestEntity = requestService.getRequestById(requestId);
		if (requestEntity instanceof RequestEntity) {

			requestDto = this.entityParseDto(requestEntity);
		}

		return requestDto;
	}

	public RequestDto updateSupplyRequested(Long requestId, Long supplyRequestedId, Long stateId, String justification)
			throws BusinessException {

		// verify if request exists
		RequestEntity requestEntity = requestService.getRequestById(requestId);
		if (!(requestEntity instanceof RequestEntity)) {
			throw new BusinessException("La solicitud no existe");
		}

		SupplyRequestedStateEntity stateRequestedSupply = supplyRequestedStateService.getStateById(stateId);
		if (!(stateRequestedSupply instanceof SupplyRequestedStateEntity)) {
			throw new BusinessException("El estado no existe.");
		}

		if (stateRequestedSupply.getId() == SupplyRequestedStateBusiness.SUPPLY_REQUESTED_STATE_UNDELIVERED
				&& justification.isEmpty()) {
			throw new BusinessException("Se debe especificar porque no se entregó el insumo.");
		}

		SupplyRequestedEntity supplyRequested = requestEntity.getSupplies().stream()
				.filter(supply -> supply.getId() == supplyRequestedId).findAny().orElse(null);
		if (supplyRequested != null) {

			supplyRequested.setState(stateRequestedSupply);

			if (stateRequestedSupply.getId() == SupplyRequestedStateBusiness.SUPPLY_REQUESTED_STATE_ACCEPTED) {
				supplyRequested.setDeliveredAt(new Date());
				supplyRequested.setJustification("");
			} else if (stateRequestedSupply
					.getId() == SupplyRequestedStateBusiness.SUPPLY_REQUESTED_STATE_UNDELIVERED) {
				supplyRequested.setJustification(justification);
			}

			supplyRequestedService.updateSupplyRequested(supplyRequested);

		} else {
			throw new BusinessException("La tipo de insumo no esta asociado con la solicitud.");
		}

		RequestDto requestDto = this.getRequestById(requestId);
		return requestDto;
	}

	public RequestDto updateRequestState(Long requestId, Long requestStateId) throws BusinessException {

		// verify if request exists
		RequestEntity requestEntity = requestService.getRequestById(requestId);
		if (!(requestEntity instanceof RequestEntity)) {
			throw new BusinessException("La solicitud no existe");
		}

		// verify if status exists
		RequestStateEntity requestState = requestStateService.getRequestStateById(requestStateId);
		if (!(requestState instanceof RequestStateEntity)) {
			throw new BusinessException("El estado no existe.");
		}

		requestEntity.setRequestState(requestState);
		requestEntity = requestService.updateRequest(requestEntity);

		RequestDto requestDto = this.getRequestById(requestId);
		return requestDto;
	}

	public List<RequestDto> getRequestByEmmiters(Long emmiterCode, String emmiterType) throws BusinessException {

		List<RequestDto> listRequestsDto = new ArrayList<RequestDto>();

		List<RequestEntity> listRequestsEntity = requestService.getRequestsByEmmiter(emmiterCode, emmiterType);

		if (listRequestsEntity.size() > 0) {
			for (RequestEntity requestEntity : listRequestsEntity) {
				RequestDto requestDto = entityParseDto(requestEntity);
				listRequestsDto.add(requestDto);
			}
		}

		return listRequestsDto;
	}

	public RequestDto entityParseDto(RequestEntity requestEntity) {

		RequestDto requestDto = new RequestDto();
		requestDto.setId(requestEntity.getId());
		requestDto.setCreatedAt(requestEntity.getCreatedAt());
		requestDto.setDeadline(requestEntity.getDeadline());
		requestDto.setObservations(requestEntity.getObservations());
		requestDto.setMunicipalityCode(requestEntity.getMunicipalityCode());

		ProviderEntity providerEntity = requestEntity.getProvider();

		ProviderDto providerDto = new ProviderDto();
		providerDto.setId(providerEntity.getId());
		providerDto.setName(providerEntity.getName());
		providerDto.setCreatedAt(providerEntity.getCreatedAt());
		providerDto.setTaxIdentificationNumber(providerEntity.getTaxIdentificationNumber());
		providerDto.setProviderCategory(new ProviderCategoryDto(providerEntity.getProviderCategory().getId(),
				providerEntity.getProviderCategory().getName()));
		requestDto.setProvider(providerDto);

		requestDto.setRequestState(new RequestStateDto(requestEntity.getRequestState().getId(),
				requestEntity.getRequestState().getName()));

		List<SupplyRequestedDto> suppliesDto = new ArrayList<SupplyRequestedDto>();
		for (SupplyRequestedEntity supplyRE : requestEntity.getSupplies()) {

			SupplyRequestedDto supplyRequested = new SupplyRequestedDto();
			supplyRequested.setId(supplyRE.getId());
			supplyRequested.setDescription(supplyRE.getDescription());
			supplyRequested.setCreatedAt(supplyRE.getCreatedAt());
			supplyRequested.setDelivered(supplyRE.getDelivered());
			supplyRequested.setDeliveredAt(supplyRE.getDeliveredAt());
			supplyRequested.setJustification(supplyRE.getJustification());
			supplyRequested.setModelVersion(supplyRE.getModelVersion());

			SupplyRequestedStateEntity stateSupplyRequested = supplyRE.getState();
			supplyRequested.setState(
					new SupplyRequestedStateDto(stateSupplyRequested.getId(), stateSupplyRequested.getName()));

			TypeSupplyEntity tsE = supplyRE.getTypeSupply();

			TypeSupplyDto typeSupplyDto = new TypeSupplyDto();
			typeSupplyDto.setCreatedAt(tsE.getCreatedAt());
			typeSupplyDto.setDescription(tsE.getDescription());
			typeSupplyDto.setId(tsE.getId());
			typeSupplyDto.setMetadataRequired(tsE.getIsMetadataRequired());
			typeSupplyDto.setModelRequired(tsE.getIsModelRequired());
			typeSupplyDto.setName(tsE.getName());

			List<ExtensionDto> listExtensionsDto = new ArrayList<ExtensionDto>();
			for (ExtensionEntity extensionEntity : tsE.getExtensions()) {
				ExtensionDto extensionDto = new ExtensionDto();
				extensionDto.setId(extensionEntity.getId());
				extensionDto.setName(extensionEntity.getName());
				listExtensionsDto.add(extensionDto);
			}
			typeSupplyDto.setExtensions(listExtensionsDto);

			ProviderProfileDto providerProfileDto = new ProviderProfileDto();
			providerProfileDto.setDescription(tsE.getProviderProfile().getDescription());
			providerProfileDto.setId(tsE.getProviderProfile().getId());
			providerProfileDto.setName(tsE.getProviderProfile().getName());
			typeSupplyDto.setProviderProfile(providerProfileDto);

			supplyRequested.setTypeSupply(typeSupplyDto);

			suppliesDto.add(supplyRequested);
		}
		requestDto.setSuppliesRequested(suppliesDto);

		List<EmitterDto> emittersDto = new ArrayList<EmitterDto>();
		for (EmitterEntity emitterEntity : requestEntity.getEmitters()) {
			EmitterDto emitterDto = new EmitterDto();
			emitterDto.setCreatedAt(emitterEntity.getCreatedAt());
			emitterDto.setEmitterCode(emitterEntity.getEmitterCode());
			emitterDto.setEmitterType(emitterEntity.getEmitterType().name());
			emitterDto.setId(emitterEntity.getId());
			emittersDto.add(emitterDto);
		}
		requestDto.setEmitters(emittersDto);

		return requestDto;
	}

}
