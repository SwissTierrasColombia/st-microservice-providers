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

	@Query(value = "SELECT setval('providers.providers_id_seq', :value, true);", nativeQuery = true)
	Long setValSequence(@Param("value") Long value);

	@Query(value = "select \n" + "p.*\n" + "from \n" + "providers.providers p,\n" + "providers.requests r ,\n"
			+ "providers.emitters e \n" + "where\n" + "r.provider_id = p.id \n" + "and e.emitter_type = 'ENTITY'\n"
			+ "and e.emitter_code = :managerCode \n" + "and e.request_id  = r.id \n" + "group by p.id;", nativeQuery = true)
	List<ProviderEntity> getProvidersWhereManagerRequested(@Param("managerCode") Long managerCode);

}