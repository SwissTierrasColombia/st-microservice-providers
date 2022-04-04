package com.ai.st.microservice.providers.test.controllers.providers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import com.ai.st.microservice.providers.dto.CreateProviderDto;
import com.ai.st.microservice.providers.dto.ProviderDto;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class StCreateProviderTests {

    private final static Logger log = LoggerFactory.getLogger(StCreateProviderProfileTests.class);

    @Autowired
    private ProviderV1Controller providerController;

    @BeforeAll
    public void init() {

        log.info("configured environment (StCreateProviderTests)");
    }

    @Test
    @Transactional
    public void validateCreateProvider() {

        CreateProviderDto createProviderDto = new CreateProviderDto();

        createProviderDto.setName(RandomStringUtils.random(10, true, false));
        createProviderDto.setTaxIdentificationNumber(RandomStringUtils.random(10, false, true));
        createProviderDto.setProviderCategoryId(ProviderCategoryBusiness.PROVIDER_CATEGORY_CADASTRAL);

        ResponseEntity<?> data = providerController.createProvider(createProviderDto);
        ProviderDto providerDto = (ProviderDto) data.getBody();

        Assert.notNull(providerDto, "La respuesta no puede ser nula.");
        assertEquals(HttpStatus.CREATED, data.getStatusCode());
        assertTrue(providerDto instanceof ProviderDto);
    }

    @Test
    @Transactional
    public void shouldErrorWhenNameIsEmpty() {

        CreateProviderDto createProviderDto = new CreateProviderDto();

        createProviderDto.setTaxIdentificationNumber(RandomStringUtils.random(10, false, true));
        createProviderDto.setProviderCategoryId(ProviderCategoryBusiness.PROVIDER_CATEGORY_CADASTRAL);

        ResponseEntity<?> data = providerController.createProvider(createProviderDto);
        ProviderDto providerDto = (ProviderDto) data.getBody();

        Assert.isNull(providerDto, "La respuesta debe ser null.");
        assertEquals(HttpStatus.BAD_REQUEST, data.getStatusCode(),
                "No debería crear el proveedor ya que no se ha enviado el nombre del proveedor.");
    }

    @Test
    @Transactional
    public void shouldErrorWhenTinIsEmpty() {

        CreateProviderDto createProviderDto = new CreateProviderDto();

        createProviderDto.setName(RandomStringUtils.random(10, true, false));
        createProviderDto.setProviderCategoryId(ProviderCategoryBusiness.PROVIDER_CATEGORY_CADASTRAL);

        ResponseEntity<?> data = providerController.createProvider(createProviderDto);
        ProviderDto providerDto = (ProviderDto) data.getBody();

        Assert.isNull(providerDto, "La respuesta debe ser null.");
        assertEquals(HttpStatus.BAD_REQUEST, data.getStatusCode(),
                "No debería crear el proveedor ya que no se ha enviado el nit del proveedor.");
    }

    @Test
    @Transactional
    public void shouldErrorWhenCategoryIsEmpty() {

        CreateProviderDto createProviderDto = new CreateProviderDto();

        createProviderDto.setName(RandomStringUtils.random(10, true, false));
        createProviderDto.setTaxIdentificationNumber(RandomStringUtils.random(10, false, true));

        ResponseEntity<?> data = providerController.createProvider(createProviderDto);
        ProviderDto providerDto = (ProviderDto) data.getBody();

        Assert.isNull(providerDto, "La respuesta debe ser null.");
        assertEquals(HttpStatus.BAD_REQUEST, data.getStatusCode(),
                "No debería crear el proveedor ya que no se ha enviado la categoría del proveedor.");
    }

    @Test
    @Transactional
    public void shouldErrorWhenCategoryDoesNotExists() {

        CreateProviderDto createProviderDto = new CreateProviderDto();

        createProviderDto.setName(RandomStringUtils.random(10, true, false));
        createProviderDto.setTaxIdentificationNumber(RandomStringUtils.random(10, false, true));
        createProviderDto.setProviderCategoryId((long) 50);

        ResponseEntity<?> data = providerController.createProvider(createProviderDto);
        ProviderDto providerDto = (ProviderDto) data.getBody();

        Assert.isNull(providerDto, "La respuesta debe ser null.");
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, data.getStatusCode(),
                "No debería crear el proveedor ya que la categoría que se envía no existe.");
    }

}
