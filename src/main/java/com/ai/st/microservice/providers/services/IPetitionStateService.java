package com.ai.st.microservice.providers.services;

import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.PetitionStateEntity;

public interface IPetitionStateService {

    public Long getCount();

    public PetitionStateEntity createPetitionState(PetitionStateEntity petition);

    public PetitionStateEntity getPetitionStateById(Long id);

}
