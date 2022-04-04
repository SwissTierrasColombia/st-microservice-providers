package com.ai.st.microservice.providers.services;

import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.RequestStateEntity;

public interface IRequestStateService {

    public RequestStateEntity createRequestState(RequestStateEntity requestState);

    public Long getCount();

    public RequestStateEntity getRequestStateById(Long id);

}
