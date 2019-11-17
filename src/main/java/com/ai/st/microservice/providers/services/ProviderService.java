package com.ai.st.microservice.providers.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.st.microservice.providers.entities.ProviderEntity;
import com.ai.st.microservice.providers.repositories.ProviderRepository;

@Service
public class ProviderService implements IProviderService {

	@Autowired
	private ProviderRepository providerRepository;

	@Override
	@Transactional
	public ProviderEntity createProvider(ProviderEntity provider) {
		return providerRepository.save(provider);
	}

	@Override
	public Long getCount() {
		return providerRepository.count();
	}

}
