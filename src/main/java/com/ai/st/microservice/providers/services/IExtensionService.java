package com.ai.st.microservice.providers.services;

import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ExtensionEntity;

public interface IExtensionService {

	public ExtensionEntity createExtension(ExtensionEntity extension);

	public void deleteExtensionById(Long id);

}
