package com.ai.st.microservice.providers.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.st.microservice.providers.dto.ExtensionDto;
import com.ai.st.microservice.providers.dto.ProviderDto;
import com.ai.st.microservice.providers.dto.ProviderProfileDto;
import com.ai.st.microservice.providers.dto.RequestDto;
import com.ai.st.microservice.providers.dto.SupplyRequestedDto;
import com.ai.st.microservice.providers.dto.SupplyRequestedStateDto;
import com.ai.st.microservice.providers.dto.TypeSupplyDto;
import com.ai.st.microservice.providers.entities.ExtensionEntity;
import com.ai.st.microservice.providers.entities.ProviderEntity;
import com.ai.st.microservice.providers.entities.RequestEntity;
import com.ai.st.microservice.providers.entities.SupplyRequestedEntity;
import com.ai.st.microservice.providers.entities.SupplyRequestedStateEntity;
import com.ai.st.microservice.providers.entities.TypeSupplyEntity;
import com.ai.st.microservice.providers.exceptions.BusinessException;
import com.ai.st.microservice.providers.services.IProviderService;
import com.ai.st.microservice.providers.services.ISupplyRequestedService;

@Component
public class SupplyRequestedBusiness {

	@Autowired
	private IProviderService providerService;

	@Autowired
	private ISupplyRequestedService supplyRequestedService;

	public List<SupplyRequestedDto> getSuppliesRequestedByProvider(Long providerId, List<Long> states)
			throws BusinessException {

		// verify provider does exists
		ProviderEntity providerEntity = providerService.getProviderById(providerId);
		if (!(providerEntity instanceof ProviderEntity)) {
			throw new BusinessException("El proveedor de insumo no existe.");
		}

		List<SupplyRequestedDto> suppliesRequestedDto = new ArrayList<SupplyRequestedDto>();

		List<SupplyRequestedEntity> listSuppliesRrequestedEntity = supplyRequestedService
				.getSuppliesRequestedByProviderAndStates(providerId, states);

		for (SupplyRequestedEntity supplyRequestedEntity : listSuppliesRrequestedEntity) {
			suppliesRequestedDto.add(this.entityParseDto(supplyRequestedEntity));
		}

		return suppliesRequestedDto;
	}

	public SupplyRequestedDto getSupplyRequestedById(Long supplyRequestedId) {

		SupplyRequestedDto supplyRequestedDto = null;

		SupplyRequestedEntity supplyRequestedEntity = supplyRequestedService.getSupplyRequestedById(supplyRequestedId);

		if (supplyRequestedEntity instanceof SupplyRequestedEntity) {
			supplyRequestedDto = entityParseDto(supplyRequestedEntity);
		}

		return supplyRequestedDto;
	}

	public SupplyRequestedDto entityParseDto(SupplyRequestedEntity supplyRE) {

		SupplyRequestedDto supplyRequested = new SupplyRequestedDto();

		supplyRequested.setId(supplyRE.getId());
		supplyRequested.setDescription(supplyRE.getDescription());
		supplyRequested.setCreatedAt(supplyRE.getCreatedAt());
		supplyRequested.setDelivered(supplyRE.getDelivered());
		supplyRequested.setDeliveredAt(supplyRE.getDeliveredAt());
		supplyRequested.setJustification(supplyRE.getJustification());
		supplyRequested.setModelVersion(supplyRE.getModelVersion());
		supplyRequested.setDeliveredBy(supplyRE.getDeliveredBy());
		supplyRequested.setUrl(supplyRE.getUrl());
		supplyRequested.setObservations(supplyRE.getObservations());
		supplyRequested.setFtp(supplyRE.getFtp());

		SupplyRequestedStateEntity stateSupplyRequested = supplyRE.getState();
		supplyRequested
				.setState(new SupplyRequestedStateDto(stateSupplyRequested.getId(), stateSupplyRequested.getName()));

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

		ProviderEntity providerEntity = tsE.getProvider();
		ProviderDto providerDto = new ProviderDto();
		providerDto.setId(providerEntity.getId());
		providerDto.setName(providerEntity.getName());
		providerDto.setCreatedAt(providerEntity.getCreatedAt());
		providerDto.setTaxIdentificationNumber(providerEntity.getTaxIdentificationNumber());

		typeSupplyDto.setProvider(providerDto);

		ProviderProfileDto providerProfileDto = new ProviderProfileDto();
		providerProfileDto.setDescription(tsE.getProviderProfile().getDescription());
		providerProfileDto.setId(tsE.getProviderProfile().getId());
		providerProfileDto.setName(tsE.getProviderProfile().getName());
		typeSupplyDto.setProviderProfile(providerProfileDto);

		supplyRequested.setTypeSupply(typeSupplyDto);

		RequestDto requestDto = new RequestDto();

		RequestEntity requestEntity = supplyRE.getRequest();

		requestDto.setId(requestEntity.getId());
		requestDto.setMunicipalityCode(requestEntity.getMunicipalityCode());

		supplyRequested.setRequest(requestDto);

		return supplyRequested;
	}

}
