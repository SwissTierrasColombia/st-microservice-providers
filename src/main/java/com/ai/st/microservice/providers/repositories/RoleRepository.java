package com.ai.st.microservice.providers.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ai.st.microservice.providers.entities.RoleEntity;

public interface RoleRepository extends CrudRepository<RoleEntity, Long> {

}
