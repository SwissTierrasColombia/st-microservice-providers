package com.ai.st.microservice.providers.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.SupplyRequestedEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.TypeSupplyEntity;
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

	@Override
	public List<SupplyRequestedEntity> getSuppliesRequestedByTypeSupply(TypeSupplyEntity supplyEntity) {
		return supplyRequestedRepository.findByTypeSupply(supplyEntity);
	}

	@Override
	public List<SupplyRequestedEntity> getSuppliesRequestedByProviderAndStates(Long providerId, List<Long> states) {
		return supplyRequestedRepository.getSuppliesRequestedByProviderAndStates(providerId, states);
	}

	@Override
	public SupplyRequestedEntity getSupplyRequestedById(Long supplyRequestedId) {
		return supplyRequestedRepository.findById(supplyRequestedId).orElse(null);
	}

}
