package com.ai.st.microservice.providers.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.SupplyRequestedEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.SupplyRevisionEntity;
import com.ai.st.microservice.providers.repositories.SupplyRevisionRepository;

@Service
public class SupplyRevisionService implements ISupplyRevisionService {

    @Autowired
    private SupplyRevisionRepository supplyRevisionRepository;

    @Override
    @Transactional
    public SupplyRevisionEntity createSupplyRevision(SupplyRevisionEntity entity) {
        return supplyRevisionRepository.save(entity);
    }

    @Override
    @Transactional
    public void deleteSupplyRevisionById(Long supplyRevisionId) {
        supplyRevisionRepository.deleteById(supplyRevisionId);
    }

    @Override
    public SupplyRevisionEntity getSupplyRevisionById(Long supplyRevisionId) {
        return supplyRevisionRepository.findById(supplyRevisionId).orElse(null);
    }

    @Override
    public SupplyRevisionEntity getSupplyRevisionBySupplyRequested(SupplyRequestedEntity supplyRequested) {
        return supplyRevisionRepository.findBySupplyRequested(supplyRequested);
    }

}
