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
import com.ai.st.microservice.providers.dto.ProviderDto;
import com.ai.st.microservice.providers.dto.UpdateProviderDto;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderCategoryEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderEntity;
import com.ai.st.microservice.providers.services.IProviderCategoryService;
import com.ai.st.microservice.providers.services.IProviderService;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class StUpdateProviderTests {

    private final static Logger log = LoggerFactory.getLogger(StUpdateProviderTests.class);

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

        log.info("configured environment (StUpdateProviderTests)");
    }

    @Test
    @Transactional
    public void validateUpdateProvider() {

        UpdateProviderDto updateProviderDto = new UpdateProviderDto();

        updateProviderDto.setId(providerEntity.getId());
        updateProviderDto.setName(RandomStringUtils.random(10, true, false));
        updateProviderDto.setTaxIdentificationNumber(RandomStringUtils.random(10, false, true));
        updateProviderDto.setProviderCategoryId(ProviderCategoryBusiness.PROVIDER_CATEGORY_CADASTRAL);

        ResponseEntity<?> data = providerController.updateProvider(updateProviderDto);
        ProviderDto providerDto = (ProviderDto) data.getBody();

        Assert.notNull(providerDto, "La respuesta no puede ser nula.");
        assertEquals(HttpStatus.OK, data.getStatusCode());
        assertTrue(providerDto instanceof ProviderDto);
    }

    @Test
    @Transactional
    public void shouldErrorWhenNameIsEmpty() {

        UpdateProviderDto updateProviderDto = new UpdateProviderDto();

        updateProviderDto.setId(providerEntity.getId());
        updateProviderDto.setTaxIdentificationNumber(RandomStringUtils.random(10, false, true));
        updateProviderDto.setProviderCategoryId(ProviderCategoryBusiness.PROVIDER_CATEGORY_CADASTRAL);

        ResponseEntity<?> data = providerController.updateProvider(updateProviderDto);
        ProviderDto providerDto = (ProviderDto) data.getBody();

        Assert.isNull(providerDto, "La respuesta debe ser nula.");
        assertEquals(HttpStatus.BAD_REQUEST, data.getStatusCode(),
                "No debería actualizar el proveedor ya que no se ha enviado el nombre del proveedor.");
    }

    @Test
    @Transactional
    public void shouldErrorWhenTinIsEmpty() {

        UpdateProviderDto updateProviderDto = new UpdateProviderDto();

        updateProviderDto.setId(providerEntity.getId());
        updateProviderDto.setName(RandomStringUtils.random(10, true, false));
        updateProviderDto.setProviderCategoryId(ProviderCategoryBusiness.PROVIDER_CATEGORY_CADASTRAL);

        ResponseEntity<?> data = providerController.updateProvider(updateProviderDto);
        ProviderDto providerDto = (ProviderDto) data.getBody();

        Assert.isNull(providerDto, "La respuesta debe ser nula.");
        assertEquals(HttpStatus.BAD_REQUEST, data.getStatusCode(),
                "No debería actualizar el proveedor ya que no se ha enviado el nit del proveedor.");
    }

    @Test
    @Transactional
    public void shouldErrorWhenProviderIdIsEmpty() {

        UpdateProviderDto updateProviderDto = new UpdateProviderDto();

        updateProviderDto.setName(RandomStringUtils.random(10, true, false));
        updateProviderDto.setTaxIdentificationNumber(RandomStringUtils.random(10, false, true));
        updateProviderDto.setProviderCategoryId(ProviderCategoryBusiness.PROVIDER_CATEGORY_CADASTRAL);

        ResponseEntity<?> data = providerController.updateProvider(updateProviderDto);
        ProviderDto providerDto = (ProviderDto) data.getBody();

        Assert.isNull(providerDto, "La respuesta debe ser nula.");
        assertEquals(HttpStatus.BAD_REQUEST, data.getStatusCode(),
                "No debería actualizar el proveedor ya que no se ha enviado el id del proveedor.");
    }

    @Test
    @Transactional
    public void shouldErrorWhenCategoryIsEmpty() {

        UpdateProviderDto updateProviderDto = new UpdateProviderDto();

        updateProviderDto.setId(providerEntity.getId());
        updateProviderDto.setName(RandomStringUtils.random(10, true, false));
        updateProviderDto.setTaxIdentificationNumber(RandomStringUtils.random(10, false, true));

        ResponseEntity<?> data = providerController.updateProvider(updateProviderDto);
        ProviderDto providerDto = (ProviderDto) data.getBody();

        Assert.isNull(providerDto, "La respuesta debe ser nula.");
        assertEquals(HttpStatus.BAD_REQUEST, data.getStatusCode(),
                "No debería actualizar el proveedor ya que no se ha enviado la categoría del proveedor.");
    }

    @Test
    @Transactional
    public void shouldErrorWhenCategoryDoesNotExists() {

        UpdateProviderDto updateProviderDto = new UpdateProviderDto();

        updateProviderDto.setId(providerEntity.getId());
        updateProviderDto.setName(RandomStringUtils.random(10, true, false));
        updateProviderDto.setTaxIdentificationNumber(RandomStringUtils.random(10, false, true));
        updateProviderDto.setProviderCategoryId((long) 50);

        ResponseEntity<?> data = providerController.updateProvider(updateProviderDto);
        ProviderDto providerDto = (ProviderDto) data.getBody();

        Assert.isNull(providerDto, "La respuesta debe ser nula.");
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, data.getStatusCode(),
                "No debería actualizar el proveedor ya que la categoría que se envía no existe.");
    }

}
