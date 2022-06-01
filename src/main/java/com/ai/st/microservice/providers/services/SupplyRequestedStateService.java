package com.ai.st.microservice.providers.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.SupplyRequestedStateEntity;
import com.ai.st.microservice.providers.repositories.SupplyRequestedStateRepository;

@Service
public class SupplyRequestedStateService implements ISupplyRequestedStateService {

    @Autowired
    private SupplyRequestedStateRepository supplyRequestedStateRepository;

    @Override
    public Long getCount() {
        return supplyRequestedStateRepository.count();
    }

    @Override
    @Transactional
    public SupplyRequestedStateEntity createState(SupplyRequestedStateEntity state) {
        return supplyRequestedStateRepository.save(state);
    }

    @Override
    public SupplyRequestedStateEntity getStateById(Long id) {
        return supplyRequestedStateRepository.findById(id).orElse(null);
    }

}
