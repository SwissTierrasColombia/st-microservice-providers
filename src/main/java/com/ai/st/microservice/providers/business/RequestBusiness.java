package com.ai.st.microservice.providers.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.st.microservice.providers.dto.EmitterDto;
import com.ai.st.microservice.providers.dto.ProviderCategoryDto;
import com.ai.st.microservice.providers.dto.ProviderDto;
import com.ai.st.microservice.providers.dto.ProviderProfileDto;
import com.ai.st.microservice.providers.dto.RequestDto;
import com.ai.st.microservice.providers.dto.RequestEmitterDto;
import com.ai.st.microservice.providers.dto.RequestStateDto;
import com.ai.st.microservice.providers.dto.SupplyRequestedDto;
import com.ai.st.microservice.providers.dto.TypeSupplyDto;
import com.ai.st.microservice.providers.dto.TypeSupplyRequestedDto;
import com.ai.st.microservice.providers.entities.EmitterEntity;
import com.ai.st.microservice.providers.entities.EmitterTypeEnum;
import com.ai.st.microservice.providers.entities.ProviderEntity;
import com.ai.st.microservice.providers.entities.RequestEntity;
import com.ai.st.microservice.providers.entities.RequestStateEntity;
import com.ai.st.microservice.providers.entities.SupplyRequestedEntity;
import com.ai.st.microservice.providers.entities.TypeSupplyEntity;
import com.ai.st.microservice.providers.exceptions.BusinessException;
import com.ai.st.microservice.providers.services.IProviderService;
import com.ai.st.microservice.providers.services.IRequestService;
import com.ai.st.microservice.providers.services.IRequestStateService;
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

	public RequestDto createRequest(Date deadline, Long providerId, List<RequestEmitterDto> requestEmmiters,
			List<TypeSupplyRequestedDto> supplies) throws BusinessException {

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

		// supplies
		List<SupplyRequestedEntity> suppliesEntities = new ArrayList<SupplyRequestedEntity>();
		for (TypeSupplyRequestedDto typeSupplyDto : listSupplies) {
			TypeSupplyEntity typeSupplyEntity = typeSupplyService.getTypeSupplyById(typeSupplyDto.getTypeSupplyId());
			SupplyRequestedEntity supplyEntity = new SupplyRequestedEntity();
			supplyEntity.setDescription(typeSupplyDto.getObservation());
			supplyEntity.setRequest(requestEntity);
			supplyEntity.setTypeSupply(typeSupplyEntity);
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

		RequestDto requestDto = new RequestDto();
		requestDto.setId(requestEntity.getId());
		requestDto.setCreatedAt(requestEntity.getCreatedAt());
		requestDto.setDeadline(requestEntity.getDeadline());
		requestDto.setObservations(requestEntity.getObservations());

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

			TypeSupplyEntity tsE = supplyRE.getTypeSupply();

			TypeSupplyDto typeSupplyDto = new TypeSupplyDto();
			typeSupplyDto.setCreatedAt(tsE.getCreatedAt());
			typeSupplyDto.setDescription(tsE.getDescription());
			typeSupplyDto.setId(tsE.getId());
			typeSupplyDto.setMetadataRequired(tsE.getIsMetadataRequired());
			typeSupplyDto.setName(tsE.getName());

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
