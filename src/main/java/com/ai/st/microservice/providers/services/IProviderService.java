package com.ai.st.microservice.providers.services;

import java.util.List;

import com.ai.st.microservice.providers.entities.ProviderCategoryEntity;
import com.ai.st.microservice.providers.entities.ProviderEntity;

public interface IProviderService {

	public ProviderEntity createProvider(ProviderEntity provider);

	public Long getCount();

	public List<ProviderEntity> getAllProviders();

	public List<ProviderEntity> getProvidersByCategoryId(Long providerCategoryId);

	public ProviderEntity getProviderByNameAndProviderCategory(String name, ProviderCategoryEntity category);

	public ProviderEntity getProviderById(Long id);

	public ProviderEntity saveProvider(ProviderEntity providerEntity);

	public void deleteProvider(ProviderEntity providerEntity);

}
