package com.ai.st.microservice.providers.services;

import java.util.List;

import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderAdministratorEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.RoleEntity;

public interface IProviderAdministratorService {

	public ProviderAdministratorEntity getProviderAdministratorByUserAndRoleAndProvider(Long userCode,
			RoleEntity roleEntity, ProviderEntity providerEntity);

	public ProviderAdministratorEntity createProviderAdministrator(ProviderAdministratorEntity entity);

	public List<ProviderAdministratorEntity> getProviderAdministratorsByUserCode(Long userCode);

}
