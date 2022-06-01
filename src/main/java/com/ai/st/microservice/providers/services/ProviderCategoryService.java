package com.ai.st.microservice.providers.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderCategoryEntity;
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

    @Override
    public List<ProviderCategoryEntity> getAllProvidersCategories() {
        return providerCategoryRepository.findAll();
    }

}
