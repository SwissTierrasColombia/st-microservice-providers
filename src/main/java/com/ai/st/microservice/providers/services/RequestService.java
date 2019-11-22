package com.ai.st.microservice.providers.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.st.microservice.providers.entities.RequestEntity;
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

}
