package com.ai.st.microservice.providers.services;

import java.util.List;

import org.springframework.data.domain.Page;

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

	public Page<RequestEntity> getRequestsByManagerAndMunicipality(Long emmiterCode, String emmiterType,
			String municipalityCode, int page, int numberItems);

	public Page<RequestEntity> getRequestsByManagerAndProvider(Long emmiterCode, String emmiterType, Long providerId,
			int page, int numberItems);

	public List<RequestEntity> getRequestsByManagerAndPackage(Long emmiterCode, String emmiterType,
			String packageLabel);

	public List<RequestEntity> getRequestsByPackage(String packageLabel);

}
