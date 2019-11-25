package com.ai.st.microservice.providers.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ai.st.microservice.providers.entities.ProviderUserEntity;

public interface ProviderUserRepository extends CrudRepository<ProviderUserEntity, Long> {

	List<ProviderUserEntity> findByUserCode(Long userCode);

}
