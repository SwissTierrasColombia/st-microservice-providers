package com.ai.st.microservice.providers.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ai.st.microservice.providers.entities.RequestEntity;

public interface RequestRepository extends CrudRepository<RequestEntity, Long> {

}
