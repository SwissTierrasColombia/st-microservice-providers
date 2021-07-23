package com.ai.st.microservice.providers.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderProfileEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.TypeSupplyEntity;
import com.ai.st.microservice.providers.repositories.TypeSupplyRepository;

@Service
public class TypeSupplyService implements ITypeSupplyService {

	@Autowired
	private TypeSupplyRepository typeSupplyRepository;

	@Override
	@Transactional
	public TypeSupplyEntity createTypeSupply(TypeSupplyEntity typeSupply) {
		return typeSupplyRepository.save(typeSupply);
	}

	@Override
	public TypeSupplyEntity getTypeSupplyById(Long id) {
		return typeSupplyRepository.findById(id).orElse(null);
	}

	@Override
	public TypeSupplyEntity getTypeSupplyByName(String name) {
		return typeSupplyRepository.findByName(name);
	}

	@Override
	@Transactional
	public void deleteTypeSupplyById(Long id) {
		typeSupplyRepository.deleteById(id);
	}

	@Override
	public List<TypeSupplyEntity> getTypeSupliesByProfile(ProviderProfileEntity providerProfileEntity) {
		return typeSupplyRepository.findByProviderProfile(providerProfileEntity);
	}

	@Override
	public Long setValueSequence(Long value) {
		return typeSupplyRepository.setValSequence(value);
	}

}
