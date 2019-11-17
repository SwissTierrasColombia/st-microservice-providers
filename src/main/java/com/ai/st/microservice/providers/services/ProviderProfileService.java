package com.ai.st.microservice.providers.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.st.microservice.providers.entities.ProviderProfileEntity;
import com.ai.st.microservice.providers.repositories.ProviderProfileRepository;

@Service
public class ProviderProfileService implements IProviderProfileService {

	@Autowired
	private ProviderProfileRepository providerProfileRepository;

	@Override
	@Transactional
	public ProviderProfileEntity createProviderProfile(ProviderProfileEntity providerProfile) {
		return providerProfileRepository.save(providerProfile);
	}

}
