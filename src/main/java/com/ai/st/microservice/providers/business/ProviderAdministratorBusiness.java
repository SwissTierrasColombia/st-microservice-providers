package com.ai.st.microservice.providers.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.st.microservice.providers.dto.ProviderCategoryDto;
import com.ai.st.microservice.providers.dto.ProviderDto;
import com.ai.st.microservice.providers.dto.RoleDto;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderAdministratorEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.RoleEntity;
import com.ai.st.microservice.providers.exceptions.BusinessException;
import com.ai.st.microservice.providers.services.IProviderAdministratorService;

@Component
public class ProviderAdministratorBusiness {

	@Autowired
	private IProviderAdministratorService providerAdministratorService;

	public List<RoleDto> getRolesByUser(Long userCode) throws BusinessException {

		List<RoleDto> rolesDto = new ArrayList<RoleDto>();

		List<ProviderAdministratorEntity> listAdministrators = providerAdministratorService
				.getProviderAdministratorsByUserCode(userCode);

		for (ProviderAdministratorEntity providerAdministrator : listAdministrators) {

			RoleEntity roleEntity = providerAdministrator.getRole();

			RoleDto roleDto = new RoleDto();
			roleDto.setId(roleEntity.getId());
			roleDto.setName(roleEntity.getName());

			rolesDto.add(roleDto);
		}

		return rolesDto;
	}

	public ProviderDto getProviderByUserCode(Long userCode) throws BusinessException {

		ProviderDto providerDto = null;

		List<ProviderAdministratorEntity> listProvidersUsersEntity = providerAdministratorService
				.getProviderAdministratorsByUserCode(userCode);

		if (listProvidersUsersEntity.size() > 0) {

			ProviderAdministratorEntity providerUserEntity = listProvidersUsersEntity.get(0);
			ProviderEntity providerEntity = providerUserEntity.getProvider();

			providerDto = new ProviderDto();
			providerDto.setId(providerEntity.getId());
			providerDto.setAlias(providerEntity.getAlias());
			providerDto.setName(providerEntity.getName());
			providerDto.setCreatedAt(providerEntity.getCreatedAt());
			providerDto.setTaxIdentificationNumber(providerEntity.getTaxIdentificationNumber());
			providerDto.setProviderCategory(new ProviderCategoryDto(providerEntity.getProviderCategory().getId(),
					providerEntity.getProviderCategory().getName()));
		}

		return providerDto;
	}

}
