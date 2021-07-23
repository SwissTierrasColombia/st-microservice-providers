package com.ai.st.microservice.providers.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderAdministratorEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.RoleEntity;

public interface ProviderAdministratorRepository extends CrudRepository<ProviderAdministratorEntity, Long> {

	ProviderAdministratorEntity findByProviderAndRoleAndUserCode(ProviderEntity provider, RoleEntity role,
			Long userCode);

	List<ProviderAdministratorEntity> findByUserCode(Long userCode);

}
