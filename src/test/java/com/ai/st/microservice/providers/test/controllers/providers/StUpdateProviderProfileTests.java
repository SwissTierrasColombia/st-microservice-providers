package com.ai.st.microservice.providers.test.controllers.providers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

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
import com.ai.st.microservice.providers.controllers.v1.ProviderV1Controller;
import com.ai.st.microservice.providers.dto.CreateProviderProfileDto;
import com.ai.st.microservice.providers.dto.ProviderProfileDto;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderCategoryEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderProfileEntity;
import com.ai.st.microservice.providers.services.IProviderCategoryService;
import com.ai.st.microservice.providers.services.IProviderProfileService;
import com.ai.st.microservice.providers.services.IProviderService;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class StUpdateProviderProfileTests {

	private final static Logger log = LoggerFactory.getLogger(StUpdateProviderProfileTests.class);

	@Autowired
	private ProviderV1Controller providerController;

	@Autowired
	private IProviderService providerService;

	@Autowired
	private IProviderCategoryService providerCategoryService;

	@Autowired
	private IProviderProfileService providerProfileService;

	private ProviderEntity providerEntity;

	private ProviderProfileEntity profileEntity;

	@BeforeAll
	public void init() {

		ProviderCategoryEntity providerCategoryCadastral = providerCategoryService
				.getProviderCategoryById(ProviderCategoryBusiness.PROVIDER_CATEGORY_CADASTRAL);

		providerEntity = new ProviderEntity();
		providerEntity.setName(RandomStringUtils.random(10, true, false));
		providerEntity.setTaxIdentificationNumber(RandomStringUtils.random(10, false, true));
		providerEntity.setCreatedAt(new Date());
		providerEntity.setProviderCategory(providerCategoryCadastral);
		providerEntity = providerService.createProvider(providerEntity);

		profileEntity = new ProviderProfileEntity();
		profileEntity.setName(RandomStringUtils.random(10, true, false));
		profileEntity.setDescription(RandomStringUtils.random(10, true, false));
		profileEntity.setProvider(providerEntity);
		providerProfileService.createProviderProfile(profileEntity);

		log.info("configured environment (StUpdateProviderProfileTests)");
	}

	@Test
	@Transactional
	public void validateUpdateProfile() {

		CreateProviderProfileDto updateProfileDto = new CreateProviderProfileDto();

		updateProfileDto.setName(RandomStringUtils.random(10, true, false));
		updateProfileDto.setDescription(RandomStringUtils.random(30, true, false));

		ResponseEntity<Object> data = providerController.updateProviderProfile(providerEntity.getId(),
				profileEntity.getId(), updateProfileDto);
		ProviderProfileDto profileDto = (ProviderProfileDto) data.getBody();

		Assert.notNull(profileDto, "La respuesta no puede ser nula.");
		assertEquals(HttpStatus.OK, data.getStatusCode());
		assertTrue(profileDto instanceof ProviderProfileDto);
	}

	@Test
	@Transactional
	public void shouldErrorWhenNameIsEmpty() {

		CreateProviderProfileDto updateProfileDto = new CreateProviderProfileDto();

		updateProfileDto.setDescription(RandomStringUtils.random(30, true, false));

		ResponseEntity<Object> data = providerController.updateProviderProfile(providerEntity.getId(),
				profileEntity.getId(), updateProfileDto);

		assertEquals(HttpStatus.BAD_REQUEST, data.getStatusCode(),
				"No debería actualizar el perfil porque no se ha enviado el nombre.");
	}

	@Test
	@Transactional
	public void shouldErrorWhenDescriptionIsEmpty() {

		CreateProviderProfileDto updateProfileDto = new CreateProviderProfileDto();

		updateProfileDto.setName(RandomStringUtils.random(10, true, false));

		ResponseEntity<Object> data = providerController.updateProviderProfile(providerEntity.getId(),
				profileEntity.getId(), updateProfileDto);

		assertEquals(HttpStatus.BAD_REQUEST, data.getStatusCode(),
				"No debería actualizar el perfil porque no se ha enviado la descripción.");
	}

	@Test
	@Transactional
	public void shouldErrorWhenProviderDoesExists() {

		CreateProviderProfileDto updateProfileDto = new CreateProviderProfileDto();

		updateProfileDto.setName(RandomStringUtils.random(10, true, false));
		updateProfileDto.setDescription(RandomStringUtils.random(30, true, false));

		ResponseEntity<Object> data = providerController.updateProviderProfile((long) 150, profileEntity.getId(),
				updateProfileDto);

		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, data.getStatusCode(),
				"No debería actualizar el perfil porque el proveedor no existe.");
	}

	@Test
	@Transactional
	public void shouldErrorWhenProfileDoesExists() {

		CreateProviderProfileDto updateProfileDto = new CreateProviderProfileDto();

		updateProfileDto.setName(RandomStringUtils.random(10, true, false));
		updateProfileDto.setDescription(RandomStringUtils.random(30, true, false));

		ResponseEntity<Object> data = providerController.updateProviderProfile(providerEntity.getId(), (long) 150,
				updateProfileDto);

		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, data.getStatusCode(),
				"No debería actualizar el perfil porque el perfil no existe.");
	}

}