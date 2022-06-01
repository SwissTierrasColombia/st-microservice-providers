package com.ai.st.microservice.providers.services;

import java.util.List;

import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderCategoryEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderEntity;

public interface IProviderService {

    public ProviderEntity createProvider(ProviderEntity provider);

    public Long getCount();

    public List<ProviderEntity> getAllProviders();

    public List<ProviderEntity> getProvidersByCategoryId(Long providerCategoryId);

    public ProviderEntity getProviderByNameAndProviderCategory(String name, ProviderCategoryEntity category);

    public ProviderEntity getProviderById(Long id);

    public ProviderEntity saveProvider(ProviderEntity providerEntity);

    public void deleteProvider(ProviderEntity providerEntity);

    public List<ProviderEntity> getAllProvidersActive(Boolean active);

    public Long setValueSequence(Long value);

    public List<ProviderEntity> getProvidersWhereManagerRequested(Long managerCode);

}
