package com.ai.st.microservice.providers.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.SupplyRequestedEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.TypeSupplyEntity;

public interface SupplyRequestedRepository extends CrudRepository<SupplyRequestedEntity, Long> {

    List<SupplyRequestedEntity> findByTypeSupply(TypeSupplyEntity typeSupply);

    @Query("SELECT sr FROM SupplyRequestedEntity sr JOIN RequestEntity r ON r.id = sr.request.id AND r.provider.id = :providerId WHERE sr.state.id IN :states")
    List<SupplyRequestedEntity> getSuppliesRequestedByProviderAndStates(@Param("providerId") Long providerId,
            @Param("states") List<Long> states);

}
