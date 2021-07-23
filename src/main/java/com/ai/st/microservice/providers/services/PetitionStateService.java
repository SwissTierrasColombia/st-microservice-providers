package com.ai.st.microservice.providers.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.PetitionStateEntity;
import com.ai.st.microservice.providers.repositories.PetitionStateRepository;

@Service
public class PetitionStateService implements IPetitionStateService {

	@Autowired
	private PetitionStateRepository petitionStateRepository;

	@Override
	public Long getCount() {
		return petitionStateRepository.count();
	}

	@Override
	@Transactional
	public PetitionStateEntity createPetitionState(PetitionStateEntity petition) {
		return petitionStateRepository.save(petition);
	}

	@Override
	public PetitionStateEntity getPetitionStateById(Long id) {
		return petitionStateRepository.findById(id).orElse(null);
	}

}
