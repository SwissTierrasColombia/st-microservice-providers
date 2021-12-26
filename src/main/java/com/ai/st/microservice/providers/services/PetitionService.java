package com.ai.st.microservice.providers.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.PetitionEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.PetitionStateEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderEntity;
import com.ai.st.microservice.providers.repositories.PetitionRepository;

@Service
public class PetitionService implements IPetitionService {

	@Autowired
	private PetitionRepository petitionRepository;

	@Override
	@Transactional
	public PetitionEntity createPetition(PetitionEntity petition) {
		return petitionRepository.save(petition);
	}

	@Override
	public PetitionEntity getPetitionById(Long id) {
		return petitionRepository.findById(id).orElse(null);
	}

	@Override
	public List<PetitionEntity> getPetitionsByProviderAndStates(ProviderEntity provider,
			List<PetitionStateEntity> states) {
		return petitionRepository.findByPetitionStateInAndProvider(states, provider);
	}

	@Override
	public List<PetitionEntity> getPetitionsByProviderAndManagerCode(ProviderEntity provider, Long managerCode) {
		return petitionRepository.findByManagerCodeAndProvider(managerCode, provider);
	}

	@Override
	public List<PetitionEntity> getPetitionsByManagerCode(Long managerCode) {
		return petitionRepository.findByManagerCode(managerCode);
	}

}
