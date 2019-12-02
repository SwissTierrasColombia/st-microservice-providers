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
import com.ai.st.microservice.providers.dto.ProviderUserDto;
import com.ai.st.microservice.providers.dto.RequestDto;
import com.ai.st.microservice.providers.dto.RequestStateDto;
import com.ai.st.microservice.providers.dto.SupplyRequestedDto;
import com.ai.st.microservice.providers.dto.TypeSupplyDto;
import com.ai.st.microservice.providers.entities.EmitterEntity;
import com.ai.st.microservice.providers.entities.ExtensionEntity;
import com.ai.st.microservice.providers.entities.ProviderCategoryEntity;
import com.ai.st.microservice.providers.entities.ProviderEntity;
import com.ai.st.microservice.providers.entities.ProviderProfileEntity;
import com.ai.st.microservice.providers.entities.ProviderUserEntity;
import com.ai.st.microservice.providers.entities.RequestEntity;
import com.ai.st.microservice.providers.entities.SupplyRequestedEntity;
import com.ai.st.microservice.providers.entities.TypeSupplyEntity;
import com.ai.st.microservice.providers.exceptions.BusinessException;
import com.ai.st.microservice.providers.services.IProviderCategoryService;
import com.ai.st.microservice.providers.services.IProviderProfileService;
import com.ai.st.microservice.providers.services.IProviderService;
import com.ai.st.microservice.providers.services.IProviderUserService;
import com.ai.st.microservice.providers.services.IRequestService;

@Component
public class ProviderBusiness {

	@Autowired
	private IProviderService providerService;

	@Autowired
	private IProviderCategoryService providerCategoryService;

	@Autowired
	private IRequestService requestService;

	@Autowired
	private IProviderProfileService profileService;

	@Autowired
	private IProviderUserService providerUserService;

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
			requestDto.setMunicipalityCode(requestEntity.getMunicipalityCode());

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
				supplyRequested.setCreatedAt(supplyRE.getCreatedAt());
				supplyRequested.setDelivered(supplyRE.getDelivered());
				supplyRequested.setDeliveredAt(supplyRE.getDeliveredAt());
				supplyRequested.setJustification(supplyRE.getJustification());

				TypeSupplyEntity tsE = supplyRE.getTypeSupply();

				TypeSupplyDto typeSupplyDto = new TypeSupplyDto();
				typeSupplyDto.setCreatedAt(tsE.getCreatedAt());
				typeSupplyDto.setDescription(tsE.getDescription());
				typeSupplyDto.setId(tsE.getId());
				typeSupplyDto.setMetadataRequired(tsE.getIsMetadataRequired());
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
				emitterDto.setId(emitterEntity.getId());
				emitterDto.setEmitterType(emitterEntity.getEmitterType().name());
				emittersDto.add(emitterDto);
			}
			requestDto.setEmitters(emittersDto);

		}

		return listRequestsDto;
	}

	public List<ProviderUserDto> getUsersByProvider(Long providerId) throws BusinessException {

		List<ProviderUserDto> users = new ArrayList<ProviderUserDto>();

		// verify provider exists
		ProviderEntity providerEntity = providerService.getProviderById(providerId);
		if (!(providerEntity instanceof ProviderEntity)) {
			throw new BusinessException("El proveedor de insumo no existe.");
		}

		for (ProviderUserEntity providerUserEntity : providerEntity.getUsers()) {

			Long userCode = providerUserEntity.getUserCode();

			ProviderUserDto providerFound = users.stream().filter(user -> userCode == user.getUserCode()).findAny()
					.orElse(null);
			if (providerFound == null) {

				ProviderUserDto providerUserDto = new ProviderUserDto();
				providerUserDto.setUserCode(userCode);

				List<ProviderProfileDto> profilesDto = new ArrayList<ProviderProfileDto>();
				for (ProviderUserEntity providerUserEntity2 : providerEntity.getUsers()) {
					if (providerUserEntity2.getUserCode() == userCode) {

						ProviderProfileEntity profileEntity = providerUserEntity2.getProviderProfile();
						ProviderProfileDto profile = new ProviderProfileDto();

						profile.setDescription(profileEntity.getDescription());
						profile.setId(profileEntity.getId());
						profile.setName(profileEntity.getName());

						profilesDto.add(profile);
					}
				}
				providerUserDto.setProfiles(profilesDto);

				users.add(providerUserDto);
			}
		}

		return users;
	}

	public List<ProviderUserDto> addUserToProvider(Long userCode, Long providerId, Long profileId)
			throws BusinessException {

		// verify provider does exists
		ProviderEntity providerEntity = providerService.getProviderById(providerId);
		if (!(providerEntity instanceof ProviderEntity)) {
			throw new BusinessException("El proveedor de insumo no existe.");
		}

		// verify provider profile does exists
		ProviderProfileEntity profileEntity = profileService.getProviderProfileById(profileId);
		if (!(profileEntity instanceof ProviderProfileEntity)) {
			throw new BusinessException("El perfil del proveedor no existe.");
		}

		// verify than profile belongs to provider
		if (profileEntity.getProvider().getId() != providerEntity.getId()) {
			throw new BusinessException("El perfil del proveedor no pertenece al proveedor de insumos.");
		}

		ProviderUserEntity existsUser = providerUserService.getProviderUserByUserCodeAndProfileAndProvider(userCode,
				profileEntity, providerEntity);
		if (existsUser instanceof ProviderUserEntity) {
			throw new BusinessException("El usuario ya esta registrado en el proveedor con el perfil especificado.");
		}

		ProviderUserEntity providerUserEntity = new ProviderUserEntity();
		providerUserEntity.setCreatedAt(new Date());
		providerUserEntity.setProvider(providerEntity);
		providerUserEntity.setProviderProfile(profileEntity);
		providerUserEntity.setUserCode(userCode);

		providerUserEntity = providerUserService.createProviderUser(providerUserEntity);

		return this.getUsersByProvider(providerId);
	}

	public List<ProviderProfileDto> getProfilesByProvider(Long providerId) throws BusinessException {

		List<ProviderProfileDto> listProvidersDto = new ArrayList<ProviderProfileDto>();

		// verify provider exists
		ProviderEntity providerEntity = providerService.getProviderById(providerId);
		if (!(providerEntity instanceof ProviderEntity)) {
			throw new BusinessException("El proveedor de insumo no existe.");
		}

		List<ProviderProfileEntity> listProvidersEntity = providerEntity.getProfiles();
		for (ProviderProfileEntity profileEntity : listProvidersEntity) {
			ProviderProfileDto profileDto = new ProviderProfileDto();
			profileDto.setDescription(profileEntity.getDescription());
			profileDto.setId(profileEntity.getId());
			profileDto.setName(profileEntity.getName());
			listProvidersDto.add(profileDto);
		}

		return listProvidersDto;
	}

}
