package com.ai.st.microservice.providers.services;

import com.ai.st.microservice.providers.entities.TypeSupplyEntity;

public interface ITypeSupplyService {

	public TypeSupplyEntity createTypeSupply(TypeSupplyEntity typeSupply);

	public TypeSupplyEntity getTypeSupplyById(Long id);

}
