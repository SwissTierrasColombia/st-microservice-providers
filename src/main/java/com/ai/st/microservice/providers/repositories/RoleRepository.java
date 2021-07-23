package com.ai.st.microservice.providers.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.RoleEntity;

public interface RoleRepository extends CrudRepository<RoleEntity, Long> {

}
