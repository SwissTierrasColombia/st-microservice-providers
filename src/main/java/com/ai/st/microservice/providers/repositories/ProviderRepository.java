package com.ai.st.microservice.providers.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ai.st.microservice.providers.entities.ProviderCategoryEntity;
import com.ai.st.microservice.providers.entities.ProviderEntity;

public interface ProviderRepository extends CrudRepository<ProviderEntity, Long> {

	@Override
	List<ProviderEntity> findAll();

	@Query("SELECT p FROM ProviderEntity p WHERE p.providerCategory.id = :providerCategoryId")
	List<ProviderEntity> getProvidersByCategoryId(@Param("providerCategoryId") Long providerCategoryId);

	ProviderEntity findByNameAndProviderCategory(String name, ProviderCategoryEntity category);
	
	List<ProviderEntity> findByActive(Boolean active);

}
