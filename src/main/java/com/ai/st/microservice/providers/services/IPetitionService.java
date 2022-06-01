package com.ai.st.microservice.providers.services;

import java.util.List;

import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.PetitionEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.PetitionStateEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderEntity;

public interface IPetitionService {

    public PetitionEntity createPetition(PetitionEntity petition);

    public PetitionEntity getPetitionById(Long id);

    public List<PetitionEntity> getPetitionsByProviderAndStates(ProviderEntity provider,
            List<PetitionStateEntity> states);

    public List<PetitionEntity> getPetitionsByProviderAndManagerCode(ProviderEntity provider, Long managerCode);

    public List<PetitionEntity> getPetitionsByManagerCode(Long managerCode);

}
