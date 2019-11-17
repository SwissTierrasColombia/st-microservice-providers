package com.ai.st.microservice.providers.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.st.microservice.providers.entities.ProviderCategoryEntity;
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

	@Override
	public List<ProviderEntity> getAllProviders() {
		return providerRepository.findAll();
	}

	@Override
	public List<ProviderEntity> getProvidersByCategoryId(Long providerCategoryId) {
		return providerRepository.getProvidersByCategoryId(providerCategoryId);
	}

	@Override
	public ProviderEntity getProviderByNameAndProviderCategory(String name, ProviderCategoryEntity category) {
		return providerRepository.findByNameAndProviderCategory(name, category);
	}

	@Override
	public ProviderEntity getProviderById(Long id) {
		return providerRepository.findById(id).orElse(null);
	}

}
