package com.ai.st.microservice.providers.services;

import java.util.List;

import com.ai.st.microservice.providers.entities.ProviderProfileEntity;
import com.ai.st.microservice.providers.entities.TypeSupplyEntity;

public interface ITypeSupplyService {

	public TypeSupplyEntity createTypeSupply(TypeSupplyEntity typeSupply);

	public TypeSupplyEntity getTypeSupplyById(Long id);

	public TypeSupplyEntity getTypeSupplyByName(String name);

	public void deleteTypeSupplyById(Long id);

	public List<TypeSupplyEntity> getTypeSupliesByProfile(ProviderProfileEntity providerProfileEntity);

}
