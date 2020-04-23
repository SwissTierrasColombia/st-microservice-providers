package com.ai.st.microservice.providers.services;

import java.util.List;

import com.ai.st.microservice.providers.entities.ProviderAdministratorEntity;
import com.ai.st.microservice.providers.entities.ProviderEntity;
import com.ai.st.microservice.providers.entities.RoleEntity;

public interface IProviderAdministratorService {

	public ProviderAdministratorEntity getProviderAdministratorByUserAndRoleAndProvider(Long userCode,
			RoleEntity roleEntity, ProviderEntity providerEntity);

	public ProviderAdministratorEntity createProviderAdministrator(ProviderAdministratorEntity entity);

	public List<ProviderAdministratorEntity> getProviderAdministratorsByUserCode(Long userCode);

}
