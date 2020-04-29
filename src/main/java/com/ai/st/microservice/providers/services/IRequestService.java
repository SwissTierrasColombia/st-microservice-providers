package com.ai.st.microservice.providers.services;

import java.util.List;

import com.ai.st.microservice.providers.entities.ProviderEntity;
import com.ai.st.microservice.providers.entities.RequestEntity;
import com.ai.st.microservice.providers.entities.RequestStateEntity;

public interface IRequestService {

	public RequestEntity createRequest(RequestEntity requestEntity);

	public List<RequestEntity> getRequestsByProviderIdAndStateId(Long providerId, Long requestStateId);

	public RequestEntity getRequestById(Long id);

	public RequestEntity updateRequest(RequestEntity requestEntity);

	public List<RequestEntity> getRequestsByEmmiter(Long emmiterCode, String emmiterType);

	public List<RequestEntity> getRequestByClosedByAndProviderAndRequestState(Long closedBy, ProviderEntity provider,
			RequestStateEntity requestState);

}
