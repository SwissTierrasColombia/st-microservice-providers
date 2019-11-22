package com.ai.st.microservice.providers.services;

import com.ai.st.microservice.providers.entities.RequestStateEntity;

public interface IRequestStateService {

	public RequestStateEntity createRequestState(RequestStateEntity requestState);

	public Long getCount();

	public RequestStateEntity getRequestStateById(Long id);

}
