package com.ai.st.microservice.providers.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.PetitionEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.PetitionStateEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderEntity;

public interface PetitionRepository extends CrudRepository<PetitionEntity, Long> {

    List<PetitionEntity> findByPetitionStateInAndProvider(List<PetitionStateEntity> states, ProviderEntity provider);

    List<PetitionEntity> findByManagerCodeAndProvider(Long managerCode, ProviderEntity provider);

    List<PetitionEntity> findByManagerCode(Long managerCode);

}
