package com.ai.st.microservice.providers.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.st.microservice.providers.entities.SupplyRequestedEntity;
import com.ai.st.microservice.providers.repositories.SupplyRequestedRepository;

@Service
public class SupplyRequestedService implements ISupplyRequestedService {

	@Autowired
	private SupplyRequestedRepository supplyRequestedRepository;

	@Override
	@Transactional
	public SupplyRequestedEntity updateSupplyRequested(SupplyRequestedEntity supplyRequestedEntity) {
		return supplyRequestedRepository.save(supplyRequestedEntity);
	}

}
