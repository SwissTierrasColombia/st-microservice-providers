package com.ai.st.microservice.providers.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ai.st.microservice.providers.entities.SupplyRequestedEntity;
import com.ai.st.microservice.providers.entities.TypeSupplyEntity;

public interface SupplyRequestedRepository extends CrudRepository<SupplyRequestedEntity, Long> {

	List<SupplyRequestedEntity> findByTypeSupply(TypeSupplyEntity typeSupply);

}
