package com.ai.st.microservice.providers.services;

import java.util.List;

import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderProfileEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.TypeSupplyEntity;

public interface ITypeSupplyService {

    public TypeSupplyEntity createTypeSupply(TypeSupplyEntity typeSupply);

    public TypeSupplyEntity getTypeSupplyById(Long id);

    public TypeSupplyEntity getTypeSupplyByName(String name);

    public void deleteTypeSupplyById(Long id);

    public List<TypeSupplyEntity> getTypeSupliesByProfile(ProviderProfileEntity providerProfileEntity);

    public Long setValueSequence(Long value);

}
