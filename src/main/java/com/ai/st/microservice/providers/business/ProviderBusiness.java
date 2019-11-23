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
import com.ai.st.microservice.providers.dto.RequestStateDto;
import com.ai.st.microservice.providers.dto.SupplyRequestedDto;
import com.ai.st.microservice.providers.dto.TypeSupplyDto;
import com.ai.st.microservice.providers.entities.EmitterEntity;
import com.ai.st.microservice.providers.entities.ProviderCategoryEntity;
import com.ai.st.microservice.providers.entities.ProviderEntity;
import com.ai.st.microservice.providers.entities.RequestEntity;
import com.ai.st.microservice.providers.entities.SupplyRequestedEntity;
import com.ai.st.microservice.providers.entities.TypeSupplyEntity;
import com.ai.st.microservice.providers.exceptions.BusinessException;
import com.ai.st.microservice.providers.services.IProviderCategoryService;
import com.ai.st.microservice.providers.services.IProviderService;
import com.ai.st.microservice.providers.services.IRequestService;

@Component
public class ProviderBusiness {

	@Autowired
	private IProviderService providerService;

	@Autowired
	private IProviderCategoryService providerCategoryService;

	@Autowired
	private IRequestService requestService;

	public List<ProviderDto> getProviders() throws BusinessException {

		List<ProviderDto> listProvidersDto = new ArrayList<ProviderDto>();

		List<ProviderEntity> listProvidersEntity = providerService.getAllProviders();
		for (ProviderEntity providerEntity : listProvidersEntity) {

			ProviderDto providerDto = new ProviderDto();
			providerDto.setId(providerEntity.getId());
			providerDto.setName(providerEntity.getName());
			providerDto.setCreatedAt(providerEntity.getCreatedAt());
			providerDto.setTaxIdentificationNumber(providerEntity.getTaxIdentificationNumber());
			providerDto.setProviderCategory(new ProviderCategoryDto(providerEntity.getProviderCategory().getId(),
					providerEntity.getProviderCategory().getName()));

			listProvidersDto.add(providerDto);
		}

		return listProvidersDto;
	}

	public List<ProviderDto> getProvidersByCategoryId(Long providerCategoryId) throws BusinessException {

		List<ProviderDto> listProvidersDto = new ArrayList<ProviderDto>();

		List<ProviderEntity> listProvidersEntity = providerService.getProvidersByCategoryId(providerCategoryId);
		for (ProviderEntity providerEntity : listProvidersEntity) {

			ProviderDto providerDto = new ProviderDto();
			providerDto.setId(providerEntity.getId());
			providerDto.setName(providerEntity.getName());
			providerDto.setCreatedAt(providerEntity.getCreatedAt());
			providerDto.setTaxIdentificationNumber(providerEntity.getTaxIdentificationNumber());
			providerDto.setProviderCategory(new ProviderCategoryDto(providerEntity.getProviderCategory().getId(),
					providerEntity.getProviderCategory().getName()));

			listProvidersDto.add(providerDto);
		}

		return listProvidersDto;
	}

	public ProviderDto createProvider(String name, String taxIdentificationNumber, Long providerCategoryId)
			throws BusinessException {

		name = name.toUpperCase();

		ProviderCategoryEntity providerCategoryEntity = providerCategoryService
				.getProviderCategoryById(providerCategoryId);

		// verify if the category exists
		if (!(providerCategoryEntity instanceof ProviderCategoryEntity)) {
			throw new BusinessException("The category does not exist.");
		}

		// verify that there is no provider with the same name
		ProviderEntity providerExistsEntity = providerService.getProviderByNameAndProviderCategory(name,
				providerCategoryEntity);
		if (providerExistsEntity instanceof ProviderEntity) {
			throw new BusinessException("The name of the provider is already registered.");
		}

		ProviderEntity providerEntity = new ProviderEntity();
		providerEntity.setName(name);
		providerEntity.setTaxIdentificationNumber(taxIdentificationNumber);
		providerEntity.setCreatedAt(new Date());
		providerEntity.setProviderCategory(providerCategoryEntity);
		providerEntity = providerService.createProvider(providerEntity);

		ProviderDto providerDto = new ProviderDto();
		providerDto.setId(providerEntity.getId());
		providerDto.setName(providerEntity.getName());
		providerDto.setCreatedAt(providerEntity.getCreatedAt());
		providerDto.setTaxIdentificationNumber(providerEntity.getTaxIdentificationNumber());
		providerDto.setProviderCategory(new ProviderCategoryDto(providerEntity.getProviderCategory().getId(),
				providerEntity.getProviderCategory().getName()));

		return providerDto;
	}

