package com.ai.st.microservice.providers.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.st.microservice.providers.dto.ProviderAdministratorDto;
import com.ai.st.microservice.providers.dto.ProviderCategoryDto;
import com.ai.st.microservice.providers.dto.ProviderDto;
import com.ai.st.microservice.providers.dto.ProviderProfileDto;
import com.ai.st.microservice.providers.dto.ProviderUserDto;
import com.ai.st.microservice.providers.dto.RequestDto;
import com.ai.st.microservice.providers.dto.RoleDto;
import com.ai.st.microservice.providers.dto.TypeSupplyDto;
import com.ai.st.microservice.providers.entities.ExtensionEntity;
import com.ai.st.microservice.providers.entities.ProviderAdministratorEntity;
import com.ai.st.microservice.providers.entities.ProviderCategoryEntity;
import com.ai.st.microservice.providers.entities.ProviderEntity;
import com.ai.st.microservice.providers.entities.ProviderProfileEntity;
import com.ai.st.microservice.providers.entities.ProviderUserEntity;
import com.ai.st.microservice.providers.entities.RequestEntity;
import com.ai.st.microservice.providers.entities.RoleEntity;
import com.ai.st.microservice.providers.entities.RequestStateEntity;
import com.ai.st.microservice.providers.entities.TypeSupplyEntity;
import com.ai.st.microservice.providers.exceptions.BusinessException;
import com.ai.st.microservice.providers.services.IExtensionService;
import com.ai.st.microservice.providers.services.IProviderAdministratorService;
import com.ai.st.microservice.providers.services.IProviderCategoryService;
import com.ai.st.microservice.providers.services.IProviderProfileService;
import com.ai.st.microservice.providers.services.IProviderService;
import com.ai.st.microservice.providers.services.IProviderUserService;
import com.ai.st.microservice.providers.services.IRequestService;
import com.ai.st.microservice.providers.services.IRoleService;
import com.ai.st.microservice.providers.services.ISupplyRequestedService;
import com.ai.st.microservice.providers.services.IRequestStateService;
import com.ai.st.microservice.providers.services.ITypeSupplyService;

@Component
public class ProviderBusiness {

	@Autowired
	private IProviderService providerService;

	@Autowired
	private IProviderCategoryService providerCategoryService;

	@Autowired
	private IRequestService requestService;

	@Autowired
	private IRequestStateService requestStateService;

	@Autowired
	private IProviderProfileService profileService;

	@Autowired
	private IProviderUserService providerUserService;

	@Autowired
	private ITypeSupplyService typeSupplyService;

	@Autowired
	private IRoleService roleService;

	@Autowired
	private IExtensionService extensionService;

	@Autowired
	private ISupplyRequestedService supplyRequestedService;

	@Autowired
	private TypeSupplyBusiness typeSupplyBusiness;

	@Autowired
	private IProviderAdministratorService providerAdministratorService;

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

	public List<TypeSupplyDto> getTypesSuppliesByProviderId(Long providerId, Boolean onlyActive)
			throws BusinessException {

		List<TypeSupplyDto> listTypeSupplyDtos = new ArrayList<TypeSupplyDto>();

		// verify provider exists
		ProviderEntity providerEntity = providerService.getProviderById(providerId);
		if (!(providerEntity instanceof ProviderEntity)) {
			throw new BusinessException("El proveedor de insumo no existe.");
		}

		List<TypeSupplyEntity> listTypeSupplyEntities = providerEntity.getTypesSupplies();
		for (TypeSupplyEntity typeSupplyEntity : listTypeSupplyEntities) {

			if ((onlyActive && typeSupplyEntity.getActive()) || !onlyActive) {
				TypeSupplyDto typeSupplyDto = typeSupplyBusiness.entityParseDto(typeSupplyEntity);
				listTypeSupplyDtos.add(typeSupplyDto);
			}

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

			RequestBusiness requestBusiness = new RequestBusiness();

			RequestDto requestDto = requestBusiness.entityParseDto(requestEntity);

			listRequestsDto.add(requestDto);
		}

		return listRequestsDto;
	}

