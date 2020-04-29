package com.ai.st.microservice.providers.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ai.st.microservice.providers.entities.ProviderAdministratorEntity;
import com.ai.st.microservice.providers.entities.ProviderEntity;
import com.ai.st.microservice.providers.entities.RoleEntity;

public interface ProviderAdministratorRepository extends CrudRepository<ProviderAdministratorEntity, Long> {

	ProviderAdministratorEntity findByProviderAndRoleAndUserCode(ProviderEntity provider, RoleEntity role,
			Long userCode);

	List<ProviderAdministratorEntity> findByUserCode(Long userCode);

}
