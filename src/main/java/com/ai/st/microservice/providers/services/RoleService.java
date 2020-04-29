package com.ai.st.microservice.providers.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.st.microservice.providers.entities.RoleEntity;
import com.ai.st.microservice.providers.repositories.RoleRepository;

@Service
public class RoleService implements IRoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Long getCount() {
		return roleRepository.count();
	}

	@Override
	@Transactional
	public RoleEntity createRole(RoleEntity roleEntity) {
		return roleRepository.save(roleEntity);
	}

	@Override
	public RoleEntity getRoleById(Long roleId) {
		return roleRepository.findById(roleId).orElse(null);
	}

}
