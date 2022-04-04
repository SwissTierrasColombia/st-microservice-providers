package com.ai.st.microservice.providers.test.controllers.providers;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

import com.ai.st.microservice.providers.business.ProviderCategoryBusiness;
import com.ai.st.microservice.providers.controllers.v1.ProviderV1Controller;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderCategoryEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderProfileEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderUserEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.TypeSupplyEntity;
import com.ai.st.microservice.providers.services.IProviderCategoryService;
import com.ai.st.microservice.providers.services.IProviderProfileService;
import com.ai.st.microservice.providers.services.IProviderService;
import com.ai.st.microservice.providers.services.IProviderUserService;
import com.ai.st.microservice.providers.services.ITypeSupplyService;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class StDeleteProviderProfileTests {

    private final static Logger log = LoggerFactory.getLogger(StDeleteProviderProfileTests.class);

    @Autowired
    private ProviderV1Controller providerController;

    @Autowired
    private IProviderService providerService;

    @Autowired
    private IProviderCategoryService providerCategoryService;

    @Autowired
    private IProviderProfileService providerProfileService;

    @Autowired
    private ITypeSupplyService typeSupplyService;

    @Autowired
    private IProviderUserService providerUserService;

    private ProviderEntity providerEntity;

    @BeforeAll
    public void init() {

        ProviderCategoryEntity providerCategoryCadastral = providerCategoryService
                .getProviderCategoryById(ProviderCategoryBusiness.PROVIDER_CATEGORY_CADASTRAL);

        providerEntity = new ProviderEntity();
        providerEntity.setId((long) 1);
        providerEntity.setName(RandomStringUtils.random(10, true, false));
        providerEntity.setTaxIdentificationNumber(RandomStringUtils.random(10, false, true));
        providerEntity.setCreatedAt(new Date());
        providerEntity.setProviderCategory(providerCategoryCadastral);
        providerEntity = providerService.createProvider(providerEntity);

        log.info("configured environment (StDeleteProviderProfileTests)");
    }

    @Test
    @Transactional
    public void validateDeleteProfile() {

        ProviderProfileEntity providerProfileEntity = new ProviderProfileEntity();
        providerProfileEntity.setName(RandomStringUtils.random(10, true, false));
        providerProfileEntity.setDescription(RandomStringUtils.random(10, true, false));
        providerProfileEntity.setProvider(providerEntity);
        providerProfileEntity = providerProfileService.createProviderProfile(providerProfileEntity);

        ResponseEntity<?> data = providerController.deleteProviderArea(providerEntity.getId(),
                providerProfileEntity.getId());
        assertEquals(HttpStatus.NO_CONTENT, data.getStatusCode());
    }

    @Test
    @Transactional
    public void shouldErrorWhenProviderDoesNotExists() {

        ProviderProfileEntity providerProfileEntity = new ProviderProfileEntity();
        providerProfileEntity.setName(RandomStringUtils.random(10, true, false));
        providerProfileEntity.setDescription(RandomStringUtils.random(10, true, false));
        providerProfileEntity.setProvider(providerEntity);
        providerProfileEntity = providerProfileService.createProviderProfile(providerProfileEntity);

        ResponseEntity<?> data = providerController.deleteProviderArea((long) 50, providerProfileEntity.getId());
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, data.getStatusCode(),
                "No debería eliminar el perfil porque el proveedor no existe.");
    }

    @Test
    @Transactional
    public void shouldErrorWhenProfileDoesNotExists() {
        ResponseEntity<?> data = providerController.deleteProviderArea(providerEntity.getId(), (long) 50);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, data.getStatusCode(),
                "No debería eliminar el perfil porque el perfil no existe.");
    }

    @Test
    @Transactional
    public void shouldErrorWhenProfileDoesNotBelongToProvider() {

        ProviderCategoryEntity providerCategoryCadastral = providerCategoryService
                .getProviderCategoryById(ProviderCategoryBusiness.PROVIDER_CATEGORY_CADASTRAL);

        ProviderEntity providerEntity2 = new ProviderEntity();
        providerEntity2.setName(RandomStringUtils.random(10, true, false));
        providerEntity2.setTaxIdentificationNumber(RandomStringUtils.random(10, false, true));
        providerEntity2.setCreatedAt(new Date());
        providerEntity2.setProviderCategory(providerCategoryCadastral);
        providerEntity2 = providerService.createProvider(providerEntity2);

        ProviderProfileEntity providerProfileEntity = new ProviderProfileEntity();
        providerProfileEntity.setName(RandomStringUtils.random(10, true, false));
        providerProfileEntity.setDescription(RandomStringUtils.random(10, true, false));
        providerProfileEntity.setProvider(providerEntity2);
        providerProfileEntity = providerProfileService.createProviderProfile(providerProfileEntity);

        ResponseEntity<?> data = providerController.deleteProviderArea(providerEntity.getId(),
                providerProfileEntity.getId());
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, data.getStatusCode(),
                "No debería eliminar el perfil porque el perfil no pertenece al proveedor.");
    }

    @Test
    @Transactional
    public void shouldErrorWhenProfileAssociatedToSupplies() {

        ProviderProfileEntity providerProfileEntity = new ProviderProfileEntity();
        providerProfileEntity.setName(RandomStringUtils.random(10, true, false));
        providerProfileEntity.setDescription(RandomStringUtils.random(10, true, false));
        providerProfileEntity.setProvider(providerEntity);
        providerProfileEntity = providerProfileService.createProviderProfile(providerProfileEntity);

        TypeSupplyEntity typeSupply = new TypeSupplyEntity();
        typeSupply.setName(RandomStringUtils.random(10, true, false));
        typeSupply.setIsMetadataRequired(false);
        typeSupply.setIsModelRequired(false);
        typeSupply.setCreatedAt(new Date());
        typeSupply.setProvider(providerEntity);
        typeSupply.setProviderProfile(providerProfileEntity);
        typeSupply = typeSupplyService.createTypeSupply(typeSupply);

        ResponseEntity<?> data = providerController.deleteProviderArea(providerEntity.getId(),
                providerProfileEntity.getId());
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, data.getStatusCode(),
                "No debería eliminar el perfil porque el perfil esta asociado a tipos de insumo.");
    }

    @Test
    @Transactional
    public void shouldErrorWhenProfileAssociatedToUsers() {

        ProviderProfileEntity providerProfileEntity = new ProviderProfileEntity();
        providerProfileEntity.setName(RandomStringUtils.random(10, true, false));
        providerProfileEntity.setDescription(RandomStringUtils.random(10, true, false));
        providerProfileEntity.setProvider(providerEntity);
        providerProfileEntity = providerProfileService.createProviderProfile(providerProfileEntity);

        ProviderUserEntity user = new ProviderUserEntity();
        user.setCreatedAt(new Date());
        user.setProvider(providerEntity);
        user.setProviderProfile(providerProfileEntity);
        user.setUserCode((long) 100);
        providerUserService.createProviderUser(user);

        ResponseEntity<?> data = providerController.deleteProviderArea(providerEntity.getId(),
                providerProfileEntity.getId());
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, data.getStatusCode(),
                "No debería eliminar el perfil porque el perfil esta asociado a usuarios.");
    }

}