	public ProviderDto getProviderById(Long providerId) throws BusinessException {

		ProviderDto providerDto = null;

		ProviderEntity providerEntity = providerService.getProviderById(providerId);
		if (providerEntity instanceof ProviderEntity) {
			providerDto = new ProviderDto();
			providerDto.setId(providerEntity.getId());
			providerDto.setName(providerEntity.getName());
			providerDto.setCreatedAt(providerEntity.getCreatedAt());
			providerDto.setTaxIdentificationNumber(providerEntity.getTaxIdentificationNumber());
			providerDto.setProviderCategory(new ProviderCategoryDto(providerEntity.getProviderCategory().getId(),
					providerEntity.getProviderCategory().getName()));
		}

		return providerDto;
	}

	public List<TypeSupplyDto> getTypesSuppliesByProviderId(Long providerId) throws BusinessException {

		List<TypeSupplyDto> listTypeSupplyDtos = new ArrayList<TypeSupplyDto>();

		// verify provider exists
		ProviderEntity providerEntity = providerService.getProviderById(providerId);
		if (!(providerEntity instanceof ProviderEntity)) {
			throw new BusinessException("El proveedor de insumo no existe.");
		}

		List<TypeSupplyEntity> listTypeSupplyEntities = providerEntity.getTypesSupplies();
		for (TypeSupplyEntity typeSupplyEntity : listTypeSupplyEntities) {

			TypeSupplyDto typeSupplyDto = new TypeSupplyDto();
			typeSupplyDto.setCreatedAt(typeSupplyEntity.getCreatedAt());
			typeSupplyDto.setDescription(typeSupplyEntity.getDescription());
			typeSupplyDto.setId(typeSupplyEntity.getId());
			typeSupplyDto.setMetadataRequired(typeSupplyEntity.getIsMetadataRequired());
			typeSupplyDto.setName(typeSupplyEntity.getName());

			ProviderProfileDto providerProfileDto = new ProviderProfileDto();
			providerProfileDto.setDescription(typeSupplyEntity.getProviderProfile().getDescription());
			providerProfileDto.setId(typeSupplyEntity.getProviderProfile().getId());
			providerProfileDto.setName(typeSupplyEntity.getProviderProfile().getName());
			typeSupplyDto.setProviderProfile(providerProfileDto);

			listTypeSupplyDtos.add(typeSupplyDto);
		}

		return listTypeSupplyDtos;
	}

	public List<RequestDto> getRequestsByProviderAndState(Long providerId, Long requestStateId)
			throws BusinessException {

		List<RequestDto> listRequestsDto = new ArrayList<RequestDto>();

		// verify provider exists
		ProviderEntity providerEntity = providerService.getProviderById(providerId);
		if (!(providerEntity instanceof ProviderEntity)) {
			throw new BusinessException("El proveedor de insumo no existe.");
		}

		List<RequestEntity> listRequestEntity = new ArrayList<RequestEntity>();

		if (requestStateId != null) {
			listRequestEntity = requestService.getRequestsByProviderIdAndStateId(providerId, requestStateId);
		} else {
			listRequestEntity = providerEntity.getRequests();
		}

		for (RequestEntity requestEntity : listRequestEntity) {

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

			listRequestsDto.add(requestDto);

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
				emitterDto.setId(emitterEntity.getId());
				emitterDto.setEmitterType(emitterEntity.getEmitterType().name());
				emittersDto.add(emitterDto);
			}
			requestDto.setEmitters(emittersDto);

		}

		return listRequestsDto;
	}

}
