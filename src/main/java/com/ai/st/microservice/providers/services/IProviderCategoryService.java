package com.ai.st.microservice.providers.services;

import java.util.List;

import com.ai.st.microservice.providers.entities.ProviderCategoryEntity;

public interface IProviderCategoryService {

	public ProviderCategoryEntity createProviderCategory(ProviderCategoryEntity providerCategory);

	public Long getCount();

	public ProviderCategoryEntity getProviderCategoryById(Long id);

	public List<ProviderCategoryEntity> getAllProvidersCategories();

}
