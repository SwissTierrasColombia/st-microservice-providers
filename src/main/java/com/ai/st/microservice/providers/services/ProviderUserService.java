package com.ai.st.microservice.providers.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderProfileEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderUserEntity;
import com.ai.st.microservice.providers.repositories.ProviderUserRepository;

@Service
public class ProviderUserService implements IProviderUserService {

	@Autowired
	private ProviderUserRepository providerUserRepository;

	@Override
	@Transactional
	public ProviderUserEntity createProviderUser(ProviderUserEntity providerUserEntity) {
		return providerUserRepository.save(providerUserEntity);
	}

	@Override
	public List<ProviderUserEntity> getProvidersUsersByCodeUser(Long userCode) {
		return providerUserRepository.findByUserCode(userCode);
	}

	@Override
	public ProviderUserEntity getProviderUserByUserCodeAndProfileAndProvider(Long userCode,
			ProviderProfileEntity providerProfileEntity, ProviderEntity provider) {
		return providerUserRepository.findByProviderProfileAndUserCodeAndProvider(providerProfileEntity, userCode,
				provider);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		providerUserRepository.deleteById(id);
	}

	@Override
	public List<ProviderUserEntity> getProviderUsersByProfile(ProviderProfileEntity providerProfile) {
		return providerUserRepository.findByProviderProfile(providerProfile);
	}

}
