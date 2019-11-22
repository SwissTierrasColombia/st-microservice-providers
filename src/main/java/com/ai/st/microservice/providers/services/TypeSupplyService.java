package com.ai.st.microservice.providers.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.st.microservice.providers.entities.TypeSupplyEntity;
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

}
