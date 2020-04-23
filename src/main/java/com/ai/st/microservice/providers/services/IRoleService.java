package com.ai.st.microservice.providers.services;

import com.ai.st.microservice.providers.entities.RoleEntity;

public interface IRoleService {

	public Long getCount();

	public RoleEntity createRole(RoleEntity roleEntity);

	public RoleEntity getRoleById(Long roleId);

}
