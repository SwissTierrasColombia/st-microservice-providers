package com.ai.st.microservice.providers.test.controllers.providers;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.ai.st.microservice.providers.business.ProviderCategoryBusiness;
import com.ai.st.microservice.providers.controllers.v1.ProviderV1Controller;
import com.ai.st.microservice.providers.dto.ProviderDto;
import com.ai.st.microservice.providers.entities.ProviderCategoryEntity;
import com.ai.st.microservice.providers.entities.ProviderEntity;
import com.ai.st.microservice.providers.services.IProviderCategoryService;
import com.ai.st.microservice.providers.services.IProviderService;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class StGetProvidersTests {

	private final static Logger log = LoggerFactory.getLogger(StGetProvidersTests.class);

	@Autowired
	private ProviderV1Controller providerController;

	@Autowired
	private IProviderService providerService;

	@Autowired
	private IProviderCategoryService providerCategoryService;

	private ProviderEntity providerEntity;

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

		log.info("configured environment (StGetProvidersTests)");
	}

	@Test
	@Transactional
	public void validateGetProviders() {
		ResponseEntity<Object> data = providerController.getProviders(false);

		@SuppressWarnings("unchecked")
		List<ProviderDto> providers = (List<ProviderDto>) data.getBody();

		assertEquals(HttpStatus.OK, data.getStatusCode());
		Assert.notNull(providers, "La respuesta no puede ser nula.");
		assertTrue(providers.get(0) instanceof ProviderDto);
	}

}
