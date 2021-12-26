package com.ai.st.microservice.providers.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderAdministratorEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.RoleEntity;
import com.ai.st.microservice.providers.repositories.ProviderAdministratorRepository;

@Service
public class ProviderAdministratorService implements IProviderAdministratorService {

	@Autowired
	private ProviderAdministratorRepository providerAdministratorRepository;

	@Override
	public ProviderAdministratorEntity getProviderAdministratorByUserAndRoleAndProvider(Long userCode,
			RoleEntity roleEntity, ProviderEntity providerEntity) {
		return providerAdministratorRepository.findByProviderAndRoleAndUserCode(providerEntity, roleEntity, userCode);
	}

	@Override
	@Transactional
	public ProviderAdministratorEntity createProviderAdministrator(ProviderAdministratorEntity entity) {
		return providerAdministratorRepository.save(entity);
	}

	@Override
	public List<ProviderAdministratorEntity> getProviderAdministratorsByUserCode(Long userCode) {
		return providerAdministratorRepository.findByUserCode(userCode);
	}

}
