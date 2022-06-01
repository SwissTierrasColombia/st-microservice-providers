package com.ai.st.microservice.providers.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.RequestStateEntity;
import com.ai.st.microservice.providers.repositories.RequestStateRepository;

@Service
public class RequestStateService implements IRequestStateService {

    @Autowired
    private RequestStateRepository requestStateRepository;

    @Override
    @Transactional
    public RequestStateEntity createRequestState(RequestStateEntity requestState) {
        return requestStateRepository.save(requestState);
    }

    @Override
    public Long getCount() {
        return requestStateRepository.count();
    }

    @Override
    public RequestStateEntity getRequestStateById(Long id) {
        return requestStateRepository.findById(id).orElse(null);
    }

}
