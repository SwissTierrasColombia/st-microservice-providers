package com.ai.st.microservice.providers.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.st.microservice.providers.dto.ProviderCategoryDto;
import com.ai.st.microservice.providers.dto.ProviderDto;
import com.ai.st.microservice.providers.entities.ProviderCategoryEntity;
import com.ai.st.microservice.providers.entities.ProviderEntity;
import com.ai.st.microservice.providers.exceptions.BusinessException;
import com.ai.st.microservice.providers.services.IProviderCategoryService;
import com.ai.st.microservice.providers.services.IProviderService;

@Component
public class ProviderBusiness {

	@Autowired
	private IProviderService providerService;

	@Autowired
	private IProviderCategoryService providerCategoryService;

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

}
