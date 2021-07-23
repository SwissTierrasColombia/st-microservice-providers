package com.ai.st.microservice.providers.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderProfileEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.TypeSupplyEntity;

public interface TypeSupplyRepository extends CrudRepository<TypeSupplyEntity, Long> {

	TypeSupplyEntity findByName(String name);

	List<TypeSupplyEntity> findByProviderProfile(ProviderProfileEntity providerProfile);
	
	@Query(value = "SELECT setval('providers.types_supplies_id_seq', :value, true);", nativeQuery = true)
	Long setValSequence(@Param("value") Long value);

}
