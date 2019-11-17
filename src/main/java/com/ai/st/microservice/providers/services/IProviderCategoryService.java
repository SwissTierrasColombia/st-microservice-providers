package com.ai.st.microservice.providers.services;

import com.ai.st.microservice.providers.entities.ProviderCategoryEntity;

public interface IProviderCategoryService {

	public ProviderCategoryEntity createProviderCategory(ProviderCategoryEntity providerCategory);

	public Long getCount();

	public ProviderCategoryEntity getProviderCategoryById(Long id);

}
