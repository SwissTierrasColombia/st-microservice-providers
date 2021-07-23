package com.ai.st.microservice.providers.services;

import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.RoleEntity;

public interface IRoleService {

	public Long getCount();

	public RoleEntity createRole(RoleEntity roleEntity);

	public RoleEntity getRoleById(Long roleId);

}
