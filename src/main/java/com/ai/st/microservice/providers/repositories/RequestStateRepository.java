package com.ai.st.microservice.providers.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ai.st.microservice.providers.entities.RequestStateEntity;

public interface RequestStateRepository extends CrudRepository<RequestStateEntity, Long> {

}
