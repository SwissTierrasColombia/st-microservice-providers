package com.ai.st.microservice.providers.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ai.st.microservice.providers.entities.ProviderProfileEntity;
import com.ai.st.microservice.providers.entities.TypeSupplyEntity;

public interface TypeSupplyRepository extends CrudRepository<TypeSupplyEntity, Long> {

	TypeSupplyEntity findByName(String name);

	List<TypeSupplyEntity> findByProviderProfile(ProviderProfileEntity providerProfile);

}
