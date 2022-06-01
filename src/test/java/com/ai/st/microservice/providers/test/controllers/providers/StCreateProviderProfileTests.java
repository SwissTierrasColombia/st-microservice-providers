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
import com.ai.st.microservice.providers.services.IProviderCategoryService;
import com.ai.st.microservice.providers.services.IProviderService;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class StCreateProviderProfileTests {

    private final static Logger log = LoggerFactory.getLogger(StCreateProviderProfileTests.class);

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

        log.info("configured environment (StCreateProviderProfileTests)");
    }

    @Test
    @Transactional
    public void validateCreateProfile() {

        CreateProviderProfileDto createProfileDto = new CreateProviderProfileDto();

        createProfileDto.setName(RandomStringUtils.random(10, true, false));
        createProfileDto.setDescription(RandomStringUtils.random(30, true, false));

        ResponseEntity<?> data = providerController.createProviderArea(providerEntity.getId(), createProfileDto);
        ProviderProfileDto profileDto = (ProviderProfileDto) data.getBody();

        Assert.notNull(profileDto, "La respuesta no puede ser nula.");
        assertEquals(HttpStatus.CREATED, data.getStatusCode());
        assertTrue(true);
    }

    @Test
    @Transactional
    public void shouldErrorWhenNameIsEmpty() {

        CreateProviderProfileDto createProfileDto = new CreateProviderProfileDto();

        createProfileDto.setDescription(RandomStringUtils.random(30, true, false));

        ResponseEntity<?> data = providerController.createProviderArea(providerEntity.getId(), createProfileDto);

        assertEquals(HttpStatus.BAD_REQUEST, data.getStatusCode(),
                "No debería crear el perfil porque no se ha enviado el nombre.");
    }

    @Test
    @Transactional
    public void shouldErrorWhenDescriptionIsEmpty() {

        CreateProviderProfileDto createProfileDto = new CreateProviderProfileDto();

        createProfileDto.setName(RandomStringUtils.random(10, true, false));

        ResponseEntity<?> data = providerController.createProviderArea(providerEntity.getId(), createProfileDto);

        assertEquals(HttpStatus.BAD_REQUEST, data.getStatusCode(),
                "No debería crear el perfil porque no se ha enviado la descripción.");
    }

    @Test
    @Transactional
    public void shouldErrorWhenProviderDoesExists() {

        CreateProviderProfileDto createProfileDto = new CreateProviderProfileDto();

        createProfileDto.setName(RandomStringUtils.random(10, true, false));
        createProfileDto.setDescription(RandomStringUtils.random(30, true, false));

        ResponseEntity<?> data = providerController.createProviderArea((long) 50, createProfileDto);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, data.getStatusCode(),
                "No debería crear el perfil porque el proveedor no existe.");
    }

}
