package com.ai.st.microservice.providers.test.controllers.administrators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;

import com.ai.st.microservice.common.business.RoleBusiness;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.ai.st.microservice.providers.business.ProviderCategoryBusiness;
import com.ai.st.microservice.providers.controllers.v1.ProviderAdministratorV1Controller;
import com.ai.st.microservice.providers.dto.RoleDto;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderAdministratorEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderCategoryEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.RoleEntity;
import com.ai.st.microservice.providers.services.IProviderAdministratorService;
import com.ai.st.microservice.providers.services.IProviderCategoryService;
import com.ai.st.microservice.providers.services.IProviderService;
import com.ai.st.microservice.providers.services.IRoleService;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class StGetRolesByUserTests {

	private final static Logger log = LoggerFactory.getLogger(StGetRolesByUserTests.class);

	@Autowired
	private ProviderAdministratorV1Controller administratorController;

	@Autowired
	private IProviderService providerService;

	@Autowired
	private IProviderCategoryService providerCategoryService;

	@Autowired
	private IProviderAdministratorService providerAdministratorService;

	@Autowired
	private IRoleService roleService;

	private Long userCode;

	@BeforeAll
	public void init() {

		userCode = (long) 11;

		ProviderCategoryEntity providerCategoryCadastral = providerCategoryService
				.getProviderCategoryById(ProviderCategoryBusiness.PROVIDER_CATEGORY_CADASTRAL);

		ProviderEntity providerEntity = new ProviderEntity();
		providerEntity.setName(RandomStringUtils.random(10, true, false));
		providerEntity.setTaxIdentificationNumber(RandomStringUtils.random(10, false, true));
		providerEntity.setCreatedAt(new Date());
		providerEntity.setProviderCategory(providerCategoryCadastral);
		providerEntity = providerService.createProvider(providerEntity);

		RoleEntity roleEntity = roleService.getRoleById(RoleBusiness.SUB_ROLE_DIRECTOR_PROVIDER);
		ProviderAdministratorEntity providerAdmin = new ProviderAdministratorEntity();
		providerAdmin.setCreatedAt(new Date());
		providerAdmin.setProvider(providerEntity);
		providerAdmin.setRole(roleEntity);
		providerAdmin.setUserCode(userCode);
		providerAdministratorService.createProviderAdministrator(providerAdmin);

		log.info("configured environment (StGetRolesByUserTests)");
	}

	@Test
	@Transactional
	public void validateGetRolesByUser() {

		ResponseEntity<Object> data = administratorController.getRolesByUser(userCode);
		@SuppressWarnings("unchecked")
		List<RoleDto> listRoleDto = (List<RoleDto>) data.getBody();

		Assert.notNull(listRoleDto, "La respuesta no puede ser nula.");
		assertEquals(HttpStatus.OK, data.getStatusCode());
		assertTrue((listRoleDto.get(0)) instanceof RoleDto);
	}

}
