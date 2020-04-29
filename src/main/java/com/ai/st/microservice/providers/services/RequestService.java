package com.ai.st.microservice.providers.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.st.microservice.providers.entities.EmitterTypeEnum;
import com.ai.st.microservice.providers.entities.ProviderEntity;
import com.ai.st.microservice.providers.entities.RequestEntity;
import com.ai.st.microservice.providers.entities.RequestStateEntity;
import com.ai.st.microservice.providers.repositories.RequestRepository;

@Service
public class RequestService implements IRequestService {

	@Autowired
	private RequestRepository requestRepository;

	@Override
	@Transactional
	public RequestEntity createRequest(RequestEntity requestEntity) {
		return requestRepository.save(requestEntity);
	}

	@Override
	public List<RequestEntity> getRequestsByProviderIdAndStateId(Long providerId, Long requestStateId) {
		return requestRepository.getRequestsByProviderIdAndStateId(providerId, requestStateId);
	}

	@Override
	public RequestEntity getRequestById(Long id) {
		return requestRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public RequestEntity updateRequest(RequestEntity requestEntity) {
		return requestRepository.save(requestEntity);
	}

	@Override
	public List<RequestEntity> getRequestsByEmmiter(Long emmiterCode, String emmiterType) {
		EmitterTypeEnum emmitterTypeEnum = null;
		if (EmitterTypeEnum.ENTITY.name().equals(emmiterType)) {
			emmitterTypeEnum = EmitterTypeEnum.ENTITY;
		} else {
			emmitterTypeEnum = EmitterTypeEnum.USER;
		}
		return requestRepository.getRequestsByEmmiterCodeAndEmmiterType(emmiterCode, emmitterTypeEnum);
	}

	@Override
	public List<RequestEntity> getRequestByClosedByAndProviderAndRequestState(Long closedBy, ProviderEntity provider,
			RequestStateEntity requestState) {
		return requestRepository.findByClosedByAndProviderAndRequestState(closedBy, provider, requestState);
	}

}
