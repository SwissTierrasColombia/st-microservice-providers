package com.ai.st.microservice.providers.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.st.microservice.providers.entities.ExtensionEntity;
import com.ai.st.microservice.providers.repositories.ExtensionRepository;

@Service
public class ExtensionService implements IExtensionService {

	@Autowired
	private ExtensionRepository extensionRepository;

	@Override
	@Transactional
	public ExtensionEntity createExtension(ExtensionEntity extension) {
		return extensionRepository.save(extension);
	}

	@Override
	@Transactional
	public void deleteExtensionById(Long id) {
		extensionRepository.deleteById(id);
	}

}
