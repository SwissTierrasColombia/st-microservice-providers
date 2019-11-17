package com.ai.st.microservice.providers.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.st.microservice.providers.entities.ProviderCategoryEntity;
import com.ai.st.microservice.providers.repositories.ProviderCategoryRepository;

@Service
public class ProviderCategoryService implements IProviderCategoryService {

	@Autowired
	private ProviderCategoryRepository providerCategoryRepository;

	@Override
	@Transactional
	public ProviderCategoryEntity createProviderCategory(ProviderCategoryEntity providerCategory) {
		return providerCategoryRepository.save(providerCategory);
	}

	@Override
	public Long getCount() {
		return providerCategoryRepository.count();
	}

	@Override
	public ProviderCategoryEntity getProviderCategoryById(Long id) {
		return providerCategoryRepository.findById(id).orElse(null);
	}

}