	public List<RequestDto> getRequestsByProviderAndUserClosed(Long providerId, Long userCode)
			throws BusinessException {

		List<RequestDto> listRequestsDto = new ArrayList<RequestDto>();

		// verify provider exists
		ProviderEntity providerEntity = providerService.getProviderById(providerId);
		if (!(providerEntity instanceof ProviderEntity)) {
			throw new BusinessException("El proveedor de insumo no existe.");
		}

		RequestStateEntity requestState = requestStateService
				.getRequestStateById(RequestStateBusiness.REQUEST_STATE_DELIVERED);

		List<RequestEntity> listRequestEntity = new ArrayList<RequestEntity>();

		listRequestEntity = requestService.getRequestByClosedByAndProviderAndRequestState(userCode, providerEntity,
				requestState);

		for (RequestEntity requestEntity : listRequestEntity) {

			RequestBusiness requestBusiness = new RequestBusiness();

			RequestDto requestDto = requestBusiness.entityParseDto(requestEntity);

			listRequestsDto.add(requestDto);
		}

		return listRequestsDto;
	}

	public List<ProviderUserDto> getUsersByProvider(Long providerId, List<Long> profiles) throws BusinessException {

		List<ProviderUserDto> users = new ArrayList<ProviderUserDto>();

		// verify provider exists
		ProviderEntity providerEntity = providerService.getProviderById(providerId);
		if (!(providerEntity instanceof ProviderEntity)) {
			throw new BusinessException("El proveedor de insumo no existe.");
		}

		for (ProviderUserEntity providerUserEntity : providerEntity.getUsers()) {

			Long profileValid = (long) 1;
			if (profiles != null && profiles.size() > 0) {

				profileValid = profiles.stream()
						.filter(profileId -> profileId.equals(providerUserEntity.getProviderProfile().getId()))
						.findAny().orElse(null);
			}

			if (profileValid != null) {
				Long userCode = providerUserEntity.getUserCode();

				ProviderUserDto providerFound = users.stream().filter(user -> userCode.equals(user.getUserCode()))
						.findAny().orElse(null);
				if (providerFound == null) {

					ProviderUserDto providerUserDto = new ProviderUserDto();
					providerUserDto.setUserCode(userCode);

					List<ProviderProfileDto> profilesDto = new ArrayList<ProviderProfileDto>();
					for (ProviderUserEntity providerUserEntity2 : providerEntity.getUsers()) {
						if (providerUserEntity2.getUserCode().equals(userCode)) {

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
		if (!profileEntity.getProvider().getId().equals(providerEntity.getId())) {
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

		return this.getUsersByProvider(providerId, null);
	}

	public List<ProviderUserDto> removeUserToProvider(Long userCode, Long providerId, Long profileId)
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

		ProviderUserEntity existsUser = providerUserService.getProviderUserByUserCodeAndProfileAndProvider(userCode,
				profileEntity, providerEntity);
		if (!(existsUser instanceof ProviderUserEntity)) {
			throw new BusinessException("El usuario no tiene asignado el perfil especificado.");
		}

		List<ProviderUserEntity> profilesUser = providerUserService.getProvidersUsersByCodeUser(userCode);
		if (profilesUser.size() <= 1) {
			throw new BusinessException("No se puede quitar el perfil al usuario porque es el único que tiene.");
		}

		providerUserService.deleteById(existsUser.getId());

		return this.getUsersByProvider(providerId, null);
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

	public ProviderProfileDto createProviderProfile(String name, String description, Long providerId)
			throws BusinessException {

		name = name.toUpperCase();

		ProviderEntity providerEntity = providerService.getProviderById(providerId);

		// verify if the category exists
		if (!(providerEntity instanceof ProviderEntity)) {
			throw new BusinessException("El proveedor no existe.");
		}

		ProviderProfileEntity providerProfileEntity = new ProviderProfileEntity();
		providerProfileEntity.setName(name);
		providerProfileEntity.setDescription(description);
		providerProfileEntity.setProvider(providerEntity);
		providerProfileEntity = profileService.createProviderProfile(providerProfileEntity);

		ProviderProfileDto providerProfileDto = new ProviderProfileDto();
		providerProfileDto.setId(providerProfileEntity.getId());
		providerProfileDto.setName(name);
		providerProfileDto.setDescription(description);
		providerProfileDto.setProvider(this.providerEntityParseDto(providerEntity));

		return providerProfileDto;
	}

	public ProviderProfileDto updateProviderProfile(String name, String description, Long providerId, Long profileId)
			throws BusinessException {

		name = name.toUpperCase();

		ProviderEntity providerEntity = providerService.getProviderById(providerId);
		if (!(providerEntity instanceof ProviderEntity)) {
			throw new BusinessException("El proveedor no existe.");
		}

		ProviderProfileEntity providerProfileEntity = profileService.getProviderProfileById(profileId);
		if (!(providerProfileEntity instanceof ProviderProfileEntity)) {
			throw new BusinessException("El perfil no existe.");
		}

		if (!providerProfileEntity.getProvider().getId().equals(providerId)) {
			throw new BusinessException("El perfil no pertenece al proveedor.");
		}

		providerProfileEntity.setName(name);
		providerProfileEntity.setDescription(description);
		providerProfileEntity = profileService.createProviderProfile(providerProfileEntity);

		ProviderProfileDto providerProfileDto = new ProviderProfileDto();
		providerProfileDto.setId(providerProfileEntity.getId());
		providerProfileDto.setName(name);
		providerProfileDto.setDescription(description);
		providerProfileDto.setProvider(this.providerEntityParseDto(providerEntity));

		return providerProfileDto;
	}

	public void deleteProviderProfile(Long providerId, Long profileId) throws BusinessException {

		ProviderEntity providerEntity = providerService.getProviderById(providerId);
		if (!(providerEntity instanceof ProviderEntity)) {
			throw new BusinessException("El proveedor no existe.");
		}

		ProviderProfileEntity providerProfileEntity = profileService.getProviderProfileById(profileId);
		if (!(providerProfileEntity instanceof ProviderProfileEntity)) {
			throw new BusinessException("El perfil no existe.");
		}

		if (!providerProfileEntity.getProvider().getId().equals(providerId)) {
			throw new BusinessException("El perfil no pertenece al proveedor.");
		}

		int countTypeSupplies = typeSupplyService.getTypeSupliesByProfile(providerProfileEntity).size();
		if (countTypeSupplies > 0) {
			throw new BusinessException("No se puede eliminar el perfil porque ya esta asociado tipos de insumo.");
		}

		int countProvidersUsers = providerUserService.getProviderUsersByProfile(providerProfileEntity).size();
		if (countProvidersUsers > 0) {
			throw new BusinessException("No se puede eliminar el perfil porque ya esta asociado a usuarios.");
		}

		profileService.deleteProviderProfileById(profileId);

	}

	public TypeSupplyDto createTypeSupply(String name, String description, boolean metadataRequired, Long providerId,
			Long providerProfileId, boolean modelRequired, List<String> extensions) throws BusinessException {

		name = name.toUpperCase();

		ProviderEntity providerEntity = providerService.getProviderById(providerId);

		ProviderProfileEntity providerProfileEntity = profileService.getProviderProfileById(providerProfileId);

		// verify if the provider exists
		if (!(providerEntity instanceof ProviderEntity)) {
			throw new BusinessException("El proveedor no existe.");
		}

		// verify if the provider profile exists
		if (!(providerProfileEntity instanceof ProviderProfileEntity)) {
			throw new BusinessException("El perfil del proveedor no existe.");
		}

		// verify that there is no provider with the same name
		TypeSupplyEntity typeSupplyExistsEntity = typeSupplyService.getTypeSupplyByName(name);
		if (typeSupplyExistsEntity instanceof TypeSupplyEntity) {
			throw new BusinessException("El nombre del tipo de insumo ya esta registrado.");
		}

		ProviderProfileEntity profileFound = providerEntity.getProfiles().stream()
				.filter(p -> p.getId().equals(providerProfileId)).findAny().orElse(null);
		if (profileFound == null) {
			throw new BusinessException("El perfil no pertenece al proveedor.");
		}

		TypeSupplyEntity typeSupplyEntity = new TypeSupplyEntity();
		typeSupplyEntity.setName(name);
		typeSupplyEntity.setDescription(description);
		typeSupplyEntity.setIsMetadataRequired(metadataRequired);
		typeSupplyEntity.setIsModelRequired(modelRequired);
		typeSupplyEntity.setCreatedAt(new Date());
		typeSupplyEntity.setProvider(providerEntity);
		typeSupplyEntity.setProviderProfile(providerProfileEntity);
		typeSupplyEntity = typeSupplyService.createTypeSupply(typeSupplyEntity);

		for (String nameExtension : extensions) {
			ExtensionEntity extensionEntity = new ExtensionEntity();
			extensionEntity.setName(nameExtension);
			extensionEntity.setTypeSupply(typeSupplyEntity);
			extensionService.createExtension(extensionEntity);
		}

		TypeSupplyDto typeSupplyDto = typeSupplyBusiness.getTypeSupplyById(typeSupplyEntity.getId());
		return typeSupplyDto;
	}

	public TypeSupplyDto updateTypeSupply(String name, String description, boolean metadataRequired, Long providerId,
			Long providerProfileId, boolean modelRequired, List<String> extensions, Long typeSupplyId)
			throws BusinessException {

		name = name.toUpperCase();

		ProviderEntity providerEntity = providerService.getProviderById(providerId);

		ProviderProfileEntity providerProfileEntity = profileService.getProviderProfileById(providerProfileId);

		// verify if the provider exists
		if (!(providerEntity instanceof ProviderEntity)) {
			throw new BusinessException("El proveedor no existe.");
		}

		// verify if the provider profile exists
		if (!(providerProfileEntity instanceof ProviderProfileEntity)) {
			throw new BusinessException("El perfil del proveedor no existe.");
		}

		TypeSupplyEntity typeSupplyEntity = typeSupplyService.getTypeSupplyById(typeSupplyId);
		if (!(typeSupplyEntity instanceof TypeSupplyEntity)) {
			throw new BusinessException("El tipo de insumo no existe.");
		}

		// verify that there is no provider with the same name
		TypeSupplyEntity typeSupplyExistsEntity = typeSupplyService.getTypeSupplyByName(name);
		if (typeSupplyExistsEntity instanceof TypeSupplyEntity
				&& !typeSupplyExistsEntity.getId().equals(typeSupplyId)) {
			throw new BusinessException("El nombre del tipo de insumo ya esta registrado.");
		}

		ProviderProfileEntity profileFound = providerEntity.getProfiles().stream()
				.filter(p -> p.getId().equals(providerProfileId)).findAny().orElse(null);
		if (profileFound == null) {
			throw new BusinessException("El perfil no pertenece al proveedor.");
		}

		if (!typeSupplyEntity.getProvider().getId().equals(providerId)) {
			throw new BusinessException("El tipo de insumo no pertenece al proveedor");
		}

		typeSupplyEntity.setName(name);
		typeSupplyEntity.setDescription(description);
		typeSupplyEntity.setIsMetadataRequired(metadataRequired);
		typeSupplyEntity.setIsModelRequired(modelRequired);
		typeSupplyEntity.setProviderProfile(providerProfileEntity);

		typeSupplyEntity = typeSupplyService.createTypeSupply(typeSupplyEntity);

		// remove extensions
		for (ExtensionEntity extensionEntity : typeSupplyEntity.getExtensions()) {
			extensionService.deleteExtensionById(extensionEntity.getId());
		}

		// add extensions
		for (String nameExtension : extensions) {
			ExtensionEntity extensionEntity = new ExtensionEntity();
			extensionEntity.setName(nameExtension);
			extensionEntity.setTypeSupply(typeSupplyEntity);
			extensionService.createExtension(extensionEntity);
		}

		TypeSupplyDto typeSupplyDto = typeSupplyBusiness.getTypeSupplyById(typeSupplyEntity.getId());
		return typeSupplyDto;
	}

	public void deleteTypeSupply(Long providerId, Long typeSupplyId) throws BusinessException {

		// verify if the provider exists
		ProviderEntity providerEntity = providerService.getProviderById(providerId);
		if (!(providerEntity instanceof ProviderEntity)) {
			throw new BusinessException("El proveedor no existe.");
		}

		TypeSupplyEntity typeSupplyEntity = typeSupplyService.getTypeSupplyById(typeSupplyId);
		if (!(typeSupplyEntity instanceof TypeSupplyEntity)) {
			throw new BusinessException("El tipo de insumo no existe.");
		}

		if (!typeSupplyEntity.getProvider().getId().equals(providerId)) {
			throw new BusinessException("El tipo de insumo no pertenece al proveedor.");
		}

		int count = supplyRequestedService.getSuppliesRequestedByTypeSupply(typeSupplyEntity).size();
		if (count > 0) {
			throw new BusinessException("No se puede borrar el tipo de insumo porque ya ha sido solicitado.");
		}

		// remove extensions
		for (ExtensionEntity extensionEntity : typeSupplyEntity.getExtensions()) {
			extensionService.deleteExtensionById(extensionEntity.getId());
		}

		typeSupplyService.deleteTypeSupplyById(typeSupplyEntity.getId());

	}

	public List<ProviderAdministratorDto> addAdministratorToProvider(Long userCode, Long providerId, Long roleId)
			throws BusinessException {

		// verify provider does exists
		ProviderEntity providerEntity = providerService.getProviderById(providerId);
		if (!(providerEntity instanceof ProviderEntity)) {
			throw new BusinessException("El proveedor de insumo no existe.");
		}

		// verify provider role does exists
		RoleEntity roleEntity = roleService.getRoleById(roleId);
		if (!(roleEntity instanceof RoleEntity)) {
			throw new BusinessException("El perfil del proveedor no existe.");
		}

		ProviderAdministratorEntity existsUser = providerAdministratorService
				.getProviderAdministratorByUserAndRoleAndProvider(userCode, roleEntity, providerEntity);
		if (existsUser instanceof ProviderAdministratorEntity) {
			throw new BusinessException("El usuario ya esta registrado en el proveedor con el rol especificado.");
		}

		ProviderAdministratorEntity providerAdministratorEntity = new ProviderAdministratorEntity();
		providerAdministratorEntity.setCreatedAt(new Date());
		providerAdministratorEntity.setProvider(providerEntity);
		providerAdministratorEntity.setRole(roleEntity);
		providerAdministratorEntity.setUserCode(userCode);

		providerAdministratorEntity = providerAdministratorService
				.createProviderAdministrator(providerAdministratorEntity);

		return this.getAdministratorsByProvider(providerId, null);
	}

	public List<ProviderAdministratorDto> getAdministratorsByProvider(Long providerId, List<Long> roles)
			throws BusinessException {

		List<ProviderAdministratorDto> administrators = new ArrayList<ProviderAdministratorDto>();

		// verify provider exists
		ProviderEntity providerEntity = providerService.getProviderById(providerId);
		if (!(providerEntity instanceof ProviderEntity)) {
			throw new BusinessException("El proveedor de insumo no existe.");
		}

		for (ProviderAdministratorEntity providerAdministratorEntity : providerEntity.getAdministrators()) {

			Long roleValid = (long) 1;

			if (roles != null && roles.size() > 0) {

				roleValid = roles.stream()
						.filter(roleId -> roleId.equals(providerAdministratorEntity.getRole().getId())).findAny()
						.orElse(null);
			}

			if (roleValid != null) {
				Long userCode = providerAdministratorEntity.getUserCode();

				ProviderAdministratorDto providerFound = administrators.stream()
						.filter(user -> userCode.equals(user.getUserCode())).findAny().orElse(null);
				if (providerFound == null) {

					ProviderAdministratorDto providerAdministratorDto = new ProviderAdministratorDto();
					providerAdministratorDto.setUserCode(userCode);

					List<RoleDto> rolesDto = new ArrayList<RoleDto>();
					for (ProviderAdministratorEntity providerAdministratorEntity2 : providerEntity
							.getAdministrators()) {
						if (providerAdministratorEntity2.getUserCode().equals(userCode)) {

							RoleEntity roleEntity = providerAdministratorEntity2.getRole();
							RoleDto role = new RoleDto();

							role.setId(roleEntity.getId());
							role.setName(roleEntity.getName());

							rolesDto.add(role);
						}
					}
					providerAdministratorDto.setRoles(rolesDto);

					administrators.add(providerAdministratorDto);
				}
			}

		}

		return administrators;
	}

	public ProviderDto providerEntityParseDto(ProviderEntity provider) {
		ProviderDto dto = null;
		if (provider instanceof ProviderEntity) {
			dto = new ProviderDto();
			dto.setId(provider.getId());
			dto.setName(provider.getName());
			dto.setCreatedAt(provider.getCreatedAt());
			dto.setProviderCategory(new ProviderCategoryDto(provider.getProviderCategory().getId(),
					provider.getProviderCategory().getName()));
			dto.setTaxIdentificationNumber(provider.getTaxIdentificationNumber());
		}
		return dto;
	}

	public ProviderProfileDto providerProfileEntityParseDto(ProviderProfileEntity providerProfileEntity) {
		ProviderProfileDto dto = null;
		if (providerProfileEntity instanceof ProviderProfileEntity) {
			dto = new ProviderProfileDto();
			dto.setId(providerProfileEntity.getId());
			dto.setName(providerProfileEntity.getName());
			dto.setDescription(providerProfileEntity.getDescription());
			dto.setProvider(this.providerEntityParseDto(providerProfileEntity.getProvider()));
		}
		return dto;
	}

	public ProviderDto updateProvider(Long id, String name, String taxIdentificationNumber, Long providerCategoryId)
			throws BusinessException {

		name = name.toUpperCase();

		ProviderCategoryEntity providerCategoryEntity = providerCategoryService
				.getProviderCategoryById(providerCategoryId);

		// verify if the category exists
		if (!(providerCategoryEntity instanceof ProviderCategoryEntity)) {
			throw new BusinessException("The category does not exist.");
		}

		ProviderEntity providerEntity = providerService.getProviderById(id);

		providerEntity.setName(name);
		providerEntity.setTaxIdentificationNumber(taxIdentificationNumber);
		providerEntity.setProviderCategory(providerCategoryEntity);
		providerEntity = providerService.saveProvider(providerEntity);

		ProviderDto providerDto = new ProviderDto();
		providerDto.setId(providerEntity.getId());
		providerDto.setName(providerEntity.getName());
		providerDto.setCreatedAt(providerEntity.getCreatedAt());
		providerDto.setTaxIdentificationNumber(providerEntity.getTaxIdentificationNumber());
		providerDto.setProviderCategory(new ProviderCategoryDto(providerEntity.getProviderCategory().getId(),
				providerEntity.getProviderCategory().getName()));

		return providerDto;
	}

	public void deleteProvider(Long providerId) throws BusinessException {

		// verify if the provider exists
		ProviderEntity providerEntity = providerService.getProviderById(providerId);
		if (!(providerEntity instanceof ProviderEntity)) {
			throw new BusinessException("El proveedor no existe.");
		}

		if (providerEntity.getTypesSupplies().size() > 0) {
			throw new BusinessException("No se puede eliminar el proveedor porque ya tiene configurado insumos.");
		}

		if (providerEntity.getProfiles().size() > 0) {
			throw new BusinessException("No se puede eliminar el proveedor porque ya tiene áreas registradas.");
		}

		if (providerEntity.getAdministrators().size() > 0 || providerEntity.getUsers().size() > 0) {
			throw new BusinessException("No se puede eliminar el proveedor porque ya tiene usuarios registrados.");
		}

		providerService.deleteProvider(providerEntity);
	}

}
