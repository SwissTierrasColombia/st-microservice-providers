package com.ai.st.microservice.providers.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.st.microservice.providers.entities.ProviderUserEntity;
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

}
